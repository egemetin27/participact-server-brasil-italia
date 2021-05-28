package it.unibo.paserver.rest.controller.v2;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.*;
import com.google.gson.JsonObject;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.QuestionnaireResponseBuilder;
import it.unibo.paserver.service.*;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsavel pelas APIs de Upload
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@Controller
public class StorageFileRestful extends ApplicationRestfulController {
    @Autowired
    TaskResultService taskResultService;
    @Autowired
    DataService dataService;
    @Autowired
    QuestionnaireResponseService questionnaireResponseService;
    @Autowired
    private StorageFileService storageFileService;
    @Autowired
    private FeedbackReportService feedbackReportService;
    @Autowired
    private IssueReportService issueReportService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = {"/api/v2/protected/storage-file/image"}, method = RequestMethod.POST, headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"})
    public @ResponseBody
    ResponseJsonRest imageHeader(@RequestPart(value = "uploadFile", required = false) MultipartFile[] uploadingFiles, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        try {
            //Details/Info
            String hrId = request.getHeader("x-storage-report-id");
            Long rId = !Validator.isEmptyString(hrId) ? Long.parseLong(hrId) : 0L;

            String haId = request.getHeader("x-storage-action-id");
            Long aId = !Validator.isEmptyString(haId) ? Long.parseLong(haId) : 0L;

            String htId = request.getHeader("x-storage-task-id");
            Long tId = !Validator.isEmptyString(htId) ? Long.parseLong(htId) : 0L;

            String hpId = request.getHeader("x-storage-profile-id");
            Long pId = !Validator.isEmptyString(hpId) ? Long.parseLong(hpId) : 0L;

            String hfId = request.getHeader("x-storage-feedback-id");
            Long fId = !Validator.isEmptyString(hfId) ? Long.parseLong(hfId) : 0L;

            String hqId = request.getHeader("x-storage-question-id");
            Long qId = !Validator.isEmptyString(hqId) ? Long.parseLong(hqId) : 0L;

            //Location
            String hAccuracy = request.getHeader("x-storage-accuracy");
            Float accuracy = !Validator.isEmptyString(hAccuracy) ? Float.parseFloat(hAccuracy) : 0F;

            String hLatitude = request.getHeader("x-storage-latitude");
            Float latitude = !Validator.isEmptyString(hLatitude) ? Float.parseFloat(hLatitude) : 0F;

            String hLongitude = request.getHeader("x-storage-longitude");
            Float longitude = !Validator.isEmptyString(hLongitude) ? Float.parseFloat(hLongitude) : 0F;

            String hAltitude = request.getHeader("x-storage-altitude");
            Float altitude = !Validator.isEmptyString(hAltitude) ? Float.parseFloat(hAltitude) : 0F;

            String hProvider = request.getHeader("x-storage-provider");
            String provider = !Validator.isEmptyString(hProvider) ? hProvider : "N/A";

            String hip = request.getHeader("x-storage-ip-address");
            String ip = !Validator.isEmptyString(hip) ? hip : "::0";

            String hAnswerGroupId = request.getHeader("x-storage-answer-group-id");
            String answerGroupId = !Validator.isEmptyString(hAnswerGroupId) ? hAnswerGroupId : "N/A";
            // Json
            JsonObject json = new JsonObject();
            json.addProperty("reportId", rId);
            json.addProperty("feedbackReportId", fId);
            json.addProperty("actionId", aId);
            json.addProperty("taskId", tId);
            json.addProperty("profileId", pId);
            json.addProperty("questionId", qId);

            json.addProperty("accuracy", accuracy);
            json.addProperty("latitude", latitude);
            json.addProperty("longitude", longitude);
            json.addProperty("altitude", altitude);
            json.addProperty("provider", provider);
            json.addProperty("ipAddress", ip);
            json.addProperty("answerGroupId", answerGroupId);
            // Execute
            return imageJson(uploadingFiles, json.toString(), request);
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        return response;
    }

    @RequestMapping(value = {"/api/v2/protected/storage-file/audio",}, method = RequestMethod.POST, headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"})
    public @ResponseBody
    ResponseJsonRest audioHeader(@RequestPart(value = "uploadFile", required = false) MultipartFile[] uploadingFiles, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        try {
            //Details/Info
            String hrId = request.getHeader("x-storage-report-id");
            Long rId = !Validator.isEmptyString(hrId) ? Long.parseLong(hrId) : 0L;

            String haId = request.getHeader("x-storage-action-id");
            Long aId = !Validator.isEmptyString(haId) ? Long.parseLong(haId) : 0L;

            String htId = request.getHeader("x-storage-task-id");
            Long tId = !Validator.isEmptyString(htId) ? Long.parseLong(htId) : 0L;

            String hpId = request.getHeader("x-storage-profile-id");
            Long pId = !Validator.isEmptyString(hpId) ? Long.parseLong(hpId) : 0L;

            String hfId = request.getHeader("x-storage-feedback-id");
            Long fId = !Validator.isEmptyString(hfId) ? Long.parseLong(hfId) : 0L;

            String hqId = request.getHeader("x-storage-question-id");
            Long qId = !Validator.isEmptyString(hqId) ? Long.parseLong(hqId) : 0L;

            //Location
            String hAccuracy = request.getHeader("x-storage-accuracy");
            Float accuracy = !Validator.isEmptyString(hAccuracy) ? Float.parseFloat(hAccuracy) : 0F;

            String hLatitude = request.getHeader("x-storage-latitude");
            Float latitude = !Validator.isEmptyString(hLatitude) ? Float.parseFloat(hLatitude) : 0F;

            String hLongitude = request.getHeader("x-storage-longitude");
            Float longitude = !Validator.isEmptyString(hLongitude) ? Float.parseFloat(hLongitude) : 0F;

            String hAltitude = request.getHeader("x-storage-altitude");
            Float altitude = !Validator.isEmptyString(hAltitude) ? Float.parseFloat(hAltitude) : 0F;

            String hProvider = request.getHeader("x-storage-provider");
            String provider = !Validator.isEmptyString(hProvider) ? hProvider : "N/A";

            String hip = request.getHeader("x-storage-ip-address");
            String ip = !Validator.isEmptyString(hip) ? hip : "::0";

            String hAnswerGroupId = request.getHeader("x-storage-answer-group-id");
            String answerGroupId = !Validator.isEmptyString(hAnswerGroupId) ? hAnswerGroupId : "N/A";
            // Json
            JsonObject json = new JsonObject();
            json.addProperty("reportId", rId);
            json.addProperty("feedbackReportId", fId);
            json.addProperty("actionId", aId);
            json.addProperty("taskId", tId);
            json.addProperty("profileId", pId);
            json.addProperty("questionId", qId);

            json.addProperty("accuracy", accuracy);
            json.addProperty("latitude", latitude);
            json.addProperty("longitude", longitude);
            json.addProperty("altitude", altitude);
            json.addProperty("provider", provider);
            json.addProperty("ipAddress", ip);
            json.addProperty("answerGroupId", answerGroupId);
            // Execute
            return audioJson(uploadingFiles, json.toString(), request);
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        return response;
    }

    @RequestMapping(value = {"/api/v2/protected/storage-file/video",}, method = RequestMethod.POST, headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"})
    public @ResponseBody
    ResponseJsonRest videoHeader(@RequestPart(value = "uploadFile", required = false) MultipartFile[] uploadingFiles, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        try {
            //Details/Info
            String hrId = request.getHeader("x-storage-report-id");
            Long rId = !Validator.isEmptyString(hrId) ? Long.parseLong(hrId) : 0L;

            String haId = request.getHeader("x-storage-action-id");
            Long aId = !Validator.isEmptyString(haId) ? Long.parseLong(haId) : 0L;

            String htId = request.getHeader("x-storage-task-id");
            Long tId = !Validator.isEmptyString(htId) ? Long.parseLong(htId) : 0L;

            String hpId = request.getHeader("x-storage-profile-id");
            Long pId = !Validator.isEmptyString(hpId) ? Long.parseLong(hpId) : 0L;

            String hfId = request.getHeader("x-storage-feedback-id");
            Long fId = !Validator.isEmptyString(hfId) ? Long.parseLong(hfId) : 0L;

            String hqId = request.getHeader("x-storage-question-id");
            Long qId = !Validator.isEmptyString(hqId) ? Long.parseLong(hqId) : 0L;

            //Location
            String hAccuracy = request.getHeader("x-storage-accuracy");
            Float accuracy = !Validator.isEmptyString(hAccuracy) ? Float.parseFloat(hAccuracy) : 0F;

            String hLatitude = request.getHeader("x-storage-latitude");
            Float latitude = !Validator.isEmptyString(hLatitude) ? Float.parseFloat(hLatitude) : 0F;

            String hLongitude = request.getHeader("x-storage-longitude");
            Float longitude = !Validator.isEmptyString(hLongitude) ? Float.parseFloat(hLongitude) : 0F;

            String hAltitude = request.getHeader("x-storage-altitude");
            Float altitude = !Validator.isEmptyString(hAltitude) ? Float.parseFloat(hAltitude) : 0F;

            String hProvider = request.getHeader("x-storage-provider");
            String provider = !Validator.isEmptyString(hProvider) ? hProvider : "N/A";

            String hip = request.getHeader("x-storage-ip-address");
            String ip = !Validator.isEmptyString(hip) ? hip : "::0";

            String hAnswerGroupId = request.getHeader("x-storage-answer-group-id");
            String answerGroupId = !Validator.isEmptyString(hAnswerGroupId) ? hAnswerGroupId : "N/A";
            // Json
            JsonObject json = new JsonObject();
            json.addProperty("reportId", rId);
            json.addProperty("feedbackReportId", fId);
            json.addProperty("actionId", aId);
            json.addProperty("taskId", tId);
            json.addProperty("profileId", pId);
            json.addProperty("questionId", qId);

            json.addProperty("accuracy", accuracy);
            json.addProperty("latitude", latitude);
            json.addProperty("longitude", longitude);
            json.addProperty("altitude", altitude);
            json.addProperty("provider", provider);
            json.addProperty("ipAddress", ip);
            json.addProperty("answerGroupId", answerGroupId);
            // Execute
            return videoJson(uploadingFiles, json.toString(), request);
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        return response;
    }

    /**
     * Upload Image
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = {"/api/v2/protected/storage-file/image-json"}, method = RequestMethod.POST, headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"})
    public @ResponseBody
    ResponseJsonRest imageJson(@RequestPart(value = "uploadFile", required = false) MultipartFile[] uploadingFiles, @RequestPart(value = "json", required = true) String json, HttpServletRequest request)
            throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        try {
            // Request
            ReceiveJson r = new ReceiveJson(json);
            Long fId = r.has("feedbackReportId") ? r.getAsLong("feedbackReportId") : 0L;
            Long aId = r.has("actionId") ? r.getAsLong("actionId") : 0L;
            Long tId = r.has("taskId") ? r.getAsLong("taskId") : 0L;
            Long rId = r.has("reportId") ? r.getAsLong("reportId") : 0L;
            Long pId = r.has("profileId") ? r.getAsLong("profileId") : 0L;// Timestamp qualquer
            Long qId = r.has("questionId") ? r.getAsLong("questionId") : 0L;

            double accuracy = r.has("accuracy") ? r.get("accuracy").getAsFloat() : 0;
            double latitude = r.has("latitude") ? r.get("latitude").getAsFloat() : 0;
            double longitude = r.has("longitude") ? r.get("longitude").getAsFloat() : 0;
            double altitude = r.has("altitude") ? r.get("altitude").getAsFloat() : 0;
            String provider = r.has("provider") ? r.get("provider").getAsString() : "N/A";
            String ipAddress = r.has("ipAddress") ? r.get("ipAddress").getAsString() : "::0";
            String answerGroupId = r.has("answerGroupId") ? r.get("answerGroupId").getAsString() : "N/A";

            DateTime now = new DateTime();
            User user = getUser();

            // CATALINA.OUT JSON
            System.out.println(r.toString());
            System.out.println(json.toString());
            // Validate
            if (fId <= 0 && aId <= 0 && tId <= 0 && rId <= 0 && pId <= 0 && qId <= 0) {
                throw new Exception(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
            } else {
                List<StorageFileImage> result = new ArrayList<StorageFileImage>();
                int success = 0;
                int failure = 0;
                boolean isBase64 = aId > 0 && tId > 0 && (qId == 0 || qId == null);
                // Upload
                for (MultipartFile uploadedFile : uploadingFiles) {
                    // Vars
                    String originalFilename = uploadedFile.getOriginalFilename();
                    String ext = FilenameUtils.getExtension(originalFilename);
                    String name = Useful.secureRandomString();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String filename = String.format("%d_I%s_%s.%s", getUserId(), timestamp.getTime(), name, ext);
                    String assetUrl = String.format("%s/%s", Config.PRODUCTION_STORAGE_URL, filename);
                    // File
                    File file = new File(Config.PRODUCTION_STORAGE_TEMP + filename);
                    uploadedFile.transferTo(file);
                    // Save
                    if (isBase64) {
                        Task task = taskService.findById(tId);
                        Action action = actionService.findById(aId);
                        if (task != null || action != null) {
                            DataPhoto data = new DataPhoto();
                            // Dimen
                            int h = r.has("height") ? r.get("height").getAsInt() : 0;
                            int w = r.has("width") ? r.get("width").getAsInt() : 0;
                            if (h == 0 || w == 0) {
                                BufferedImage bimg = ImageIO.read(file);
                                w = bimg.getWidth();
                                h = bimg.getHeight();
                            }
                            // Vars
                            data.setActionId(aId);
                            data.setTaskId(tId);
                            data.setHeight(h);
                            data.setWidth(w);
                            data.setUser(user);
                            // Image
                            byte[] backToBytes = uploadedFile.getBytes();
                            BinaryDocument bd = new BinaryDocument();
                            bd.setContent(backToBytes);
                            bd.setContentType("image");
                            bd.setContentSubtype(ext);
                            bd.setCreated(now);
                            bd.setFilename(filename);
                            bd.setOwner(user);
                            data.setFile(bd);
                            // Date
                            DateTime s = new DateTime(r.has("timestamp") ? r.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                            data.setSampleTimestamp(s);
                            data.setDataReceivedTimestamp(now);
                            // Add
                            data = dataService.merge(data);
                            taskResultService.addData(tId, user.getId(), data);
                            success++;
                        } else {
                            failure++;
                        }
                    } else {
                        // Storage File Img
                        StorageFileImage img = new StorageFileImage();
                        img.setAssetUrl(assetUrl);
                        img.setFileName(filename);
                        img.setFileExtension(ext);
                        img.setOriginalFilename(originalFilename);
                        img.setAccuracy(accuracy);
                        img.setLatitude(latitude);
                        img.setLongitude(longitude);
                        img.setAltitude(altitude);
                        img.setProvider(provider);
                        img.setIpAddress(ipAddress);

                        try {
                            // Upload
                            AzureStorage.uploadBlod(file, null);
                            // Sucess
                            result.add(img);
                            success++;
                        } catch (Exception e) {
                            img.setAssetUrl(null);
                            img.setRemoved(true);
                            result.add(img);
                            failure++;
                        }
                    }
                }
                // Status
                response.setStatus(success > 0);
                response.setTotal(uploadingFiles.length);
                response.setMessage(success > 0 ? null : response.getMessage());
                // Result
                if (result.size() > 0) {
                    response.setItem(result);
                    response.setMessage(null);
                    List<StorageFile> files = new ArrayList<StorageFile>();
                    for (StorageFileImage s : result) {
                        StorageFile item = storageFileService.saveOrUpdate(s);
                        if (item != null) {
                            files.add(item);
                        }
                    }
                    // Pra onde?
                    if (fId > 0 && rId <= 0) {
                        FeedbackReport fr = feedbackReportService.findById(fId);
                        if (fr != null) {
                            List<StorageFile> fs = fr.getFiles();
                            fs.addAll(files);
                            fr.setFiles(fs);
                            response.setStatus(feedbackReportService.saveOrUpdate(fr) != null);
                        }
                    } else if (rId > 0) {
                        IssueReport ir = issueReportService.findById(rId);
                        if (ir != null) {
                            List<StorageFile> fs = ir.getFiles();
                            fs.addAll(files);
                            ir.setFiles(fs);
                            boolean status = issueReportService.saveOrUpdate(ir) != null;
                            response.setStatus(status);
                        }
                    } else if (pId > 0) {
                        StorageFile pImg = result.get(result.size() - 1);
                        String img = pImg.getAssetUrl();
                        // IDs
                        Long progenitorId = user.getProgenitorId();
                        if (progenitorId > 0) {// Foto da Conta Principal
                            User a = getAccount(progenitorId);
                            if (a != null) {
                                a.setPhoto(img);
                                getAuthenticatorService().doUpdateAccount(a);
                            }
                        }
                        // Foto na conta de visitante
                        user.setPhoto(img);
                        getAuthenticatorService().doUpdateAccount(user);
                    } else if (qId > 0 && tId > 0 && aId > 0) {
                        StorageFile pImg = result.get(result.size() - 1);
                        String img = pImg.getAssetUrl();

                        QuestionnaireResponseBuilder qrb = new QuestionnaireResponseBuilder();
                        qrb.setAll(tId, aId, qId, getUserId(), img, 0L, true, false);
                        qrb.setAccuracy(accuracy);
                        qrb.setLatitude(latitude);
                        qrb.setLongitude(longitude);
                        qrb.setAltitude(altitude);
                        qrb.setProvider(provider);
                        qrb.setPhoto(true);
                        qrb.setIpAddress(ipAddress);
                        qrb.setAnswerGroupId(answerGroupId);
                        QuestionnaireResponse qr = qrb.build(true);
                        questionnaireResponseService.save(qr);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        System.out.println(response);
        return response;
    }

    /**
     * Video Upload
     *
     * @param uploadingFiles
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = {"/api/v2/protected/storage-file/video-json",}, method = RequestMethod.POST, headers = {"content-type=multipart/mixed", "content-type=multipart/form-data", "content-type=video/mp4"})
    public @ResponseBody
    ResponseJsonRest videoJson(@RequestPart(value = "uploadFile", required = false) MultipartFile[] uploadingFiles, @RequestPart(value = "json", required = true) String json, HttpServletRequest request)
            throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        try {
            // Request
            ReceiveJson r = new ReceiveJson(json);
            Long fId = r.has("feedbackReportId") ? r.getAsLong("feedbackReportId") : 0L;
            Long rId = r.has("reportId") ? r.getAsLong("reportId") : 0L;
            String metadata = r.has("metadata") ? r.getAsString("metadata") : null;
            String encodingType = r.has("encodingType") ? r.getAsString("encodingType") : null;
            Long duration = r.has("duration") ? r.getAsLong("duration") : 0L;

            double accuracy = r.has("accuracy") ? r.get("accuracy").getAsFloat() : 0;
            double latitude = r.has("latitude") ? r.get("latitude").getAsFloat() : 0;
            double longitude = r.has("longitude") ? r.get("longitude").getAsFloat() : 0;
            double altitude = r.has("altitude") ? r.get("altitude").getAsFloat() : 0;
            String provider = r.has("provider") ? r.get("provider").getAsString() : "N/A";
            String ipAddress = r.has("ipAddress") ? r.get("ipAddress").getAsString() : "::0";

            DateTime now = new DateTime();
            User user = getUser();
            // Validate
            if (fId <= 0 && rId <= 0) {
                throw new Exception(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
            } else {
                // BEGIN FILE
                List<StorageFileVideo> result = new ArrayList<StorageFileVideo>();
                int success = 0;
                int failure = 0;
                // Upload
                for (MultipartFile uploadedFile : uploadingFiles) {
                    // Vars
                    System.out.println(uploadedFile.getContentType());
                    Boolean isItOkayMobile = false;
                    Boolean isItOkayWeb = false;
                    String originalFilename = uploadedFile.getOriginalFilename();
                    String ext = FilenameUtils.getExtension(originalFilename);
                    String name = Useful.secureRandomString();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String filename = String.format("%d_I%s_%s.%s", getUserId(), timestamp.getTime(), name, ext);
                    String assetUrl = String.format("%s/%s", Config.PRODUCTION_STORAGE_URL, filename);
                    String assetUrlWeb = assetUrl;
                    String assetUrlThird = assetUrl;
                    String assetUrlFourth = assetUrl;
                    String src = Config.PRODUCTION_STORAGE_TEMP + filename;
                    // Encode
                    String newName = filename.replace(String.format(".%s", ext), "_");
                    String dst = Config.PRODUCTION_STORAGE_TEMP + newName + "_encoded.mp4";
                    String encodedUrl = String.format("%s/%s", Config.PRODUCTION_STORAGE_URL, newName + "_encoded.mp4");

                    String dstWeb = Config.PRODUCTION_STORAGE_TEMP + newName + "_web_encoded.mp4";
                    String encodedUrlWeb = String.format("%s/%s", Config.PRODUCTION_STORAGE_URL, newName + "_web_encoded.mp4");
                    // File
                    File file = new File(src);
                    // Encode
                    // Mobile
                    if (FFmpeg.toMp4(src, dst)) {
                        file = new File(dst);
                        assetUrl = encodedUrl;
                        System.out.println(assetUrl);
                        try {
                            // Copy
                            uploadedFile.transferTo(file);
                            // Upload
                            // AzureStorage.uploadBlod(file, "video/mp4");
                            String encodeUrl = AzureMedia.uploadMediaToStream(newName, "_encoded.mp4", "video/mp4", file);
                            if (!Validator.isEmptyString(encodeUrl)) {
                                assetUrl = encodeUrl;
                            }
                            System.out.println(encodeUrl);
                            // Is it okay?
                            isItOkayMobile = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    // Desktop
                    if (FFmpeg.toH264(src, dstWeb)) {
                        file = new File(dstWeb);
                        assetUrlWeb = encodedUrlWeb;
                        System.out.println(assetUrlWeb);
                        try {
                            // Copy
                            uploadedFile.transferTo(file);
                            // Upload
                            AzureStorage.uploadBlod(file, "video/mp4");
                            // Is it okay?
                            isItOkayWeb = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    // Storage File
                    StorageFileVideo video = new StorageFileVideo();
                    video.setAssetUrl(assetUrl);
                    video.setAssetUrlWeb(assetUrlWeb);
                    video.setAssetUrlThird(assetUrlThird);
                    video.setAssetUrlFourth(assetUrlFourth);
                    video.setFileName(filename);
                    video.setFileExtension(ext);
                    video.setOriginalFilename(originalFilename);
                    video.setEncodingType(encodingType);
                    video.setDuration(duration);
                    video.setMetadata(metadata);
                    video.setVideoClip(true);

                    video.setAccuracy(accuracy);
                    video.setLatitude(latitude);
                    video.setLongitude(longitude);
                    video.setAltitude(altitude);
                    video.setProvider(provider);
                    video.setIpAddress(ipAddress);
                    try {
                        if (isItOkayMobile || isItOkayWeb) {
                            // Sucess
                            result.add(video);
                            success++;
                        } else {
                            failure++;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        video.setAssetUrl(null);
                        video.setRemoved(true);
                        result.add(video);
                        failure++;
                    }
                }
                // Status
                response.setStatus(success > 0);
                response.setTotal(uploadingFiles.length);
                response.setMessage(success > 0 ? null : response.getMessage());
                // Result
                if (result.size() > 0) {
                    response.setItem(result);
                    response.setMessage(null);
                    List<StorageFile> files = new ArrayList<StorageFile>();
                    for (StorageFileVideo s : result) {
                        StorageFile item = storageFileService.saveOrUpdate(s);
                        if (item != null) {
                            files.add(item);
                        }
                    }
                    // Pra onde?
                    if (fId > 0) {
                        FeedbackReport fr = feedbackReportService.findById(fId);
                        if (fr != null) {
                            List<StorageFile> fs = fr.getFiles();
                            fs.addAll(files);
                            fr.setFiles(fs);
                            response.setStatus(feedbackReportService.saveOrUpdate(fr) != null);
                        }
                    } else if (rId > 0) {
                        IssueReport ir = issueReportService.findById(rId);
                        if (ir != null) {
                            List<StorageFile> fs = ir.getFiles();
                            fs.addAll(files);
                            ir.setFiles(fs);
                            boolean status = issueReportService.saveOrUpdate(ir) != null;
                            response.setStatus(status);
                        }
                    }
                }
                // END FILE
            }
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        return response;
    }

    /**
     * Audio Upload
     *
     * @param uploadingFiles
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = {"/api/v2/protected/storage-file/audio-json",}, method = RequestMethod.POST, headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"})
    public @ResponseBody
    ResponseJsonRest audioJson(@RequestPart(value = "uploadFile", required = false) MultipartFile[] uploadingFiles, @RequestPart(value = "json", required = true) String json, HttpServletRequest request)
            throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        try {
            // Request
            ReceiveJson r = new ReceiveJson(json);
            Long fId = r.has("feedbackReportId") ? r.getAsLong("feedbackReportId") : 0L;
            Long rId = r.has("reportId") ? r.getAsLong("reportId") : 0L;

            String metadata = r.has("metadata") ? r.getAsString("metadata") : null;
            String encodingType = r.has("encodingType") ? r.getAsString("encodingType") : null;
            Long duration = r.has("duration") ? r.getAsLong("duration") : 0L;

            double accuracy = r.has("accuracy") ? r.get("accuracy").getAsFloat() : 0;
            double latitude = r.has("latitude") ? r.get("latitude").getAsFloat() : 0;
            double longitude = r.has("longitude") ? r.get("longitude").getAsFloat() : 0;
            double altitude = r.has("altitude") ? r.get("altitude").getAsFloat() : 0;
            String provider = r.has("provider") ? r.get("provider").getAsString() : "N/A";
            String ipAddress = r.has("ipAddress") ? r.get("ipAddress").getAsString() : "::0";

            DateTime now = new DateTime();
            User user = getUser();
            // Validate
            if (fId <= 0 && rId <= 0) {
                throw new Exception(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
            } else {
                // BEGIN FILE
                List<StorageFileAudio> result = new ArrayList<StorageFileAudio>();
                int success = 0;
                int failure = 0;
                // Upload
                for (MultipartFile uploadedFile : uploadingFiles) {
                    // Vars
                    Boolean isItOkayMobile = false;
                    Boolean isItOkayWeb = false;
                    String originalFilename = uploadedFile.getOriginalFilename();
                    String ext = FilenameUtils.getExtension(originalFilename);
                    String name = Useful.secureRandomString();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String filename = String.format("%d_I%s_%s.%s", getUserId(), timestamp.getTime(), name, ext);
                    String assetUrl = String.format("%s/%s", Config.PRODUCTION_STORAGE_URL, filename);
                    String assetUrlWeb = assetUrl;
                    String assetUrlThird = assetUrl;
                    String assetUrlFourth = assetUrl;
                    String src = Config.PRODUCTION_STORAGE_TEMP + filename;
                    // Encode
                    String newName = filename.replace(String.format(".%s", ext), "_");
                    String dst = Config.PRODUCTION_STORAGE_TEMP + newName + "_encoded.mp3";
                    String encodedUrl = String.format("%s/%s", Config.PRODUCTION_STORAGE_URL, newName + "_encoded.mp3");

                    String dstWeb = Config.PRODUCTION_STORAGE_TEMP + newName + "_web_encoded.aac";
                    String encodedUrlWeb = String.format("%s/%s", Config.PRODUCTION_STORAGE_URL, newName + "_web_encoded.aac");
                    // File
                    File file = new File(src);
                    // Encode
                    // Mobile
                    if (FFmpeg.toMp3(src, dst)) {
                        file = new File(dst);
                        assetUrl = encodedUrl;
                        try {
                            // Copy
                            uploadedFile.transferTo(file);
                            // Upload
                            // AzureStorage.uploadBlod(file, "audio/mp3");
                            String encodeUrl = AzureMedia.uploadMediaToStream(newName, "_encoded.mp3", "audio/mp3", file);
                            if (!Validator.isEmptyString(encodeUrl)) {
                                assetUrl = encodeUrl;
                            }
                            System.out.println(encodeUrl);
                            // Is it okay?
                            isItOkayMobile = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    //Apple
                    String dstFlac = Config.PRODUCTION_STORAGE_TEMP + newName + "_encoded.flac";
                    if (FFmpeg.toFlac(src, dstFlac)) {
                        file = new File(dstFlac);
                        try {
                            // Copy
                            uploadedFile.transferTo(file);
                            // Upload
                            String urlFlac = AzureMedia.uploadMediaToStream(newName, "_encoded.flac", "audio/flac", file);
                            if (!Validator.isEmptyString(urlFlac)) {
                                assetUrlFourth = urlFlac;
                            }
                            System.out.println(assetUrlFourth);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    //Wav
                    String dstWav = Config.PRODUCTION_STORAGE_TEMP + newName + "_encoded.wav";
                    if (FFmpeg.toFlac(src, dstWav)) {
                        file = new File(dstWav);
                        try {
                            // Copy
                            uploadedFile.transferTo(file);
                            // Upload
                            String urlWav = AzureMedia.uploadMediaToStream(newName, "_encoded.wav", "audio/wav", file);
                            if (!Validator.isEmptyString(urlWav)) {
                                assetUrlThird = urlWav;
                            }
                            System.out.println(assetUrlThird);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    // Desktop
                    if (FFmpeg.toAac(src, dstWeb)) {
                        file = new File(dstWeb);
                        assetUrlWeb = encodedUrlWeb;
                        try {
                            // Copy
                            uploadedFile.transferTo(file);
                            // Upload
                            AzureStorage.uploadBlod(file, "audio/vnd.dlna.adts");
                            // Is it okay?
                            isItOkayWeb = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    // Storage File
                    StorageFileAudio audio = new StorageFileAudio();
                    audio.setAssetUrl(assetUrl);
                    audio.setAssetUrlWeb(assetUrlWeb);
                    audio.setAssetUrlThird(assetUrlThird);
                    audio.setAssetUrlFourth(assetUrlFourth);
                    audio.setFileName(filename);
                    audio.setFileExtension(ext);
                    audio.setOriginalFilename(originalFilename);
                    audio.setEncodingType(encodingType);
                    audio.setDuration(duration);
                    audio.setMetadata(metadata);

                    audio.setAccuracy(accuracy);
                    audio.setLatitude(latitude);
                    audio.setLongitude(longitude);
                    audio.setAltitude(altitude);
                    audio.setProvider(provider);
                    audio.setIpAddress(ipAddress);
                    // Audio Info
                    try {
                        if (isItOkayMobile || isItOkayWeb) {
                            // Sucess
                            result.add(audio);
                            success++;
                        } else {
                            failure++;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        audio.setAssetUrl(null);
                        audio.setRemoved(true);
                        result.add(audio);
                        failure++;
                    }
                }
                // Status
                response.setStatus(success > 0);
                response.setTotal(uploadingFiles.length);
                response.setMessage(success > 0 ? null : response.getMessage());
                // Result
                if (result.size() > 0) {
                    response.setItem(result);
                    response.setMessage(null);
                    List<StorageFile> files = new ArrayList<StorageFile>();
                    for (StorageFileAudio s : result) {
                        StorageFile item = storageFileService.saveOrUpdate(s);
                        if (item != null) {
                            files.add(item);
                        }
                    }
                    // Pra onde?
                    if (fId > 0 && rId <= 0) {
                        FeedbackReport fr = feedbackReportService.findById(fId);
                        if (fr != null) {
                            List<StorageFile> fs = fr.getFiles();
                            fs.addAll(files);
                            fr.setFiles(fs);
                            response.setStatus(feedbackReportService.saveOrUpdate(fr) != null);
                        }
                    } else if (rId > 0) {
                        IssueReport ir = issueReportService.findById(rId);
                        if (ir != null) {
                            List<StorageFile> fs = ir.getFiles();
                            fs.addAll(files);
                            ir.setFiles(fs);
                            response.setStatus(issueReportService.saveOrUpdate(ir) != null);
                        }
                    }
                }
                // END FILE
            }
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        return response;
    }
}