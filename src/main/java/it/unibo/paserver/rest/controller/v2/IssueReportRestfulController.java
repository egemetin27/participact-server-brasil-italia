package it.unibo.paserver.rest.controller.v2;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import br.gov.cgu.eouv.service.ManifestacaoService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.flat.IssueReportStatsFlat;
import it.unibo.paserver.domain.support.IssueAbuseBuilder;
import it.unibo.paserver.domain.support.IssueReportBuilder;
import it.unibo.paserver.service.*;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Relatos/Alertas
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@RestController
public class IssueReportRestfulController extends ApplicationRestfulController {
    @Autowired
    ManifestacaoService manifestacaoService;
    @Autowired
    private IssueCategoryService issueCategoryService;
    @Autowired
    private IssueSubCategoryService issueSubCategoryService;
    @Autowired
    private IssueReportService issueReportService;
    @Autowired
    private IssueAbuseService issueAbuseService;
    @Autowired
    private AbuseTypeService abuseTypeService;

    /**
     * Criando um Alerta/Relato
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/issue-report/submit"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest submit(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // value
        long subcategoryId = (r.has("subcategoryId") ? r.get("subcategoryId").getAsLong() : 0L);
        String comment = (r.has("comment") ? r.get("comment").getAsString() : null);
        String provider = (r.has("provider") ? r.get("provider").getAsString() : "N/A");
        String formattedCity = (r.has("city") ? r.get("city").getAsString() : null);
        String formattedAddress = (r.has("address") ? r.get("address").getAsString() : null);
        String optionalUserName = (r.has("userName") ? r.get("userName").getAsString() : null);
        String optionalUserEmail = (r.has("userEmail") ? r.get("userEmail").getAsString() : null);
        String gpsInfo = (r.has("gpsInfo") ? r.get("gpsInfo").getAsString() : null);
        int fileCounter = (r.has("filesCount") ? r.get("filesCount").getAsInt() : 0);

        float accuracy = (r.has("accuracy") ? r.get("accuracy").getAsFloat() : 0);
        float latitude = (r.has("latitude") ? r.get("latitude").getAsFloat() : 0);
        float longitude = (r.has("longitude") ? r.get("longitude").getAsFloat() : 0);
        float altitude = (r.has("altitude") ? r.get("altitude").getAsFloat() : 0);

        float horizontalAccuracy = (r.has("horizontalAccuracy") ? r.get("horizontalAccuracy").getAsFloat() : 0);
        float verticalAccuracy = (r.has("verticalAccuracy") ? r.get("verticalAccuracy").getAsFloat() : 0);
        float course = (r.has("course") ? r.get("course").getAsFloat() : 0);
        float speed = (r.has("speed") ? r.get("speed").getAsFloat() : 0);
        float floor = (r.has("floor") ? r.get("floor").getAsFloat() : 0);
        DateTime now = new DateTime();
        // Validate
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            if (subcategoryId <= 0) {
                throw new Exception(messageSource.getMessage("subcategory.type.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else {
                IssueSubCategory is = issueSubCategoryService.findById(subcategoryId);
                if (is != null) {
                    // Build
                    IssueReportBuilder irb = new IssueReportBuilder();
                    irb.setComment(comment);
                    irb.setSubcategory(is);
                    irb.setUser(getUser());

                    irb.setProvider(provider);
                    irb.setAccuracy(accuracy);
                    irb.setLatitude(latitude);
                    irb.setLongitude(longitude);
                    irb.setAltitude(altitude);
                    irb.setHorizontalAccuracy(horizontalAccuracy);
                    irb.setVerticalAccuracy(verticalAccuracy);
                    irb.setCourse(course);
                    irb.setSpeed(speed);
                    irb.setFloor(floor);
                    irb.setFileCounter(fileCounter);
                    if (!Validator.isEmptyString(formattedCity)) { // Contem a cidade?
                        irb.setFormattedCity(Useful.unaccent(formattedCity));
                    }
                    if (!Validator.isEmptyString(formattedAddress)) { // Contem a Endereco?
                        irb.setFormattedAddress(formattedAddress);
                    }
                    irb.setGpsInfo(gpsInfo);
                    irb.setOptionalUserEmail(optionalUserEmail);
                    irb.setOptionalUserName(optionalUserName);
                    // Date
                    DateTime s = new DateTime(r.has("timestamp") ? r.get("timestamp").getAsLong() * 1000L : System.currentTimeMillis());
                    irb.setSampleTimestamp(s);
                    irb.setDataReceivedTimestamp(now);
                    // Save
                    IssueReport ir = irb.build(true);
                    IssueReport result = issueReportService.saveOrUpdate(ir);
                    if (result != null) {
                        response.setStatus(true);
                        response.setOutcome(result.getId());
                        response.setMessage(null);
                        response.setRemoved(result.getUser().isRemoved());
                        // Async
                        manifestacaoService.registerThirdPartyManifestation(result);
                    }
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Consultando dados de um relato
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/issue-report/find"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest find(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Params
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Dates
        Long reportId = r.getAsLong("report_id");
        try {
            // Check
            if (reportId > 0) {
                // Search
                IssueReport result = issueReportService.findById(reportId);
                if (result != null) {
                    Hibernate.initialize(result.getFiles());
                }
                // Resultado
                response.setItem(result);
                response.setStatus(true);
                response.setMessage(null);
            } else {
                throw new Exception("Invalid Report Id");
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Consultando dados resumido
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/issue-report/stats"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest Statistics(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        System.out.println(json.toString());
        // Params
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Overview
        boolean checkTotal = r.has("check_total") ? r.getAsBoolean("check_total") : false;
        // Dates
        String dateStart = r.getAsString("start_date");
        String dateEnd = r.getAsString("end_date");

        DateTime queryStart = new DateTime().minusMonths(6);
        DateTime queryEnd = new DateTime().minusMinutes(10);

        if (Validator.isValidDateFormat(dateStart, "yyyy-MM-dd")) {
            queryStart = Useful.converteStringToDate(dateStart + " 00:00:00");
        } else if (Validator.isValidDateFormat(dateStart, "yyyy-MM-dd HH:mm:ss")) {
            queryStart = Useful.converteStringToDate(dateStart);
        }

        if (Validator.isValidDateFormat(dateEnd, "yyyy-MM-dd")) {
            queryEnd = Useful.converteStringToDate(dateEnd + " 00:00:00");
        } else if (Validator.isValidDateFormat(dateEnd, "yyyy-MM-dd HH:mm:ss")) {
            queryEnd = Useful.converteStringToDate(dateEnd);
        }

        if (queryEnd.isEqual(queryStart)) {
            queryStart = queryEnd.minusDays(7);
        } else if (queryEnd.isBefore(queryStart)) {
            queryStart = queryEnd.minusDays(7);
        }
        // Categoria/SubCategoria
        long subCategoryId = r.getAsLong("subcategory_id");
        long categoryId = r.getAsLong("category_id");
        if (subCategoryId > 0) {
            params.put("subCategoryId", subCategoryId);
        } else if (categoryId > 0) {
            params.put("categoryId", categoryId);
        }
        params.put("isSecondary", false);
        try {

            // Geo
            String latitude = (r.has("latitude") ? r.get("latitude").getAsString() : null);
            String longitude = (r.has("longitude") ? r.get("longitude").getAsString() : null);
            if (Validator.isValidLatitude(latitude) && Validator.isValidLongitude(longitude)) {
                System.out.println(latitude);
                System.out.println(longitude);

                double lat = Double.parseDouble(latitude);
                double lon = Double.parseDouble(longitude);
                double R = Config.GEO_LOCATION_R; // earth radius in km
                // double radius = Config.GEO_LOCATION_RADIUS; // km
                double radius = (r.has("radius") ? r.getAsDouble("radius") : Config.GEO_LOCATION_RADIUS);
                double minLon = lon - Math.toDegrees(radius / R / Math.cos(Math.toRadians(lat)));
                double maxLon = lon + Math.toDegrees(radius / R / Math.cos(Math.toRadians(lat)));
                double maxLat = lat + Math.toDegrees(radius / R);
                double minLat = lat - Math.toDegrees(radius / R);

                params.put("maxLat", maxLat);
                params.put("minLat", minLat);
                params.put("minLon", minLon);
                params.put("maxLon", maxLon);
                // Search
                List<Object[]> result = new ArrayList<Object[]>();
                if (checkTotal) {
                    result = issueReportService.searchByStatsGrouped(params, queryStart, queryEnd, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
                } else {
                    result = issueReportService.searchByStats(params, queryStart, queryEnd, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
                }
                // Resultado
                response.setStatus(true);
                response.setMessage(null);
                if (result.size() > 0) {
                    List<IssueReportStatsFlat> s = new ArrayList<IssueReportStatsFlat>();
                    if (checkTotal) {
                        for (Object[] i : result) {
                            IssueReportStatsFlat flat = new IssueReportStatsFlat();
                            flat.setReportId(Long.parseLong((String) i[4].toString()));
                            flat.setCategoryId(Long.parseLong((String) i[0].toString()));
                            flat.setDateTime(null);
                            flat.setLatitude(0);
                            flat.setLongitude(0);
                            flat.setCategoryName((String) i[2].toString());
                            flat.setCategoryColor((String) i[3].toString());
                            flat.setQueryAt((String) i[1].toString());

                            s.add(flat);
                        }
                    } else {
                        for (Object[] i : result) {
                            String latFlat = (String) i[3].toString();
                            String lngFlat = (String) i[4].toString();
                            if (Validator.isValidLatitude(latFlat) && Validator.isValidLongitude(lngFlat)) {
                                IssueReportStatsFlat flat = new IssueReportStatsFlat();
                                flat.setReportId(Long.parseLong((String) i[0].toString()));
                                flat.setCategoryId(Long.parseLong((String) i[1].toString()));
                                flat.setDateTime((String) i[2].toString());
                                flat.setLatitude(Double.parseDouble(latFlat));
                                flat.setLongitude(Double.parseDouble(lngFlat));
                                flat.setCategoryName((String) i[5].toString());
                                flat.setCategoryColor((String) i[6].toString());
                                flat.setQueryAt((String) i[7].toString());

                                s.add(flat);
                            }
                        }
                    }
                    response.setItem(s);
                    response.setCount(s.size());
                    response.setTotal(s.size());
                }
            } else {
                throw new Exception("Invalid Lat/Lng");
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Consultando relatorios
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/issue-report"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest search(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Params
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        // Request
        ReceiveJson r = new ReceiveJson(json);
        int count = r.getAsInt("count");
        count = count == 0 ? 10 : count;
        count = count == -1 ? Config.SELECT_MAX_COUNT : count;// Unlimit
        int offset = r.getAsInt("offset");
        long subCategoryId = r.getAsLong("subcategory_id");
        long categoryId = r.getAsLong("category_id");
        boolean mine = r.getAsBoolean("mine");
        if (subCategoryId > 0) {
            params.put("subCategoryId", subCategoryId);
        } else if (categoryId > 0) {
            params.put("categoryId", categoryId);
        }
        // Version
        String version = r.getAsString("version");
        DateTime updateDate = new DateTime().minusYears(1);
        if (Validator.isValidDateFormat(version, "yyyy-MM-dd HH:mm:ss")) {
            updateDate = Useful.converteStringToDate(version);
        }
        response.setVersion(new DateTime().minusHours(3));
        // Geo
        int zoom = (r.has("zoom") ? r.get("zoom").getAsInt() : 0);
        // int rad = Useful.getScaleMapsZoom(zoom);
        String latitude = (r.has("latitude") ? r.get("latitude").getAsString() : null);
        String longitude = (r.has("longitude") ? r.get("longitude").getAsString() : null);
        if (Validator.isValidLatitude(latitude) && Validator.isValidLongitude(longitude)) {
            System.out.println(latitude);
            System.out.println(longitude);

            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);
            double R = Config.GEO_LOCATION_R; // earth radius in km
            double radius = Config.GEO_LOCATION_RADIUS; // km
            double minLon = lon - Math.toDegrees(radius / R / Math.cos(Math.toRadians(lat)));
            double maxLon = lon + Math.toDegrees(radius / R / Math.cos(Math.toRadians(lat)));
            double maxLat = lat + Math.toDegrees(radius / R);
            double minLat = lat - Math.toDegrees(radius / R);

            params.put("maxLat", maxLat);
            params.put("minLat", minLat);
            params.put("minLon", minLon);
            params.put("maxLon", maxLon);
        }
        // Auxiliares / Datas
        if (mine) {
            params.put("userId", getUserId());
        } else {
            String startDate = r.getAsString("start_date");
            String endDate = r.getAsString("end_date");
            if (Validator.isValidDateFormat(startDate, "yyyy-MM-dd") && Validator.isValidDateFormat(endDate, "yyyy-MM-dd")) {
                params.put("startDate", Useful.converteStringToDate(String.format("%s 00:00:00", startDate)));
                params.put("endDate", Useful.converteStringToDate(String.format("%s 23:59:59", endDate)));
            } else {
                if (zoom == 0) {
                    params.put("startDate", new DateTime().minusDays(8));
                    params.put("endDate", new DateTime());
                }
                params.put("updateDate", updateDate);
            }
        }
        try {
            //Somente principais
            params.put("isSecondary", false);
            long issueId = r.getAsLong("issue_id");
            if (issueId > 0) {
                params.put("issueId", issueId);
            }
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            List<IssueReport> result = issueReportService.filter(params, PaginationUtil.pagerequest(offset, count));
//            long issueId = r.getAsLong("issue_id");
//            if (issueId > 0) {
//                IssueReport issue = issueReportService.findById(issueId);
//                if (issue != null) {
//                    result.add(issue);
//                }
//            }
            //Foreach
            if (result.size() > 0) {
                for (IssueReport i : result) {
                    //if (i.isResolved()) {
                    List<ManifestacaoResposta> respostaList = manifestacaoService.getListaManifestacaoResposta(i.getId());
                    if (respostaList.size() > 0) {
                        i.setPublicMessage(respostaList.get(respostaList.size() - 1).getTexto());
                        List<HashMap<String, Object>> hashMaps = new ArrayList<>();
                        for (ManifestacaoResposta mr : respostaList) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", mr.getId());
                            map.put("tipo", mr.getTipo());
                            map.put("texto", mr.getTexto());
                            map.put("dataresposta", mr.getDataResposta().toString());
                            hashMaps.add(map);
                        }
                        i.setOmbudsmanHistory(hashMaps);
                    }
                    //}
                    i.setPublicEmail(Config.defaultUserEmail);
                    //List
                    // Params / MultiMap
                    ListMultimap<String, Object> args = ArrayListMultimap.create();
                    args.put("isSecondary", true);
                    args.put("isFail", false);
                    args.put("parentId", i.getId());
                    //Hash
                    List<HashMap<String, Object>> ombudsmanList = new ArrayList<>();
                    List<Object[]> ombList = issueReportService.search(args, null);
                    if (ombList.size() > 0) {
                        for(Object[] omb : ombList){
                            HashMap<String, Object> ombMap =  new HashMap<>();
                            ombMap.put("pub_protocol", omb[26]);
                            ombMap.put("ombudsman_name", omb[35]);
                            ombudsmanList.add(ombMap);
                        }
                    }
                    i.setOmbudsmanList(ombudsmanList);
                }
            }
            // Resultado
            response.setStatus(true);
            response.setMessage(null);
            if (result.size() > 0) {
                response.setItem(result);
                response.setCount(result.size());
            } else {
                response.setItem(new ArrayList());
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Tipos de Categorias
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/issue-report/category"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest types(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        int count = r.getAsInt("count");
        count = count == 0 ? 10 : count;
        int offset = r.getAsInt("offset");
        // Version
        String version = r.getAsString("version");
        DateTime updateDate = new DateTime().minusYears(3);
        if (Validator.isValidDateFormat(version, "yyyy-MM-dd HH:mm:ss")) {
            updateDate = Useful.converteStringToDate(version);
        }
        response.setVersion(new DateTime().minusHours(3));
        // Auxiliares
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        params.put("updateDate", updateDate);
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            List<IssueCategory> result = issueCategoryService.filter(params, PaginationUtil.pagerequest(offset, count));
            // Resultado
            response.setStatus(true);
            response.setMessage(null);
            if (result.size() > 0) {
                for (IssueCategory ic_item : result) {
                    List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
                    List<IssueSubCategory> subcategories = ic_item.getSubcategories();
                    if (subcategories != null && subcategories.size() > 0) {
                        for (IssueSubCategory is_item : subcategories) {
                            HashMap<String, Object> value = new HashMap<String, Object>();
                            if (!is_item.isRemoved()) {
                                //Map
                                value.put("id", is_item.getId());
                                value.put("name", is_item.getName());
                                value.put("urlAsset", is_item.getUrlAsset());
                                value.put("urlAssetLight", is_item.getUrlAssetLight());
                                value.put("sequence", is_item.getSequence());
                                value.put("level", is_item.getLevel());
                                value.put("categoryId", ic_item.getId());
                                value.put("parentId", is_item.getParentId());
                                map.add(value);
                            }
                        }
                    }
                    ic_item.setMap(map);
                }
                response.setItem(result);
                response.setCount(result.size());
            } else {
                response.setItem(new ArrayList<>());
                response.setCount(0);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Enviando uma denuncia
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/issue-report/abuse/submit"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest abuse(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        String comment = r.getAsString("comment");
        Long reportId = r.getAsLong("reportId");
        Long typeId = r.getAsLong("typeId");
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            if (reportId <= 0) {
                throw new Exception(messageSource.getMessage("abuse.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else {
                IssueReport ir = issueReportService.findById(reportId);
                IssueAbuseType at = abuseTypeService.findById(typeId);
                if (ir != null && at != null) {
                    IssueAbuseBuilder iab = new IssueAbuseBuilder();
                    iab.setComment(comment);
                    iab.setIssue(ir);
                    iab.setType(at);
                    iab.setUser(getUser());

                    IssueAbuse ia = iab.build(true);
                    IssueAbuse result = issueAbuseService.saveOrUpdate(ia);
                    if (result != null) {
                        issueReportService.incrementNegativeScore(reportId);
                        response.setStatus(true);
                        response.setOutcome(result.getId());
                        response.setMessage(null);
                    }
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Tipos de denuncias
     *
     * @param json
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/api/v2/protected/issue-report/abuse"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest abuses(@RequestBody String json, HttpServletRequest request) throws Exception {
        // Default Response
        ResponseJsonRest response = new ResponseJsonRest();
        // Details
        isUserDetails(request);
        // Response
        response.setStatus(false);
        response.setCount(0);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setOutcome(getUserId());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        int count = r.getAsInt("count");
        count = count == 0 ? 10 : count;
        int offset = r.getAsInt("offset");
        // Version
        String version = r.getAsString("version");
        DateTime updateDate = new DateTime().minusYears(3);
        if (Validator.isValidDateFormat(version, "yyyy-MM-dd HH:mm:ss")) {
            updateDate = Useful.converteStringToDate(version);
        }
        response.setVersion(new DateTime().minusHours(3));
        // Auxiliares
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        params.put("updateDate", updateDate);
        try {
            // CATALINA.OUT JSON
            System.out.println(json.toString());

            List<IssueAbuseType> result = abuseTypeService.filter(params, PaginationUtil.pagerequest(offset, count));
            // Resultado
            response.setStatus(true);
            response.setMessage(null);
            if (result.size() > 0) {
                response.setItem(result);
                response.setCount(result.size());
            } else {
                response.setItem(new ArrayList<>());
                response.setCount(0);
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }
}
