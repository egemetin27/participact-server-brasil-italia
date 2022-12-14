package it.unibo.paserver.manteinance.test;

import static org.junit.Assert.assertEquals;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.config.test.WebComponentsConfig;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.manteinance.TaskReportManteinance;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.UserService;

import java.io.IOException;
import java.util.ArrayList;

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
public class TwoTaskAvailableToExpired {

	@Autowired
	UserService userService;

	@Autowired
	TaskReportService taskReportService;

	@Autowired
	TaskResultService taskResultService;

	@Autowired
	TaskService taskService;

	@Autowired
	TaskReportManteinance taskReportManteinance;

	private Task task1;
	private Task task2;

	private User user;

	@Before
	public void setup() {
		task1 = new Task();
		task1.setName("TestTaskTaskControllerTest");
		task1.setDescription("Task scheduled");
		DateTime start = new DateTime().minusDays(5);
		DateTime end = start.plusDays(4);
		task1.setStart(start);
		task1.setDeadline(end);
		task1.setDuration(10L);
		task1.setSensingDuration(2L);

		task2 = new Task();
		task2.setName("Test2TaskTaskControllerTest");
		task2.setDescription("Task scheduled");
		start = new DateTime().minusDays(5);
		end = start.plusDays(4);
		task2.setStart(start);
		task2.setDeadline(end);
		task2.setDuration(10L);
		task2.setSensingDuration(2L);

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
	public void runManteinance() throws IOException {
		long numReports = taskReportService.getTaskReportsCountByUser(user
				.getId());
		assertEquals(0, numReports);
		ArrayList<String> users = new ArrayList<String>();
		users.add(user.getOfficialEmail());

		TaskReport taskReport = new TaskReport();
		taskReport.setUser(user);
		taskReport.setTask(task1);
		taskReport.setHistory(new ArrayList<TaskHistory>());
		taskReport.setCurrentState(TaskState.AVAILABLE);
		task1.getTaskReport().add(taskReport);
		TaskHistory history = new TaskHistory();
		history.setState(TaskState.AVAILABLE);
		history.setTaskReport(taskReport);
		history.setTimestamp(new DateTime().minusDays(4));
		taskReport.addHistory(history);
		task1 = taskService.save(task1);

		taskReport = new TaskReport();
		taskReport.setUser(user);
		taskReport.setTask(task2);
		taskReport.setHistory(new ArrayList<TaskHistory>());
		taskReport.setCurrentState(TaskState.AVAILABLE);
		task2.getTaskReport().add(taskReport);
		history = new TaskHistory();
		history.setState(TaskState.AVAILABLE);
		history.setTaskReport(taskReport);
		history.setTimestamp(new DateTime().minusDays(4));
		taskReport.addHistory(history);
		history = new TaskHistory();
		history.setState(TaskState.RUNNING);
		history.setTaskReport(taskReport);
		history.setTimestamp(new DateTime().minusDays(4).plusHours(5));
		taskReport.addHistory(history);
		task2 = taskService.save(task2);

		taskReportManteinance.setAvailableExpiredTasksToRejected();

		TaskReport tr = taskReportService.findByUserAndTask(user.getId(),
				task2.getId());
		assertEquals(TaskState.RUNNING, tr.getCurrentState());
		assertEquals(2, tr.getHistory().size());
		assertEquals(TaskState.RUNNING,
				tr.getHistory().get(tr.getHistory().size() - 1).getState());

		tr = taskReportService.findByUserAndTask(user.getId(), task1.getId());
		assertEquals(TaskState.IGNORED, tr.getCurrentState());
		assertEquals(2, tr.getHistory().size());
		assertEquals(task1.getDeadline(),
				tr.getHistory().get(tr.getHistory().size() - 1).getTimestamp());
	}
}
