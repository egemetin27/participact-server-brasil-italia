package it.unibo.paserver.rest.controller.v2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.QuestionnaireResponseBuilder;
import it.unibo.paserver.rest.controller.v1.ResultDataController;
import it.unibo.paserver.service.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Recebimento dos dados coletados no aplicativo
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@RestController
public class CampaignReceiveDataRestfulController extends ApplicationRestfulController {
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

    /**
     * ACCELEROMETER
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/accelerometer"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest accelerometer(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    DataAccelerometer data = new DataAccelerometer();
                    // Vars
                    data.setX(netData.get("acceleration_x").getAsFloat());
                    data.setY(netData.get("acceleration_y").getAsFloat());
                    data.setZ(netData.get("acceleration_z").getAsFloat());
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            // e.printStackTrace(System.out);
            System.out.println("Error while receiving data in ACCELEROMETER" + e.getMessage());
        }
        // Result
        return response;
    }

    /**
     * ACCELEROMETER CLASSIFIER
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/accelerometerclassifier"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest accelerometerClassifier(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataAccelerometerClassifier data = new DataAccelerometerClassifier();
                    // Vars
                    data.setValue(netData.get("value").getAsString());
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in ACCELEROMETERCLASSIFIER" + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * DR
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/dr"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest dr(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataDR data = new DataDR();
                    // Vars
                    data.setAccuracy(netData.get("accuracy").getAsFloat());
                    data.setLatitude(netData.get("latitude").getAsFloat());
                    data.setLongitude(netData.get("longitude").getAsFloat());

                    data.setPole(netData.get("pole").getAsString());
                    data.setState(netData.get("state").getAsString());
                    data.setStatus(netData.get("status").getAsString());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in DR " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * ACTIVITY RECOGNITION COMPARE
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/activityrecognitioncompare"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest activityRecognitionCompare(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataActivityRecognitionCompare data = new DataActivityRecognitionCompare();
                    // Vars
                    data.setGoogleArConfidence(netData.get("googleArConfidence").getAsInt());
                    data.setGoogleArTimestamp(new DateTime(netData.get("googleArTimestamp").getAsLong()));
                    data.setGoogleArValue(netData.get("googleArValue").getAsString());

                    data.setMostArTimestamp(new DateTime(netData.get("mostArTimestamp").getAsLong()));
                    data.setMostArValue(netData.get("mostArValue").getAsString());
                    data.setUserActivity(netData.get("userActivity").getAsString());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in ACTIVITYRECOGNITIONCOMPARE " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * APP ON SCREEN
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/apponscreen"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest appOnScreen(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataAppOnScreen data = new DataAppOnScreen();
                    // Vars
                    data.setAppName(netData.get("appname").getAsString());
                    data.setEndTime(new DateTime(netData.get("endTime").getAsLong()));
                    data.setStartTime(new DateTime(netData.get("startTime").getAsLong()));
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in APPONSCREEN " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * APPS NET TRAFFIC
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/appsnettraffic"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest appsNetTraffic(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataAppNetTraffic data = new DataAppNetTraffic();
                    // Vars
                    data.setName(netData.get("appname").getAsString());
                    data.setRxBytes(netData.get("rxBytes").getAsLong());
                    data.setTxBytes(netData.get("txBytes").getAsLong());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in APPSNETTRAFFIC " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * BATTERY
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/battery"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest battery(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataBattery data = new DataBattery();
                    // Vars
                    data.setHealth(netData.has("health") ? netData.get("health").getAsInt() : 0);
                    data.setPlugged(netData.has("plugged") ? netData.get("plugged").getAsInt() : 0);
                    data.setScale(netData.has("scale") ? netData.get("scale").getAsInt() : 0);
                    data.setTemperature(netData.has("temperature") ? netData.get("temperature").getAsInt() : 0);

                    data.setLevel(netData.has("batteryLevel") ? netData.get("batteryLevel").getAsInt() : 0);
                    data.setStatus(netData.has("batteryState") ? netData.get("batteryState").getAsInt() : 0);
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in BATTERY " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * BLUETOOTH
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/bluetooth"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest bluetooth(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataBluetooth data = new DataBluetooth();
                    // Vars
                    data.setDeviceClass(netData.get("deviceClass").getAsInt());
                    data.setFriendlyName(netData.get("friendlyName").getAsString());
                    data.setMac(netData.get("mac").getAsString());
                    data.setMajorClass(netData.get("majorClass").getAsInt());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in BLUETOOTH " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * CONNECTION TYPE
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/connectiontype"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest connectionType(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataConnectionType data = new DataConnectionType();
                    // Vars
                    data.setConnectionType(netData.get("connectionType").getAsString());
                    data.setMobileNetworkType(netData.get("carrierName").getAsString());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in CONNECTIONTYPE " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * CELL
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/cell"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest cell(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
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
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in CELL " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * DEVICE NET TRAFFIC
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/devicenettraffic"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest deviceNetTraffic(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataDeviceNetTraffic data = new DataDeviceNetTraffic();
                    // Vars
                    data.setRxBytes(netData.has("rxBytes") ? netData.get("rxBytes").getAsLong() : 0L);
                    data.setTxBytes(netData.has("txBytes") ? netData.get("txBytes").getAsLong() : 0L);

                    data.setNetworkUptime(netData.has("uptime") ? netData.get("uptime").getAsFloat() : 0L);
                    data.setRxPackets(netData.has("rxPackets") ? netData.get("rxPackets").getAsLong() : 0L);
                    data.setTxPackets(netData.has("txPackets") ? netData.get("txPackets").getAsLong() : 0L);
                    data.setNetworkInterface(netData.has("interface") ? netData.get("interface").getAsString() : "N/A");
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in DEVICENETTRAFFIC " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * GOOGLE ACTIVITY RECOGNITION
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/googleactivityrecognition"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest googleActivityRecognition(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataGoogleActivityRecognition data = new DataGoogleActivityRecognition();
                    // Vars
                    data.setConfidence(netData.has("confidence") ? netData.get("confidence").getAsInt() : 0);
                    data.setStationary(netData.has("stationary") ? netData.get("stationary").getAsInt() : 0);
                    data.setWalking(netData.has("walking") ? netData.get("walking").getAsInt() : 0);
                    data.setRunning(netData.has("running") ? netData.get("running").getAsInt() : 0);
                    data.setAutomotive(netData.has("automotive") ? netData.get("automotive").getAsInt() : 0);
                    data.setUnknown(netData.has("unknown") ? netData.get("unknown").getAsInt() : 0);

                    data.setClassifier_value(netData.has("recognizedActivity") ? "N/A" : netData.get("recognizedActivity").getAsString());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in GOOGLEACTIVITYRECOGNITION " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * GYROSCOPE
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/gyroscope"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest gyroscope(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataGyroscope data = new DataGyroscope();
                    // Vars
                    data.setRotationX(netData.get("rotation_x").getAsInt());
                    data.setRotationY(netData.get("rotation_y").getAsInt());
                    data.setRotationZ(netData.get("rotation_z").getAsInt());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in GYROSCOPE " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * INSTALLED APPS
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/installedapps"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest installedApps(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataInstalledApps data = new DataInstalledApps();
                    // Vars
                    data.setPkgName(netData.get("pkgName").getAsString());
                    data.setRequestedPermissions(netData.get("requestedPermissions").getAsString());
                    data.setVersionCode(netData.get("versionCode").getAsFloat());
                    data.setVersionName(netData.get("versionName").getAsString());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in INSTALLEDAPPS " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * LIGHT
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/light"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest light(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataLight data = new DataLight();
                    // Vars
                    data.setValue(netData.get("value").getAsFloat());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in LIGHT " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * LOCATION
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/location"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest location(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataLocation data = new DataLocation();
                    // Vars
                    data.setAccuracy(netData.has("accuracy") ? netData.get("accuracy").getAsFloat() : 0);
                    data.setLatitude(netData.has("latitude") ? netData.get("latitude").getAsFloat() : 0);
                    data.setLongitude(netData.has("longitude") ? netData.get("longitude").getAsFloat() : 0);
                    data.setAltitude(netData.has("altitude") ? netData.get("altitude").getAsFloat() : 0);
                    data.setProvider(netData.has("provider") ? netData.get("provider").getAsString() : "N/A");

                    data.setHorizontalAccuracy(netData.has("horizontalAccuracy") ? netData.get("horizontalAccuracy").getAsFloat() : 0);
                    data.setVerticalAccuracy(netData.has("verticalAccuracy") ? netData.get("verticalAccuracy").getAsFloat() : 0);
                    data.setCourse(netData.has("course") ? netData.get("course").getAsFloat() : 0);
                    data.setSpeed(netData.has("speed") ? netData.get("speed").getAsFloat() : 0);
                    data.setFloor(netData.has("floor") ? netData.get("floor").getAsFloat() : 0);

                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in LOCATION " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * MAGNETIC FIELD
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/magneticfield"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest magneticField(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataMagneticField data = new DataMagneticField();
                    // Vars
                    data.setMagneticFieldX(netData.get("magneticField_x").getAsFloat());
                    data.setMagneticFieldY(netData.get("magneticField_y").getAsFloat());
                    data.setMagneticFieldZ(netData.get("magneticField_z").getAsFloat());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in MAGNETICFIELD " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * PHONE CALL DURATION
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/phonecallduration"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest phoneCallDuration(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataPhoneCallDuration data = new DataPhoneCallDuration();
                    // Vars
                    data.setCallEnd(new DateTime(netData.has("callEnd") ? netData.get("callEnd").getAsLong() * 1000L : System.currentTimeMillis()));
                    data.setCallStart(new DateTime(netData.has("callStart") ? netData.get("callStart").getAsLong() * 1000L : System.currentTimeMillis()));
                    data.setIsIncoming(netData.get("isIncoming").getAsBoolean());
                    data.setPhoneNumber(netData.get("phoneNumber").getAsString());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in PHONECALLDURATION " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * PHONE CALL EVENT
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/phonecallevent"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest phoneCallEvent(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataPhoneCallEvent data = new DataPhoneCallEvent();
                    // Vars
                    data.setIsIncomingCall(netData.get("isIncoming").getAsBoolean());
                    data.setIsStart(netData.get("isStart").getAsBoolean());
                    data.setPhoneNumber(netData.get("phoneNumber").getAsString());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in PHONECALLEVENT " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * SYSTEM STATS
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/systemstats"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest systemStats(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
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
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in SYSTEMSTATS " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * WIFI SCAN
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/receive-data/wifiscan"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest wifiScan(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);

            JsonArray dataList = r.getAsJsonArray("data");
            DateTime now = new DateTime();
            List<Data> resultData = new ArrayList<Data>();
            for (JsonElement elm : dataList) {
                if (elm.isJsonObject()) {
                    JsonObject netData = elm.getAsJsonObject();
                    // Clazz
                    DataWifiScan data = new DataWifiScan();
                    // Vars
                    data.setBssid(netData.get("bssid").getAsString());
                    data.setCapabilities(netData.get("capabilities").getAsString());
                    data.setFrequency(netData.get("frequency").getAsInt());
                    data.setLevel(netData.get("level").getAsInt());
                    data.setSsid(netData.get("ssid").getAsString());
                    // User
                    data.setUser(getUser());
                    // Date
                    DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Add
                    resultData.add(data);
                }
            }
            // Save
            if (resultData.size() > 0) {
                if (dataService.save(resultData) != null) {
                    response.setStatus(true);
                    response.setMessage(null);
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in WIFISCAN " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * QUESTION
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value = {"/api/v2/protected/receive-data/question"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest question(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
            //CATALINA.OUT JSON
            System.out.println(json);
            //Async
            new Thread(() -> {
                this.questionSave(r);
            }).start();
            //Return
            response.setStatus(true);
            response.setMessage(null);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in QUESTION " + e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    @Async
    private void questionSave(ReceiveJson r) {
        JsonArray dataList = r.getAsJsonArray("data");
        DateTime now = new DateTime();
        Map<Long, List<Data>> taskIdToDataToAdd = new TreeMap<Long, List<Data>>();
        for (JsonElement elm : dataList) {
            if (elm.isJsonObject()) {
                JsonObject netData = elm.getAsJsonObject();
                // Begin Loop
                Long actionId = netData.get("actionId").getAsLong();
                Long taskId = netData.get("taskId").getAsLong();
                Long questionId = netData.get("questionId").getAsLong();
                int typeId = netData.has("type") ? netData.get("type").getAsInt() : 0;

                DateTime s = new DateTime(netData.has("timestamp") ? netData.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                Long answerId = netData.has("answerId") ? netData.get("answerId").getAsLong() : 0L;
                boolean closedAnswerValue = netData.has("closedAnswerValue") ? netData.get("closedAnswerValue").getAsBoolean() : true;
                String openAnswerValue = netData.has("openAnswerValue") ? netData.get("openAnswerValue").getAsString() : "N/A";

                double accuracy = netData.has("accuracy") ? netData.get("accuracy").getAsFloat() : 0;
                double latitude = netData.has("latitude") ? netData.get("latitude").getAsFloat() : 0;
                double longitude = netData.has("longitude") ? netData.get("longitude").getAsFloat() : 0;
                double altitude = netData.has("altitude") ? netData.get("altitude").getAsFloat() : 0;
                String provider = netData.has("provider") ? netData.get("provider").getAsString() : "N/A";
                String ipAddress = netData.has("ipAddress") ? netData.get("ipAddress").getAsString() : "::0";
                String answerGroupId = netData.has("answerGroupId") ? netData.get("answerGroupId").getAsString() : "N/A";
                try {
                    System.out.println(netData.toString());
                    System.out.println(" typeId " + typeId);
                } catch (Exception e) {
                }


                ActionQuestionaire questionnaire = (ActionQuestionaire) actionService.findById(actionId);
                if (typeId == 0) {
                    DataQuestionaireOpenAnswer data = new DataQuestionaireOpenAnswer();
                    // Date
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Vars
                    data.setOpenAnswerValue(openAnswerValue);
                    Question q = resultDataController.getQuestionById(questionnaire, questionId);
                    data.setQuestion(q);
                    data.setUser(getUser());
                    // Merge
                    Data savedData = dataService.merge(data);
                    // Data
                    resultDataController.addData(taskIdToDataToAdd, taskId, savedData);
                    // Pre consolidando
                    try {
                        QuestionnaireResponseBuilder qrb = new QuestionnaireResponseBuilder();
                        qrb.setAll(taskId, actionId, questionId, getUserId(), openAnswerValue, 0L, true, false);
                        qrb.setAccuracy(accuracy);
                        qrb.setLatitude(latitude);
                        qrb.setLongitude(longitude);
                        qrb.setAltitude(altitude);
                        qrb.setProvider(provider);
                        qrb.setAnswerGroupId(answerGroupId);
                        qrb.setIpAddress(ipAddress);
                        QuestionnaireResponse qr = qrb.build(true);
                        if (questionnaire.getRepeat()) {
                            questionnaireResponseService.save(qr);
                        } else {
                            questionnaireResponseService.saveOrUpdate(qr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace(System.out);
                    }
                } else if (typeId == 1) {
                    // answer to closed question
                    DataQuestionaireClosedAnswer data = new DataQuestionaireClosedAnswer();
                    // Date
                    data.setSampleTimestamp(s);
                    data.setDataReceivedTimestamp(now);
                    // Vars
                    Question q = resultDataController.getQuestionById(questionnaire, questionId);
                    ClosedAnswer ca = resultDataController.getClosedAnswerById(q, answerId);
                    if (ca != null) {
                        //Add
                        data.setClosedAnswer(ca);
                        data.setClosedAnswerValue(closedAnswerValue);
                        data.setUser(getUser());
                        //Save
                        Data savedData = dataService.merge(data);
                        resultDataController.addData(taskIdToDataToAdd, taskId, savedData);
                        // Pre consolidando
                        try {
                            QuestionnaireResponseBuilder qrb = new QuestionnaireResponseBuilder();
                            qrb.setAll(taskId, actionId, questionId, getUserId(), ca.getAnswerDescription(), answerId, closedAnswerValue, true);
                            qrb.setAccuracy(accuracy);
                            qrb.setLatitude(latitude);
                            qrb.setLongitude(longitude);
                            qrb.setAltitude(altitude);
                            qrb.setProvider(provider);
                            qrb.setAnswerGroupId(answerGroupId);
                            qrb.setIpAddress(ipAddress);
                            QuestionnaireResponse qr = qrb.build(true);
                            if (questionnaire.getRepeat()) {
                                questionnaireResponseService.save(qr);
                            } else {
                                questionnaireResponseService.saveOrUpdate(qr);
                            }
                            //Increment
                            questionnaireResponseService.incrementClosedAnswer(questionId, answerId);
                        } catch (Exception e) {
                            e.printStackTrace(System.out);
                        }
                    }
                } else {
                    System.out.println("Received malformed questionnaire answer, ignoring");
                }
                // End Loop
            }
        }

        try {
            for (Long taskId : taskIdToDataToAdd.keySet()) {
                System.out.println(taskId);
                System.out.println(getUserId());
                System.out.println(taskIdToDataToAdd.get(taskId));
                taskResultService.addData(taskId, getUserId(), taskIdToDataToAdd.get(taskId));
            }
        } catch (Exception e) {
            //Descartando
            System.out.println(String.format("%s", e.getMessage()));
        }
    }

    /**
     * PHOTO
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = {"/api/v2/protected/receive-data/photo"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest photo(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in PHOTO " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * AUDIO
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = {"/api/v2/protected/receive-data/audio"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest audio(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in AUDIO " + e.getMessage());
            // e.printStackTrace(System.out);
        }

        // Result
        return response;
    }

    /**
     * VIDEO
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = {"/api/v2/protected/receive-data/video"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest video(@RequestBody String json, HttpServletRequest request) throws Exception {
        // User Logged
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Validate
        try {
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            System.out.println("Error while receiving data in VIDEO " + e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }
}
