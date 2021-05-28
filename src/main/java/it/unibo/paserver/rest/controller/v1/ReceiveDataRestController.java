package it.unibo.paserver.rest.controller.v1;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.ClosedAnswer;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataAccelerometer;
import it.unibo.paserver.domain.DataAccelerometerClassifier;
import it.unibo.paserver.domain.DataActivityRecognitionCompare;
import it.unibo.paserver.domain.DataAppNetTraffic;
import it.unibo.paserver.domain.DataAppOnScreen;
import it.unibo.paserver.domain.DataBattery;
import it.unibo.paserver.domain.DataBluetooth;
import it.unibo.paserver.domain.DataCell;
import it.unibo.paserver.domain.DataConnectionType;
import it.unibo.paserver.domain.DataDR;
import it.unibo.paserver.domain.DataDeviceNetTraffic;
import it.unibo.paserver.domain.DataGoogleActivityRecognition;
import it.unibo.paserver.domain.DataGyroscope;
import it.unibo.paserver.domain.DataInstalledApps;
import it.unibo.paserver.domain.DataLight;
import it.unibo.paserver.domain.DataLocation;
import it.unibo.paserver.domain.DataMagneticField;
import it.unibo.paserver.domain.DataPhoneCallDuration;
import it.unibo.paserver.domain.DataPhoneCallEvent;
import it.unibo.paserver.domain.DataPhoto;
import it.unibo.paserver.domain.DataQuestionaireClosedAnswer;
import it.unibo.paserver.domain.DataQuestionaireOpenAnswer;
import it.unibo.paserver.domain.DataSystemStats;
import it.unibo.paserver.domain.DataWifiScan;
import it.unibo.paserver.domain.Question;
import it.unibo.paserver.domain.QuestionnaireResponse;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseMessage;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.QuestionnaireResponseBuilder;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.DataService;
import it.unibo.paserver.service.QuestionnaireResponseService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.UserService;

@Controller
public class ReceiveDataRestController {
	@Autowired
	UserService userService;
	@Autowired
	DataService dataService;
	@Autowired
	ActionService actionService;
	@Autowired
	TaskService taskService;
	@Autowired
	TaskResultService taskResultService;
	@Autowired
	ResultDataController resultDataController;
	@Autowired
	QuestionnaireResponseService questionnaireResponseService;

	// Com dados genericos
	/**
	 * Accelerometer
	 * 
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/accelerometer", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataAccelerometer(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataAccelerometer data = new DataAccelerometer();
						// Vars
						data.setX(netData.get("x").getAsFloat());
						data.setY(netData.get("y").getAsFloat());
						data.setZ(netData.get("z").getAsFloat());
						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataAccelerometer" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Accelerometer Classifier
	 * 
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/accelerometerClassifier", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataaccelerometerClassifier(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataAccelerometerClassifier data = new DataAccelerometerClassifier();
						// Vars
						data.setValue(netData.get("value").getAsString());
						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataaccelerometerClassifier" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * DR
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/dr", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataDR(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataDR data = new DataDR();
						// Vars
						data.setAccuracy(netData.get("accuracy").getAsFloat());
						data.setLatitude(netData.get("latitude").getAsFloat());
						data.setLongitude(netData.get("longitude").getAsFloat());

						data.setPole(netData.get("pole").getAsString());
						data.setState(netData.get("state").getAsString());
						data.setStatus(netData.get("status").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataDR" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Activity Recognition Compare
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/activityrecognitioncompare", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataActivityRecognitionCompare(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataActivityRecognitionCompare data = new DataActivityRecognitionCompare();
						// Vars
						data.setGoogleArConfidence(netData.get("googleArConfidence").getAsInt());
						data.setGoogleArTimestamp(new DateTime(netData.get("googleArTimestamp").getAsLong()));
						data.setGoogleArValue(netData.get("googleArValue").getAsString());

						data.setMostArTimestamp(new DateTime(netData.get("mostArTimestamp").getAsLong()));
						data.setMostArValue(netData.get("mostArValue").getAsString());
						data.setUserActivity(netData.get("userActivity").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataActivityRecognitionCompare" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * App On Screen
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/apponscreen", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataAppOnScreen(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataAppOnScreen data = new DataAppOnScreen();
						// Vars
						data.setAppName(netData.get("appname").getAsString());
						data.setEndTime(new DateTime(netData.get("endTime").getAsLong()));
						data.setStartTime(new DateTime(netData.get("startTime").getAsLong()));

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataAppOnScreen" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Apps Net Traffic
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/appsnettraffic", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataAppsNetTraffic(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataAppNetTraffic data = new DataAppNetTraffic();
						// Vars
						data.setName(netData.get("appname").getAsString());
						data.setRxBytes(netData.get("endTime").getAsLong());
						data.setTxBytes(netData.get("startTime").getAsLong());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataAppsNetTraffic" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Battery
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/battery", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataBattery(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataBattery data = new DataBattery();
						// Vars
						data.setHealth(netData.get("health").getAsInt());
						data.setLevel(netData.get("level").getAsInt());
						data.setPlugged(netData.get("plugged").getAsInt());
						data.setScale(netData.get("scale").getAsInt());
						data.setStatus(netData.get("status").getAsInt());
						data.setTemperature(netData.get("temperature").getAsInt());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataBattery" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Bluetooth
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/bluetooth", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataBluetooth(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataBluetooth data = new DataBluetooth();
						// Vars
						data.setDeviceClass(netData.get("deviceClass").getAsInt());
						data.setFriendlyName(netData.get("friendlyName").getAsString());
						data.setMac(netData.get("mac").getAsString());
						data.setMajorClass(netData.get("majorClass").getAsInt());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataBluetooth" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Connection Type
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/connectiontype", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataConnectionType(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataConnectionType data = new DataConnectionType();
						// Vars
						data.setConnectionType(netData.get("connectionType").getAsString());
						data.setMobileNetworkType(netData.get("mobileNetworkType").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataConnectionType" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Cell
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/cell", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataCell(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataCell data = new DataCell();
						// Vars
						data.setBaseNetworkId(netData.get("baseNetworkId").getAsInt());
						data.setBaseStationId(netData.get("baseStationId").getAsInt());
						data.setBaseStationLatitude(netData.get("baseStationLatitude").getAsInt());
						data.setBaseStationLongitude(netData.get("baseStationLongitude").getAsInt());
						data.setBaseSystemId(netData.get("baseSystemId").getAsInt());
						data.setGsmCellId(netData.get("gsmCellId").getAsInt());
						data.setGsmLac(netData.get("gsmLac").getAsInt());
						data.setPhoneType(netData.get("phoneType").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataCell" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Device Net Traffic
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/devicenettraffic", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataDeviceNetTraffic(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataDeviceNetTraffic data = new DataDeviceNetTraffic();
						// Vars
						data.setRxBytes(netData.get("rxBytes").getAsLong());
						data.setTxBytes(netData.get("txBytes").getAsLong());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataDeviceNetTraffic" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Google Activity Recognition
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/googleactivityrecognition", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataGoogleActivityRecognition(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());

		System.out.println(json.toString());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");

				System.out.println(json.toString());

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataGoogleActivityRecognition data = new DataGoogleActivityRecognition();
						// Vars
						data.setConfidence(netData.has("confidence") ? netData.get("confidence").getAsInt() : 0);
						data.setClassifier_value(netData.get("recognizedActivity").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataGoogleActivityRecognition" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				e.printStackTrace(System.out);
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Gyroscope
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/gyroscope", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataGyroscope(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataGyroscope data = new DataGyroscope();
						// Vars
						data.setRotationX(netData.get("rotationX").getAsInt());
						data.setRotationY(netData.get("rotationY").getAsInt());
						data.setRotationZ(netData.get("rotationZ").getAsInt());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataGyroscope" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Installed Apps
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/installedapps", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataInstalledApps(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataInstalledApps data = new DataInstalledApps();
						// Vars
						data.setPkgName(netData.get("pkgName").getAsString());
						data.setRequestedPermissions(netData.get("requestedPermissions").getAsString());
						data.setVersionCode(netData.get("versionCode").getAsFloat());
						data.setVersionName(netData.get("versionName").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataInstalledApps" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Light
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/light", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataLight(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataLight data = new DataLight();
						// Vars
						data.setValue(netData.get("value").getAsFloat());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataLight" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Data Location
	 * 
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/location", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataLocation(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataLocation data = new DataLocation();
						// Vars
						data.setAccuracy(netData.get("accuracy").getAsDouble());
						data.setLatitude(netData.get("latitude").getAsDouble());
						data.setLongitude(netData.get("longitude").getAsDouble());
						data.setProvider(netData.get("provider").getAsString());
						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataLocation" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Magnetic Field
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/magneticfield", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataMagneticField(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataMagneticField data = new DataMagneticField();
						// Vars
						data.setMagneticFieldX(netData.get("magneticFieldX").getAsFloat());
						data.setMagneticFieldY(netData.get("magneticFieldY").getAsFloat());
						data.setMagneticFieldZ(netData.get("magneticFieldZ").getAsFloat());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataMagneticField" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Phone Call Duration
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/phonecallduration", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataPhoneCallDuration(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataPhoneCallDuration data = new DataPhoneCallDuration();
						// Vars
						data.setCallEnd(new DateTime(netData.get("callEnd").getAsFloat()));
						data.setCallStart(new DateTime(netData.get("callStart").getAsFloat()));
						data.setIsIncoming(netData.get("isIncoming").getAsBoolean());
						data.setPhoneNumber(netData.get("phoneNumber").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataPhoneCallDuration" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Phone Call Event
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/phonecallevent", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataPhoneCallEvent(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataPhoneCallEvent data = new DataPhoneCallEvent();
						// Vars
						data.setIsIncomingCall(netData.get("isIncoming").getAsBoolean());
						data.setIsStart(netData.get("isStart").getAsBoolean());
						data.setPhoneNumber(netData.get("phoneNumber").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataPhoneCallEvent" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * System Stats
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/systemstats", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataSystemStats(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataSystemStats data = new DataSystemStats();
						// Vars
						data.setBOOT_TIME(netData.get("BOOTTIME").getAsLong());
						data.setCONTEXT_SWITCHES(netData.get("CONTEXTSWITCHES").getAsLong());
						data.setCPU_FREQUENCY(netData.get("CPUFREQUENCY").getAsFloat());
						data.setCPU_HARDIRQ(netData.get("CPUHARDIRQ").getAsLong());
						data.setCPU_IDLE(netData.get("CPUIDLE").getAsLong());
						data.setCPU_IOWAIT(netData.get("CPUIOWAIT").getAsLong());
						data.setCPU_NICED(netData.get("CPUNICED").getAsLong());
						data.setCPU_SOFTIRQ(netData.get("CPUSOFTIRQ").getAsLong());
						data.setCPU_SYSTEM(netData.get("CPUSYSTEM").getAsLong());
						data.setCPU_USER(netData.get("CPUUSER").getAsLong());
						data.setMEM_ACTIVE(netData.get("MEMACTIVE").getAsLong());
						data.setMEM_FREE(netData.get("MEMFREE").getAsLong());
						data.setMEM_INACTIVE(netData.get("MEMINACTIVE").getAsLong());
						data.setMEM_TOTAL(netData.get("MEMTOTAL").getAsLong());
						data.setPROCESSES(netData.get("PROCESSES").getAsLong());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataSystemStats" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Wifi Scan
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/wifiscan", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataWifiScan(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						DataWifiScan data = new DataWifiScan();
						// Vars
						data.setBssid(netData.get("bssid").getAsString());
						data.setCapabilities(netData.get("capabilities").getAsString());
						data.setFrequency(netData.get("frequency").getAsInt());
						data.setLevel(netData.get("level").getAsInt());
						data.setSsid(netData.get("ssid").getAsString());

						data.setUser(user);
						// Date
						DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
						data.setSampleTimestamp(s);
						data.setDataReceivedTimestamp(now);
						// Add
						resultData.add(data);
					}
				}

				if (resultData.size() > 0) {
					dataService.save(resultData);
					response.setResultCode(HttpStatus.OK.value());
					response.setProperty("status", "true");
				}
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataWifiScan" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	// Com dados especificos
	/**
	 * Photo
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/photo", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataPhoto(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				DateTime now = new DateTime();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();

						Long taskId = netData.get("taskId").getAsLong();
						Task task = taskService.findById(taskId);
						Action action = actionService.findById(netData.get("actionId").getAsLong());
						if (task != null || action != null) {
							DataPhoto data = new DataPhoto();
							// Vars
							data.setActionId(netData.get("actionId").getAsLong());
							data.setTaskId(netData.get("taskId").getAsLong());
							data.setHeight(netData.get("height").getAsInt());
							data.setWidth(netData.get("width").getAsInt());

							data.setUser(user);
							// Image
							BinaryDocument bd = new BinaryDocument();
							byte[] backToBytes = Base64.decodeBase64(netData.get("image").getAsString().toString());
							bd.setContent(backToBytes);
							bd.setContentType("image");
							bd.setContentSubtype("jpg");
							bd.setCreated(now);
							String filename = String.format("task_%09d_action_%09d_%s.jpg", data.getTaskId(), data.getActionId(), user.getOfficialEmail());
							bd.setFilename(filename);
							bd.setOwner(user);
							data.setFile(bd);
							// Date
							DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
							data.setSampleTimestamp(s);
							data.setDataReceivedTimestamp(now);
							// Add
							data = dataService.merge(data);
							taskResultService.addData(taskId, user.getId(), data);
						}
					}
				}

				response.setResultCode(HttpStatus.OK.value());
				response.setProperty("status", "true");
			} catch (Exception e) {
				System.out.println("Error while receiving data in photo" + e.getMessage());
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}

	/**
	 * Questionarion
	 * 
	 * @param json
	 * @param request
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/rest/json/question", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage receiveDataQuestion(@RequestBody String json, HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(resultDataController.HEADER_KEY);
				response.setKey(key);
				// Request
				ReceiveJson r = new ReceiveJson(json);
				JsonArray dataList = r.getAsJsonArray("list");
				Map<Long, List<Data>> taskIdToDataToAdd = new TreeMap<Long, List<Data>>();

				DateTime now = new DateTime();
				for (JsonElement elm : dataList) {
					if (elm.isJsonObject()) {
						JsonObject netData = elm.getAsJsonObject();
						// Begin Loop
						Long actionId = netData.get("actionId").getAsLong();
						Long taskId = netData.get("taskId").getAsLong();
						int typeId = netData.has("type") ? netData.get("type").getAsInt() : 0;
						// System.out.println(netData.toString());
						ActionQuestionaire questionnaire = (ActionQuestionaire) actionService.findById(actionId);
						if (typeId == 0) {
							DataQuestionaireOpenAnswer data = new DataQuestionaireOpenAnswer();
							// Date
							DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
							data.setSampleTimestamp(s);
							data.setDataReceivedTimestamp(now);
							// Vars
							String openAnswerValue = netData.get("openAnswerValue").getAsString();
							data.setOpenAnswerValue(openAnswerValue);
							Long questionId = netData.get("questionId").getAsLong();
							Question q = resultDataController.getQuestionById(questionnaire, questionId);
							data.setQuestion(q);
							data.setUser(user);
							// Merge
							Data savedData = dataService.merge(data);
							// Data
							resultDataController.addData(taskIdToDataToAdd, taskId, savedData);
							// Pre consolidando
							try {
								QuestionnaireResponseBuilder qrb = new QuestionnaireResponseBuilder();
								QuestionnaireResponse qr = qrb.setAll(taskId, actionId, questionId, user.getId(), openAnswerValue, 0L, true, false).build(true);
								questionnaireResponseService.saveOrUpdate(qr);
							} catch (Exception e) {
								e.printStackTrace(System.out);
							}
						} else if (typeId == 1) {
							// answer to closed question
							DataQuestionaireClosedAnswer data = new DataQuestionaireClosedAnswer();
							// Date
							DateTime s = new DateTime(netData.get("sampleTime").getAsLong());
							data.setSampleTimestamp(s);
							data.setDataReceivedTimestamp(now);
							// Vars
							Long questionId = netData.get("questionId").getAsLong();
							Question q = resultDataController.getQuestionById(questionnaire, questionId);
							Long answerId = netData.get("answerId").getAsLong();
							ClosedAnswer ca = resultDataController.getClosedAnswerById(q, answerId);
							data.setClosedAnswer(ca);
							boolean closedAnswerValue = netData.get("closedAnswerValue").getAsBoolean();
							data.setClosedAnswerValue(closedAnswerValue);
							data.setUser(user);

							Data savedData = dataService.merge(data);
							resultDataController.addData(taskIdToDataToAdd, taskId, savedData);
							// Pre consolidando
							try {
								QuestionnaireResponseBuilder qrb = new QuestionnaireResponseBuilder();
								QuestionnaireResponse qr = qrb.setAll(taskId, actionId, questionId, user.getId(), ca.getAnswerDescription(), answerId, closedAnswerValue, true).build(true);
								questionnaireResponseService.saveOrUpdate(qr);
							} catch (Exception e) {
								e.printStackTrace(System.out);
							}
						} else {
							System.out.println("Received malformed questionnaire answer, ignoring");
						}
						// End Loop
					}
				}

				for (Long taskId : taskIdToDataToAdd.keySet()) {
					taskResultService.addData(taskId, user.getId(), taskIdToDataToAdd.get(taskId));
				}

				response.setResultCode(HttpStatus.OK.value());
				response.setProperty("status", "true");
			} catch (Exception e) {
				System.out.println("Error while receiving data in receiveDataQuestion" + e.getMessage());
				e.printStackTrace(System.out);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		return response;
	}
}