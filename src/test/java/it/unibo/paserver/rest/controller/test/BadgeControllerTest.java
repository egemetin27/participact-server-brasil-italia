package it.unibo.paserver.rest.controller.test;

import static org.junit.Assert.assertEquals;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.config.test.WebComponentsConfig;
import it.unibo.paserver.domain.AbstractBadge;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.BadgeActionBuilder;
import it.unibo.paserver.domain.support.BadgeTaskBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.rest.controller.v1.BadgeController;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.UserBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
public class BadgeControllerTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BadgeController badgeController;

	private User user;
	private AbstractBadge badgeAction;
	private AbstractBadge badgeTask;

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		user = new UserBuilder().setCredentials("primo@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430912")
				.setName("Primo").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Primi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRK")
				.setContactPhoneNumber("+39 051 123456")
				.setOfficialEmail("primo@studio.unibo.it")
				.setProjectEmail("primo@gmail.com")
				.setProjectPhoneNumber("+39333012345")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM)
				.setBadges(new HashSet<Badge>()).build();

		ActionSensing accelerometerAction = new ActionSensing();
		accelerometerAction
				.setInput_type(Pipeline.Type.ACCELEROMETER_CLASSIFIER.toInt());
		accelerometerAction.setName("Accelerometer");
		Task task = new Task();
		task.setName("TestTaskTaskControllerTest");
		task.setDescription("Task scheduled");
		DateTime start = new DateTime();
		DateTime end = start.plusDays(20);
		task.setStart(start);
		task.setDeadline(end);
		task.setDuration(10L);
		task.setSensingDuration(2L);
		Set<Action> actions = new LinkedHashSet<Action>();
		actions.add(accelerometerAction);
		task.setActions(actions);

		badgeTask = new BadgeTaskBuilder()
				.setDescription("descrizione badge task")
				.setTitle("titolo badge task").setTask(task).build();
		badgeAction = new BadgeActionBuilder()
				.setDescription("descrizione badge action")
				.setTitle("titolo badge action")
				.setActionType(ActionType.PHOTO).setQuantity(1).build();

		user.getBadges().add(badgeTask);

	}

	@Test
	public void getAllBadgesTest() {
		Badge[] foundArray = badgeController.getAllbadges();
		Set<Badge> foundSet = new HashSet<Badge>(Arrays.asList(foundArray));
		Set<Badge> expectedSet = new HashSet<Badge>(2);
		expectedSet.add(badgeAction);
		expectedSet.add(badgeTask);
		assertEquals(expectedSet, foundSet);
	}

	@Test
	public void getBadgeByIdTest() {
		Badge found = badgeController.getBadgeById(badgeTask.getId());
		assertEquals(badgeTask, found);

	}

	@Test
	public void getBadgeByUserTest() {
		Badge[] foundArray = badgeController.getBadgeByUser(user.getId());
		Set<Badge> foundSet = new HashSet<Badge>(Arrays.asList(foundArray));
		Set<Badge> expectedSet = new HashSet<Badge>(1);
		expectedSet.add(badgeTask);
		assertEquals(expectedSet, foundSet);

	}

}
