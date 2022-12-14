package it.unibo.paserver.rest.controller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unibo.participact.domain.proto.DataLocationProtos.DataLocationProto;
import it.unibo.participact.domain.proto.DataLocationProtos.DataLocationProtoList;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.config.test.WebComponentsConfig;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.DataAccelerometer;
import it.unibo.paserver.domain.DataLocation;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskFlatList;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskResult;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.flat.TaskFlat;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.rest.controller.v1.ResultDataController;
import it.unibo.paserver.rest.controller.v1.TaskController;
import it.unibo.paserver.service.AccountService;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.DataService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.task.StrategyHolder;
import it.unibo.paserver.web.controller.task.TaskAddController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class,
		WebComponentsConfig.class, TestDataContextConfiguration.class })
@Transactional
public class LocationTasksDataUploadTest {

	@Autowired
	TaskController taskController;

	@Autowired
	TaskAddController taskAddController;

	@Autowired
	ActionService actionService;

	@Autowired
	UserService userService;

	@Autowired
	AccountService accountService;

	@Autowired
	TaskReportService taskReportService;

	@Autowired
	ResultDataController resultDataController;

	@Autowired
	DataService dataService;

	@Autowired
	TaskResultService taskResultService;

	private Task task1;
	private Task task2;

	private User user;

	@Before
	public void setup() {
		task1 = new Task();
		task1.setName("TestTaskTaskControllerTest");
		task1.setDescription("Task scheduled");
		DateTime start = new DateTime().minusDays(5);
		DateTime end = start.plusDays(20);
		task1.setStart(start);
		task1.setDeadline(end);
		task1.setDuration(10L);
		task1.setSensingDuration(2L);
		Set<Action> actions = new LinkedHashSet<Action>();
		ActionSensing accelerometerAction = new ActionSensing();
		accelerometerAction.setInput_type(Pipeline.Type.LOCATION.toInt());
		accelerometerAction.setName("Location");
		actions.add(accelerometerAction);
		task1.setActions(actions);

		task2 = new Task();
		task2.setName("Test2TaskTaskControllerTest");
		task2.setDescription("Task scheduled");
		start = new DateTime().minusDays(5);
		end = start.plusDays(20);
		task2.setStart(start);
		task2.setDeadline(end);
		task2.setDuration(10L);
		task2.setSensingDuration(2L);
		actions = new LinkedHashSet<Action>();
		accelerometerAction = new ActionSensing();
		accelerometerAction.setInput_type(Pipeline.Type.ACCELEROMETER.toInt());
		accelerometerAction.setName("Accelerometer2");
		actions.add(accelerometerAction);
		task2.setActions(actions);

		user = new UserBuilder()
				.setCredentials("john2@studio.unibo.it", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430912")
				.setName("Mario").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Rossi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRK")
				.setContactPhoneNumber("+39 051 123456")
				.setOfficialEmail("john2@studio.unibo.it")
				.setProjectEmail("john2@gmail.com")
				.setProjectPhoneNumber("+39333012345")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM).build(true);

		userService.save(user);

		user = userService.getUser("john2@studio.unibo.it");
	}

	@Test
	public void acceptTasks() throws IOException {
		long numReports = taskReportService.getTaskReportsCountByUser(user
				.getId());
		assertEquals(0, numReports);
		ArrayList<String> users = new ArrayList<String>();
		users.add(user.getOfficialEmail());
		StrategyHolder strategyHolder = new StrategyHolder();
		strategyHolder.setId(1);
		strategyHolder.setName("By Average Level");
		taskAddController.createAndAssignTask(task1, users,strategyHolder);
		taskAddController.createAndAssignTask(task2, users,strategyHolder);
		Principal p = new Principal() {

			@Override
			public String getName() {
				return "john2@studio.unibo.it";
			}
		};

		TaskFlatList taskFlatList = taskController.getTaskFlat(null, p,
				TaskState.AVAILABLE.toString(),null);

		numReports = taskReportService.getTaskReportsCountByUser(user.getId());
		assertEquals(2, numReports);

		TaskFlat tf = taskFlatList.getList().get(0);
		taskController.acceptTask(null, p, tf.getId());
		tf = taskFlatList.getList().get(1);
		taskController.acceptTask(null, p, tf.getId());

		List<TaskReport> taskReports = taskReportService
				.getTaskReportsByUser(user.getId());
		assertEquals(2, taskReports.size());

		TaskReport tr = taskReports.get(0);
		assertEquals(TaskState.RUNNING, tr.getCurrentState());
		tr = taskReports.get(1);
		assertEquals(TaskState.RUNNING, tr.getCurrentState());

		List<DataLocationProto> locationData = new ArrayList<DataLocationProto>();
		long sampleTS = new DateTime().getMillis();
		locationData.add(DataLocationProto.newBuilder().setSampleTime(sampleTS)
				.setLatitude(44.524744).setLongitude(11.340637).setAccuracy(5)
				.setProvider("Google Maps").build());
		DataLocationProtoList protoAccData = DataLocationProtoList.newBuilder()
				.addAllList(locationData).build();
		HttpServletRequest hr = mock(HttpServletRequest.class);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		protoAccData.writeTo(baos);
		baos.close();
		ServletInputStream msis = new MockServletInputStream(baos.toByteArray());
		when(hr.getHeader(ResultDataController.HEADER_KEY)).thenReturn(
				"MOCK_KEY");
		when(hr.getInputStream()).thenReturn(msis);

		resultDataController.submitDataLocation(hr, p);

		TaskReport trs = taskReportService.findByUserAndTask(user.getId(),
				task1.getId());
		assertNotNull(trs);
		List<TaskReport> trs2 = taskReportService.getTaskReportsByUser(user
				.getId());
		assertEquals(2, trs2.size());

		tr = taskReportService.findByUserAndTask(user.getId(), task1.getId());
		assertNotNull(tr);
		assertEquals(TaskState.RUNNING, tr.getCurrentState());
		tr = taskReportService.findByUserAndTask(user.getId(), task2.getId());
		assertNotNull(tr);
		assertEquals(TaskState.RUNNING, tr.getCurrentState());

		List<DataLocation> das = dataService.getDataByUser(user.getId(),
				DataLocation.class);
		assertEquals(1, das.size());
		assertEquals(sampleTS, das.get(0).getSampleTimestamp().toDate()
				.getTime());
		assertEquals("Google Maps", das.get(0).getProvider());

		TaskResult taskResult = taskResultService.findByUserAndTask(
				user.getId(), task1.getId());
		assertNotNull(taskResult);
		assertEquals(1, taskResult.getData().size());
		assertEquals(1, taskResult.getData(DataLocation.class).size());
		assertEquals(0, taskResult.getData(DataAccelerometer.class).size());
	}

	private class MockServletInputStream extends ServletInputStream {

		private InputStream is;

		public MockServletInputStream(byte[] data) {
			is = new ByteArrayInputStream(data);
		}

		@Override
		public int read() throws IOException {
			return is.read();
		}

	}
}
