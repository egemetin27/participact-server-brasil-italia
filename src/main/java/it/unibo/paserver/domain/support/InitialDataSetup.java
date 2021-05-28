package it.unibo.paserver.domain.support;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Validator;
import de.svenjacobs.loremipsum.LoremIpsum;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.PipelineDescription;
import it.unibo.paserver.domain.Platform;
import it.unibo.paserver.domain.Role;
import it.unibo.paserver.domain.SensorType;
import it.unibo.paserver.domain.SensorTypeInfo;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.CampaignService;
import it.unibo.paserver.service.DataService;
import it.unibo.paserver.service.DevicesService;
import it.unibo.paserver.service.InstitutionsService;
import it.unibo.paserver.service.PipelineDescriptionService;
import it.unibo.paserver.service.SchoolCourseService;
import it.unibo.paserver.service.SensorService;
import it.unibo.paserver.service.TaskHistoryService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserLogFileService;
import it.unibo.paserver.service.UserService;

public class InitialDataSetup {
	private TransactionTemplate transactionTemplate;
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	ActionService actionService;
	@Autowired
	TaskService taskService;
	@Autowired
	TaskUserService taskUserService;
	@Autowired
	UserService userService;
	@Autowired
	PipelineDescriptionService pipelineDescriptionService;
	@Autowired
	InstitutionsService institutionsService;
	@Autowired
	TaskReportService taskReportService;
	@Autowired
	TaskHistoryService taskHistoryService;
	@Autowired
	DataService dataService;
	@Autowired
	UserLogFileService userLogFileService;
	@Autowired
	TaskResultService taskResultService;
	@Autowired
	DevicesService deviceService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	SchoolCourseService schoolCourseService;
	@Autowired
	CampaignService campaignService;

	@Autowired
	SensorService sensorService;

	DefaultResourceLoader defaultResourceLoader;

	public InitialDataSetup(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void initialize() {
		EntityBuilderManager.setEntityManager(entityManager);

		transactionTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus transactionStatus) {
				// String str;
				LoremIpsum loremIpsum = new LoremIpsum();
				defaultResourceLoader = new DefaultResourceLoader();
				// Accounts
				if (!isDataAlreadyPresent()) {
					System.out.println("isDataAlreadyPresent");

					new AccountBuilder().credentials("test", "secret").creationDate(new DateTime()).addRole(Role.ROLE_VIEW).setAdmin(false).setPrivilege(Role.ROLE_RESEARCHER_FIRST.ordinal()).build();
					new AccountBuilder().credentials("root", "secret").creationDate(new DateTime()).addRole(Role.ROLE_ADMIN).setAdmin(true).setPrivilege(Role.ROLE_ADMIN.ordinal()).addRole(Role.ROLE_VIEW).build();
					new AccountBuilder().credentials("prova", "test").creationDate(new DateTime()).setPrivilege(Role.ROLE_RESEARCHER_SECOND.ordinal()).setAdmin(false).build();

				}
				if (!isPipelineDescriptionAlreadyPresent()) {
					System.out.println("isPipelineDescriptionAlreadyPresen");
					configurePipelineDescription();
				}
				// Devices
				if (!isDevicesAlreadyPresent()) {
					System.out.println("isDevicesAlreadyPresent");

					DevicesBuilder db = new DevicesBuilder().setAll(null, "Apple", "iPhone 6", "Apple", Platform.IOS, null, "vbvbcn", "iOS 10", "A9", null, null);
					// Devices d = db.getEntity();
					/*
					 * Set<Sensor> set = getAllSensors(); for(Sensor s : set) s.setDevice(d);
					 * d.setSensors(set);
					 */
					db.build();

					db = new DevicesBuilder().setAll(null, "Apple", "iPhone 7", "Apple", Platform.IOS, null, "ewewqe", "iOS 10", "A10", null, null);
					// d = db.getEntity();
					/*
					 * set = getAllSensors(); for(Sensor s : set) s.setDevice(d); d.setSensors(set);
					 */
					db.build();

					db = new DevicesBuilder().setAll(null, "Samsung", "Galaxy S7", "Samsung", Platform.ANDROID, null, "eqwewq", "Android 6", "MXCPU", null, null);
					// d = db.getEntity();
					/*
					 * set = getAllSensors(); for(Sensor s : set) s.setDevice(d); d.setSensors(set);
					 */
					db.build();

					db = new DevicesBuilder().setAll(null, "Samsung", "Galaxy S7 Edge", "Samsung", Platform.ANDROID, null, "wewewe", "Android 6", "MXCPU", null, null);
					// d = db.getEntity();
					/*
					 * set = getAllSensors(); for(Sensor s : set) s.setDevice(d); d.setSensors(set);
					 */
					db.build();

					db = new DevicesBuilder().setAll(null, "Samsung", "Galaxy S6", "Samsung", Platform.ANDROID, null, "asddasd", "Android 6", "MXCPU", null, null);
					// d = db.getEntity();
					/*
					 * set = getAllSensors(); for(Sensor s : set) s.setDevice(d); d.setSensors(set);
					 */
					db.build();

					/*
					 * try { InputStream source = defaultResourceLoader
					 * .getResource("classpath:/META-INF/devices-latest.json").getInputStream();
					 * BufferedReader reader = new BufferedReader(new InputStreamReader(source,
					 * "UTF-8")); JsonParser parser = new JsonParser(); JsonObject jObject =
					 * parser.parse(org.apache.commons.io.IOUtils.toString(reader))
					 * .getAsJsonObject(); for (Entry<String, JsonElement> item :
					 * jObject.entrySet()) { try { JsonObject j = item.getValue().getAsJsonObject();
					 * String carrier =
					 * j.get("carrier").getAsJsonObject().get("name").getAsString(); String maker =
					 * j.get("maker").getAsJsonObject().get("name").getAsString(); String model =
					 * j.get("name").getAsJsonObject().get("id").getAsString(); String cpu =
					 * j.get("cpu").getAsJsonObject().get("name").getAsString(); String os =
					 * j.get("os").getAsJsonObject().get("type").getAsString(); if
					 * (!Validator.isEmptyString(maker) && !Validator.isEmptyString(model)) {
					 * DevicesBuilder db = new DevicesBuilder() .setAll(null, maker, model, maker,
					 * null, null, carrier, os, cpu, null, null); Devices d = db.getEntity();
					 * addAllSensorToDevice(d); db.build(); } } catch (Exception e) { // TODO:
					 * handle exception } } } catch (Exception e) { e.printStackTrace(); }
					 */
				}
				// Cursos
				if (!isSchoolCoursesAlreadyPresent()) {
					System.out.println("isSchoolCoursesAlreadyPresent");
					try {
						InputStream source = defaultResourceLoader.getResource("classpath:/META-INF/courses.json").getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(source));
						JsonParser parser = new JsonParser();
						JsonObject jObject = parser.parse(org.apache.commons.io.IOUtils.toString(reader)).getAsJsonObject();

						for (Entry<String, JsonElement> item : jObject.entrySet()) {
							try {
								JsonObject j = item.getValue().getAsJsonObject();
								String name = j.get("name").getAsString();
								if (!Validator.isEmptyString(name)) {
									new SchoolCourseBuilder().setAll(null, name, UniCourse.GRADUATION, null).build();
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// Instituicoes
				if (!isInstitutionsAlreadyPresent()) {
					System.out.println("isInstitutionsAlreadyPresent");
					try {
						InputStream source = defaultResourceLoader.getResource("classpath:/META-INF/schools.json").getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(source));
						JsonParser parser = new JsonParser();
						JsonObject jObject = parser.parse(org.apache.commons.io.IOUtils.toString(reader)).getAsJsonObject();

						for (Entry<String, JsonElement> item : jObject.entrySet()) {
							try {
								JsonObject j = item.getValue().getAsJsonObject();
								String name = j.get("name").getAsString();
								if (!Validator.isEmptyString(name)) {
									new InstitutionsBuilder().setAll(name, null, null, null, null, null, null, null, null, null, null, null).build();
								}
								if (!Validator.isEmptyString(name)) {
									new InstitutionsBuilder().setAll(name, null, null, null, null, null, null, null, null, null, null, null).build();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				// Usuario
				if (!isUserAlreadyPresent()) {
					System.out.println("isUserAlreadyPresent");
					try {
						// for (int i = 0; i < 1000; i++) {
						// addRandomUser(loremIpsum, i);
						// }
						addOneUser(loremIpsum, "calraiden@gmail.com", "Claudio", "Google");
						addOneUser(loremIpsum, "qana@qana.com.br", "Qana", "Fake");
						addOneUser(loremIpsum, "fabio.bergmann@bergmannsoft.com.br", "Fabio", "Bergmann");
						addOneUser(loremIpsum, "fabiocberg@gmail.com", "Fabio", "Bergmann");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// Tarefas/Campanhas
				if (!isTaskAlreadyPresent()) {
					System.out.println("isTaskAlreadyPresent");
					try {
						addRandomTask(loremIpsum, 1L, true, true);
						addRandomTask(loremIpsum, 1L, false, true);
						addRandomTask(loremIpsum, 1L, true, false);
						addRandomTask(loremIpsum, 1L, false, false);

						addRandomTask(loremIpsum, 2L, true, true);
						addRandomTask(loremIpsum, 2L, false, true);
						addRandomTask(loremIpsum, 2L, true, false);
						addRandomTask(loremIpsum, 2L, false, false);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				return null;
			}

			private void configurePipelineDescription() {

				// Accelerometer
				PipelineDescription p = new PipelineDescription();
				p.setType(Pipeline.Type.ACCELEROMETER);
				p.setDescription("This pipeline fetches data from the accelerometer.");
				Set<SensorTypeInfo> sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo typeAccelerometer = new SensorTypeInfo();
				typeAccelerometer.setLogic(false);
				typeAccelerometer.setDescription("sensor for measuring device acceleration.");
				typeAccelerometer.setSensorType(SensorType.ACCELEROMETER);
				typeAccelerometer = sensorService.saveSensorTypeInfo(typeAccelerometer);
				sensorSet.add(typeAccelerometer);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Average accelerometer
				p = new PipelineDescription();
				p.setType(Pipeline.Type.AVERAGE_ACCELEROMETER);
				p.setDescription("This pipeline fetches average data from the accelerometer.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo average = new SensorTypeInfo();
				average.setLogic(true);
				average.setDescription("sensor for measuring average device acceleration.");
				average.setSensorType(SensorType.AVERAGE_ACCELEROMETER);
				average = sensorService.saveSensorTypeInfo(average);
				sensorSet.add(typeAccelerometer);
				sensorSet.add(average);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Accelerometer classifier
				p = new PipelineDescription();
				p.setType(Pipeline.Type.ACCELEROMETER_CLASSIFIER);
				p.setDescription("This pipeline classifies accelerometer data to infer device state.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo classf = new SensorTypeInfo();
				classf.setLogic(true);
				classf.setDescription("sensor for classifying data about device acceleration.");
				classf.setSensorType(SensorType.ACCELEROMETER_CLASSIFIER);
				classf = sensorService.saveSensorTypeInfo(classf);
				sensorSet.add(typeAccelerometer);
				sensorSet.add(classf);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Gyroscope
				p = new PipelineDescription();
				p.setType(Pipeline.Type.GYROSCOPE);
				p.setDescription("This pipeline fetches data about phone orientation.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo gyro = new SensorTypeInfo();
				gyro.setDescription("sensor for acquiring data about phone orientation.");
				gyro.setSensorType(SensorType.GYROSCOPE);
				gyro = sensorService.saveSensorTypeInfo(gyro);
				gyro.setLogic(false);
				sensorSet.add(gyro);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Light
				p = new PipelineDescription();
				p.setType(Pipeline.Type.LIGHT);
				p.setDescription("This pipeline fetches data about ambient light.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo light = new SensorTypeInfo();
				light.setDescription("sensor for acquiring data about ambient light.");
				light.setSensorType(SensorType.LIGHT);
				light = sensorService.saveSensorTypeInfo(light);
				light.setLogic(false);
				sensorSet.add(light);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Magnetic field
				p = new PipelineDescription();
				p.setType(Pipeline.Type.MAGNETIC_FIELD);
				p.setDescription("This pipeline fetches data about the magnetic field percieved by the device.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo mag = new SensorTypeInfo();
				mag.setDescription("sensor for acquiring data about the magnetic field percieved by the device.");
				mag.setSensorType(SensorType.MAGNETIC_FIELD);
				mag = sensorService.saveSensorTypeInfo(mag);
				mag.setLogic(false);
				sensorSet.add(mag);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Bluetooth
				p = new PipelineDescription();
				p.setType(Pipeline.Type.BLUETOOTH);
				p.setDescription("This pipeline fetches data from the bluetooth interface of the device.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo blue = new SensorTypeInfo();
				blue.setDescription("sensor for acquiring data about the bluetooth usage.");
				blue.setSensorType(SensorType.BLUETOOTH);
				blue = sensorService.saveSensorTypeInfo(blue);
				blue.setLogic(false);
				sensorSet.add(blue);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// WiFi
				p = new PipelineDescription();
				p.setType(Pipeline.Type.WIFI_SCAN);
				p.setDescription("This pipeline fetches data from the wi-fi interface of the device.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo wifi = new SensorTypeInfo();
				wifi.setDescription("sensor for acquiring data about the wi-fi interface of the device.");
				wifi.setSensorType(SensorType.WIFI_SCAN);
				wifi = sensorService.saveSensorTypeInfo(wifi);
				wifi.setLogic(false);
				sensorSet.add(wifi);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Battery
				p = new PipelineDescription();
				p.setType(Pipeline.Type.BATTERY);
				p.setDescription("This pipeline fetches data about the battery of the device.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo battery = new SensorTypeInfo();
				battery.setDescription("sensor for acquiring battery informations.");
				battery.setSensorType(SensorType.BATTERY);
				battery = sensorService.saveSensorTypeInfo(battery);
				battery.setLogic(false);
				sensorSet.add(battery);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Location
				p = new PipelineDescription();
				p.setType(Pipeline.Type.LOCATION);
				p.setDescription("This pipeline fetches data about device's location.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo location = new SensorTypeInfo();
				location.setDescription("sensor for fetching data about device's location.");
				location.setSensorType(SensorType.LOCATION);
				location = sensorService.saveSensorTypeInfo(location);
				location.setLogic(false);
				sensorSet.add(location);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Activity recognition compare
				p = new PipelineDescription();
				p.setType(Pipeline.Type.ACTIVITY_RECOGNITION_COMPARE);
				p.setDescription("This pipeline fetches data about user's activities.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo ac = new SensorTypeInfo();
				ac.setDescription("this is a logical abstraction in order to monitor user's activities.");
				ac.setSensorType(SensorType.ACTIVITY_RECOGNITION_COMPARE);
				ac = sensorService.saveSensorTypeInfo(ac);
				ac.setLogic(true);
				sensorSet.add(ac);
				sensorSet.add(typeAccelerometer);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Google activity recognition compare
				p = new PipelineDescription();
				p.setType(Pipeline.Type.GOOGLE_ACTIVITY_RECOGNITION);
				p.setDescription("This pipeline fetches data about user's activities.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo ga = new SensorTypeInfo();
				ga.setDescription("this is a logical abstraction in order to use Google API for user's activity recognition.");
				ga.setSensorType(SensorType.GOOGLE_ACTIVITY_RECOGNITION);
				ga = sensorService.saveSensorTypeInfo(ga);
				ga.setLogic(true);
				sensorSet.add(ga);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// DR
				p = new PipelineDescription();
				p.setType(Pipeline.Type.DR);
				p.setDescription("This pipeline fetches data about user's activities.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo dr = new SensorTypeInfo();
				dr.setDescription("this is a logical abstraction in order to monitor user's activities.");
				dr.setSensorType(SensorType.DR);
				dr = sensorService.saveSensorTypeInfo(dr);
				dr.setLogic(true);
				sensorSet.add(dr);
				sensorSet.add(typeAccelerometer);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Installed apps
				p = new PipelineDescription();
				p.setType(Pipeline.Type.INSTALLED_APPS);
				p.setDescription("This pipeline fetches data about installed app on the device.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo installed_apps = new SensorTypeInfo();
				installed_apps.setDescription("sensor for acquiring data about apps installed on the device.");
				installed_apps.setSensorType(SensorType.INSTALLED_APPS);
				installed_apps = sensorService.saveSensorTypeInfo(installed_apps);
				installed_apps.setLogic(true);
				sensorSet.add(installed_apps);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// App on screen
				p = new PipelineDescription();
				p.setType(Pipeline.Type.APP_ON_SCREEN);
				p.setDescription("This pipeline fetches data about apps usage.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo app_on_screen = new SensorTypeInfo();
				app_on_screen.setDescription("sensor for acquiring data about apps usage.");
				app_on_screen.setSensorType(SensorType.APP_ON_SCREEN);
				app_on_screen = sensorService.saveSensorTypeInfo(app_on_screen);
				app_on_screen.setLogic(true);
				sensorSet.add(app_on_screen);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Device net traffic
				p = new PipelineDescription();
				p.setType(Pipeline.Type.DEVICE_NET_TRAFFIC);
				p.setDescription("This pipeline fetches data about the device net traffic and usage.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo d_traffic = new SensorTypeInfo();
				d_traffic.setDescription("sensor for acquiring data about the device internet traffic.");
				d_traffic.setSensorType(SensorType.DEVICE_NET_TRAFFIC);
				d_traffic = sensorService.saveSensorTypeInfo(d_traffic);
				d_traffic.setLogic(true);
				sensorSet.add(d_traffic);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Apps net traffic
				p = new PipelineDescription();
				p.setType(Pipeline.Type.APPS_NET_TRAFFIC);
				p.setDescription("This pipeline fetches data about apps net traffic and usage.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo net_traffic = new SensorTypeInfo();
				net_traffic.setDescription("sensor for acquiring data about apps internet traffic.");
				net_traffic.setSensorType(SensorType.APPS_NET_TRAFFIC);
				net_traffic = sensorService.saveSensorTypeInfo(net_traffic);
				net_traffic.setLogic(true);
				sensorSet.add(net_traffic);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Phone call duration
				p = new PipelineDescription();
				p.setType(Pipeline.Type.PHONE_CALL_DURATION);
				p.setDescription("This pipeline fetches data about phone calls duration.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo phone_call_duration = new SensorTypeInfo();
				phone_call_duration.setDescription("sensor for acquiring data about phone calls duration.");
				phone_call_duration.setSensorType(SensorType.PHONE_CALL_DURATION);
				phone_call_duration = sensorService.saveSensorTypeInfo(phone_call_duration);
				phone_call_duration.setLogic(true);
				sensorSet.add(phone_call_duration);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Phone call event
				p = new PipelineDescription();
				p.setType(Pipeline.Type.PHONE_CALL_EVENT);
				p.setDescription("This pipeline fetches data about phone calls events.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo phone_call_event = new SensorTypeInfo();
				phone_call_event.setDescription("sensor for acquiring data about phone calls events.");
				phone_call_event.setSensorType(SensorType.PHONE_CALL_EVENT);
				phone_call_event = sensorService.saveSensorTypeInfo(phone_call_event);
				phone_call_event.setLogic(true);
				sensorSet.add(phone_call_event);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// System stats
				p = new PipelineDescription();
				p.setType(Pipeline.Type.SYSTEM_STATS);
				p.setDescription("This pipeline fetches data about the phone system status.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo sys_stat = new SensorTypeInfo();
				sys_stat.setDescription("sensor for acquiring data about the phone system status.");
				sys_stat.setSensorType(SensorType.SYSTEM_STATS);
				sys_stat = sensorService.saveSensorTypeInfo(sys_stat);
				sys_stat.setLogic(true);
				sensorSet.add(sys_stat);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Connection type
				p = new PipelineDescription();
				p.setType(Pipeline.Type.CONNECTION_TYPE);
				p.setDescription("This pipeline fetches data about phone connectivity type.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo conn_type = new SensorTypeInfo();
				conn_type.setDescription("sensor for acquiring data about phone connectivity type.");
				conn_type.setSensorType(SensorType.CONNECTION_TYPE);
				conn_type = sensorService.saveSensorTypeInfo(conn_type);
				conn_type.setLogic(true);
				sensorSet.add(conn_type);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Step counter
				p = new PipelineDescription();
				p.setType(Pipeline.Type.STEP_COUNTER);
				p.setDescription("This pipeline fetches data about the user's steps.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo step = new SensorTypeInfo();
				step.setDescription("sensor for acquiring data about the user's steps.");
				step.setSensorType(SensorType.STEP_COUNTER);
				step = sensorService.saveSensorTypeInfo(step);
				step.setLogic(false);
				sensorSet.add(step);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Cell
				p = new PipelineDescription();
				p.setType(Pipeline.Type.CELL);
				p.setDescription("This pipeline fetches data about the cellular connectivity of the device.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo cell = new SensorTypeInfo();
				cell.setDescription("sensor for acquiring data about the cellular connectivity of the device.");
				cell.setSensorType(SensorType.CELL);
				cell = sensorService.saveSensorTypeInfo(cell);
				cell.setLogic(true);
				sensorSet.add(cell);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Audio classifier
				p = new PipelineDescription();
				p.setType(Pipeline.Type.AUDIO_CLASSIFIER);
				p.setDescription("This pipeline fetches audio data and classifies it.");
				sensorSet = new HashSet<SensorTypeInfo>();
				ac = new SensorTypeInfo();
				ac.setDescription("sensor for the classification of audio data.");
				ac.setSensorType(SensorType.AUDIO_CLASSIFIER);
				ac = sensorService.saveSensorTypeInfo(ac);
				ac.setLogic(true);
				sensorSet.add(ac);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Raw audio
				p = new PipelineDescription();
				p.setType(Pipeline.Type.RAW_AUDIO);
				p.setDescription("This pipeline fetches raw audio data.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo ra = new SensorTypeInfo();
				ra.setDescription("sensor for fetching raw audio data.");
				ra.setSensorType(SensorType.RAW_AUDIO);
				ra = sensorService.saveSensorTypeInfo(ra);
				ra.setLogic(false);
				sensorSet.add(ra);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Training
				p = new PipelineDescription();
				p.setType(Pipeline.Type.TRAINING);
				p.setDescription("This pipeline fetches data about user's physical activity.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo tra = new SensorTypeInfo();
				tra.setDescription("sensor for fetching data about user's physical activity.");
				tra.setSensorType(SensorType.TRAINING);
				tra = sensorService.saveSensorTypeInfo(tra);
				tra.setLogic(true);
				sensorSet.add(tra);
				sensorSet.add(gyro);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Test
				p = new PipelineDescription();
				p.setType(Pipeline.Type.TEST);
				p.setDescription("This pipeline is for test purposes.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo test = new SensorTypeInfo();
				test.setDescription("sensor for testing pipelines behaviour.");
				test.setSensorType(SensorType.TEST);
				test = sensorService.saveSensorTypeInfo(test);
				test.setLogic(true);
				sensorSet.add(test);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				// Dummy
				p = new PipelineDescription();
				p.setType(Pipeline.Type.DUMMY);
				p.setDescription("Dummy pipeline.");
				sensorSet = new HashSet<SensorTypeInfo>();
				SensorTypeInfo dummy = new SensorTypeInfo();
				dummy.setDescription("only a placeholder for testing.");
				dummy.setSensorType(SensorType.DUMMY);
				dummy = sensorService.saveSensorTypeInfo(dummy);
				dummy.setLogic(true);
				sensorSet.add(dummy);
				p.setSensDescr(sensorSet);
				pipelineDescriptionService.save(p);

				SensorTypeInfo acti = new SensorTypeInfo();
				acti.setDescription("activity detection sensor.");
				acti.setSensorType(SensorType.ACTIVITY_DETECTION);
				acti = sensorService.saveSensorTypeInfo(acti);

				SensorTypeInfo photo = new SensorTypeInfo();
				photo.setDescription("camera sensor.");
				photo.setSensorType(SensorType.PHOTO);
				photo = sensorService.saveSensorTypeInfo(photo);

				SensorTypeInfo quest = new SensorTypeInfo();
				quest.setDescription("questionnaire form.");
				quest.setSensorType(SensorType.QUESTIONNAIRE);
				quest = sensorService.saveSensorTypeInfo(quest);

			}

			private boolean isDataAlreadyPresent() {
				return entityManager.createQuery("SELECT COUNT(c.id) FROM Account c", Long.class).getSingleResult() > 0;
			}

			private boolean isDevicesAlreadyPresent() {
				return entityManager.createQuery("SELECT COUNT(d.id) FROM Devices d", Long.class).getSingleResult() > 0;
			}

			private boolean isPipelineDescriptionAlreadyPresent() {
				return entityManager.createQuery("SELECT COUNT(d.id) FROM PipelineDescription d", Long.class).getSingleResult() > 0;
			}

			private boolean isSchoolCoursesAlreadyPresent() {
				return entityManager.createQuery("SELECT COUNT(s.id) FROM SchoolCourse s", Long.class).getSingleResult() > 0;
			}

			private boolean isInstitutionsAlreadyPresent() {
				return entityManager.createQuery("SELECT COUNT(i.id) FROM Institutions i", Long.class).getSingleResult() > 0;
			}

			private boolean isUserAlreadyPresent() {
				return entityManager.createQuery("SELECT COUNT(u.id) FROM User u", Long.class).getSingleResult() > 0;
			}

			private boolean isTaskAlreadyPresent() {
				return entityManager.createQuery("SELECT COUNT(t.id) FROM Task t", Long.class).getSingleResult() > 0;
			}

			private Task addRandomTask(LoremIpsum loremIpsum, long parentId, boolean canBeRefused, boolean isDuration) {
				Task task = new Task();
				task.setName(loremIpsum.getWords(RandomUtils.nextInt(1, 10)));
				task.setDescription(loremIpsum.getParagraphs(1));
				task.setCanBeRefused(canBeRefused);
				task.setStart(new DateTime());
				task.setDeadline(new DateTime().plusDays(RandomUtils.nextInt(1, 28)));
				task.setIsDuration(isDuration);
				task.setDuration(RandomUtils.nextLong(60, 2592000));
				task.setSensingDuration(RandomUtils.nextLong(60, 2592000));
				task.setActions(new LinkedHashSet<Action>());
				task.setParentId(parentId);
				return taskService.save(task);
			}

			/*
			 * private void addRandomUser(LoremIpsum loremIpsum, int count) { String name =
			 * loremIpsum.getWords(1); String surname = loremIpsum.getWords(1); String
			 * officialEmail = name + "." + surname + count + "@example.com"; String
			 * password = Config.defaultUserPassword;
			 * 
			 * Long id = 0L; UserBuilder ub = new UserBuilder().setAll(id, officialEmail,
			 * name, surname, it.unibo.paserver.domain.Gender.getRandom(), new LocalDate(),
			 * "", loremIpsum.getWords(1), loremIpsum.getWords(1), loremIpsum.getWords(1),
			 * loremIpsum.getWords(1), Math.random() + "", loremIpsum.getWords(1), "",
			 * it.unibo.paserver.domain.DocumentIdType.getRandom(), "",
			 * UniCourse.getRandom(), (Boolean) false, (Integer) 0, "",
			 * loremIpsum.getParagraphs(1), null, null, null,
			 * null).setCredentials(officialEmail, password);
			 * userService.save(ub.build(true)); }
			 */

			private void addOneUser(LoremIpsum loremIpsum, String officialEmail, String name, String surname) {
				Long id = 0L;
				String password = Config.defaultUserPassword;

				UserBuilder ub = new UserBuilder().setAll(id, officialEmail, name, surname, it.unibo.paserver.domain.Gender.getRandom(), new LocalDate(), "", loremIpsum.getWords(1), loremIpsum.getWords(1), loremIpsum.getWords(1),
						loremIpsum.getWords(1), Math.random() + "", loremIpsum.getWords(1), "", it.unibo.paserver.domain.DocumentIdType.getRandom(), "", UniCourse.getRandom(), (Boolean) false, (Integer) 0, "", loremIpsum.getParagraphs(1),
						null, null, null, null).setCredentials(officialEmail, password);
				userService.save(ub.build(true));
			}
		});

		EntityBuilderManager.clearEntityManager();
	}

	/*
	 * private Set<Sensor> getAllSensors(){ HashSet<Sensor> set = new
	 * HashSet<Sensor>(); for (SensorType stype : SensorType.values()) { Sensor
	 * sensor = new Sensor(); MinimalSensorDetail msd = new MinimalSensorDetail();
	 * msd.setSensorType(stype); sensor.setSensorDetail(msd); set.add(sensor); }
	 * return set; }
	 */

	/*
	 * private void addAllSensorToDevice(Devices device) { for (SensorType stype :
	 * SensorType.values()) { //System.out.println(stype); Sensor sensor = new
	 * Sensor(); sensor.setSensorType(stype); sensor.setDevice(device);
	 * //sensorService.save(sensor); } }
	 */
}
