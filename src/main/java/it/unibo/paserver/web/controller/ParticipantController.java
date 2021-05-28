package it.unibo.paserver.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.EnumUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.FilterByUser;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.domain.ReceiveAdvancedSearch;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJson;
import it.unibo.paserver.domain.ResultType;
import it.unibo.paserver.domain.SchoolCourse;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.service.DevicesService;
import it.unibo.paserver.service.FileExportService;
import it.unibo.paserver.service.InstitutionsService;
import it.unibo.paserver.service.ParticipantService;
import it.unibo.paserver.service.SchoolCourseService;
import it.unibo.paserver.web.security.v1.AccountAdminDetails;

/**
 * Controller dos participantes no dashboard
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@Controller
public class ParticipantController extends ApplicationController {

    @Autowired
    private ParticipantService participantService;
    @Autowired
    private DevicesService devicesService;
    @Autowired
    private InstitutionsService institutionsService;
    @Autowired
    private SchoolCourseService schoolCourseService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private FileExportService fileExportService;
    ResponseJson response = new ResponseJson();

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/participant/index", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/participant/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("institutions", institutionsService.findAll());
        modelAndView.addObject("courses", schoolCourseService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/protected/participant/details/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
    public ModelAndView details(@PathVariable("id") long id, ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/participant/details");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("id", id);
        // Return
        return modelAndView;
    }

    /**
     * Formulario
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/participant/form", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
    public ModelAndView form(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/participant/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("devices", devicesService.findAll());
        modelAndView.addObject("institutions", institutionsService.findAll());
        modelAndView.addObject("courses", schoolCourseService.findAll());
        return modelAndView;
    }

    /**
     * Edicao de usuario
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/participant/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
    public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
        // Model view
        modelAndView.setViewName("/protected/participant/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("devices", devicesService.findAll());
        modelAndView.addObject("institutions", institutionsService.findAll());
        modelAndView.addObject("courses", schoolCourseService.findAll());
        modelAndView.addObject("form", id);
        return modelAndView;
    }

    /**
     * Edicao de usuario
     *
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/participant/edit/{id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id, HttpServletRequest request) throws JsonProcessingException {
        isRoot(request);
        // Search
        User u = participantService.findParticipantById(id);
        if (u != null) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id", u.getId());
            item.put("name", u.getName());
            item.put("surname", u.getSurname());
            item.put("officialEmail", u.getOfficialEmail());
            item.put("hasAllowOmbudsman", u.getHasAllowOmbudsman());
            item.put("fileSource", u.getFileSource());
            item.put("address", u.getCurrentAddress());
            item.put("addressDistrict", u.getCurrentDistrict());
            item.put("addressNumber", u.getCurrentNumber());
            item.put("addressCity", u.getCurrentCity());
            item.put("addressState", u.getCurrentProvince());
            item.put("addressCountry", u.getCurrentCountry());
            item.put("addressPostalCode", u.getCurrentZipCode());
            item.put("birthdate", u.getBirthdate());
            item.put("gender", u.getGender());
            item.put("device", u.getDevice());
            item.put("homePhoneNumber", u.getHomePhoneNumber());
            item.put("contactPhoneNumber", u.getContactPhoneNumber());
            item.put("uniCourse", u.getUniCourse());
            item.put("uniYear", (u.getUniYear() != null) ? u.getUniYear().toString() : "");
            item.put("uniIsSupplementaryYear", u.getUniIsSupplementaryYear());
            item.put("uniDepartment", u.getUniDepartment());
            item.put("uniCodCourse", u.getUniCodCourse());
            item.put("uniPhase", u.getUniPhase());
            item.put("uniStatus", u.getUniStatus());
            item.put("notes", u.getNotes());
            item.put("emailPrimary", u.getOfficialEmail());
            item.put("emailSecondary", u.getSecondaryEmail());
            item.put("authorizeContact", u.isAuthorizeContact());
            item.put("ageRange", u.getAgeRange());
            if (isAdmin || isResearcher) {
                item.put("documentIdType", u.getDocumentIdType());
                item.put("documentId", u.getDocumentId());
                item.put("mapLat", u.getMapLat());
                item.put("mapLng", u.getMapLng());
            } else {
                item.put("documentIdType", 0L);
                item.put("documentId", 0L);
                item.put("mapLat", null);
                item.put("mapLng", null);
            }
            // Joins
            long institutionId = 0L;
            long schoolCourseId = 0L;
            String institutionName = "";
            String schoolCourseName = "";
            String genderName = "";
            String uniCourseName = "";

            Institutions i = u.getInstitutionId();
            SchoolCourse s = u.getSchoolCourseId();
            try {
                institutionId = (i != null) ? i.getId() : null;
                schoolCourseId = (s != null) ? s.getId() : null;
            } catch (Exception e) {
                // TODO: handle exception
            }
            item.put("institutionId", institutionId);
            item.put("schoolCourseId", schoolCourseId);
            // Nomes para facilitar a vida do angular
            try {
                institutionName = (i != null) ? i.getName() : null;
                schoolCourseName = (i != null) ? s.getName() : null;
                genderName = (u.getGender() != null ? messageSource.getMessage(u.getGender().toString().toLowerCase() + ".title", null, LocaleContextHolder.getLocale()) : null);
                uniCourseName = (u.getUniCourse() != null ? messageSource.getMessage("education." + u.getUniCourse().toString().toLowerCase() + ".title", null, LocaleContextHolder.getLocale()) : null);
            } catch (Exception e) {
                // TODO: handle exception
            }
            item.put("institutionName", institutionName);
            item.put("schoolCourseName", schoolCourseName);
            item.put("genderName", genderName);
            item.put("uniCourseName", uniCourseName);
            // Return
            response.setStatus(true);
            response.setItem(item);
        }
        return response;
    }

    /**
     * Removed um item
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/participant/removed/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
    public @ResponseBody
    ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
        // Removed
        boolean removed = false;
        try {
            if (id != 26529) {
                removed = participantService.removed(id);
            }
        } catch (Exception e) {
            removed = false;
        }
        // Response
        response.setStatus(removed);
        response.setItems(null);
        response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale()) : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
        return response;
    }

    /**
     * Salva/Atualiza um item
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/participant/save/", "/protected/participant/edit/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST')")
    public @ResponseBody
    ResponseJson save(@RequestBody String json) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Values
        long id = Useful.convertStringToLong(r.getAsString("id"));
        String name = r.getAsString("name");
        String surname = r.getAsString("surname");
        String fileSource = r.getAsString("fileSource");
        String officialEmail = r.getAsString("officialEmail");
        String emailSecondary = r.getAsString("emailSecondary");
        String birthdate = r.getAsString("birthdate");
        String dt_format = r.getAsString("dt_format");
        boolean hasAllowOmbudsman = r.getAsBoolean("hasAllowOmbudsman");
        // Credencias
        String password = r.getAsString("npassword");
        String rpassword = r.getAsString("rpassword");
        // Geo/Address
        String address = r.getAsString("address");
        String addressCity = r.getAsString("addressCity");
        String addressDistrict = r.getAsString("addressDistrict");
        String addressPostalCode = r.getAsString("addressPostalCode");
        String addressNumber = Useful.removeAllNonNumeric(r.getAsString("addressNumber"));
        String addressState = r.getAsString("addressState");
        String addressCountry = r.getAsString("addressCountry");
        String mapLat = r.getAsString("mapLat");
        String mapLng = r.getAsString("mapLng");
        // Contact
        String contactPhoneNumber = Useful.removeAllNonNumeric(r.getAsString("contactPhoneNumber"));
        String device = r.getAsString("device");
        String documentNumber = r.getAsString("documentId");
        String documentidType = r.getAsString("documentIdType");
        String gender = r.getAsString("gender");
        String homePhoneNumber = Useful.removeAllNonNumeric(r.getAsString("homePhoneNumber"));
        String ageRange = r.getAsString("ageRange");
        boolean authorizeContact = r.getAsBoolean("authorizeContact");
        // Educacao
        long institutionId = Useful.convertStringToLong(r.getAsString("institutionId"));
        long schoolCourseId = Useful.convertStringToLong(r.getAsString("schoolCourseId"));
        String uniCourse = r.getAsString("uniCourse");
        boolean uniIsSupplementaryYear = r.getAsBoolean("uniIsSupplementaryYear");
        int uniYear = !Validator.isEmptyString(r.getAsString("uniYear")) ? r.getAsInt("uniYear") : 0;
        String uniDepartment = r.getAsString("uniDepartment");
        String uniCodCourse = r.getAsString("uniCodCourse");
        int uniPhase = !Validator.isEmptyString(r.getAsString("uniPhase")) ? r.getAsInt("uniPhase") : 0;
        String uniStatus = r.getAsString("uniStatus");
        // Notes
        String notes = r.getAsString("notes");
        // Validate
        try {
            if (!Validator.isValidStringLength(name, 1, 100)) {
                throw new Exception(messageSource.getMessage("protected.participant.name", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidEmail(officialEmail)) {
                throw new Exception(messageSource.getMessage("protected.participant.email", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("label.login.invalid.email", null, LocaleContextHolder.getLocale()));
                // Password
            } else if (id == 0 && !Validator.isValidPassword(password) || !Validator.isEmptyString(password) && !Validator.isValidPassword(password)) {
                throw new Exception(messageSource.getMessage("label.password.insecure", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isEmptyString(password) && !Validator.isStringEquals(password, rpassword)) {
                throw new Exception(messageSource.getMessage("error.password.mistach", null, LocaleContextHolder.getLocale()));
            } else {
                // Check
                User hasUser = participantService.findByEmail(officialEmail);
                if (hasUser != null && hasUser.getId() != id) {
                    throw new Exception(messageSource.getMessage("alreadyexists.addAccountForm.username", new Object[]{officialEmail}, LocaleContextHolder.getLocale()));
                } else {
                    // Builder
                    UserBuilder ub = new UserBuilder();
                    // New or Update
                    if (hasUser == null && id == 0) {
                        ub.setCredentials(officialEmail, password);
                    } else {
                        if (hasUser != null) {
                            ub.setPhoto(hasUser.getPhoto());
                            ub.setPassword(hasUser.getPassword());
                            ub.setProgenitorId(hasUser.getProgenitorId());
                        } else {
                            // Segunda checagem
                            hasUser = participantService.findById(id);
                            if (hasUser == null) {
                                throw new Exception(messageSource.getMessage("synchronization.failed", new Object[]{officialEmail}, LocaleContextHolder.getLocale()));
                            } else {
                                ub.setPhoto(hasUser.getPhoto());
                                ub.setPassword(hasUser.getPassword());
                                ub.setProgenitorId(hasUser.getProgenitorId());
                            }
                        }
                    }
                    // Fix issue senha
                    if (!Validator.isEmptyString(password) && Validator.isValidPassword(password)) {
                        ub.setCredentials(officialEmail, password);
                    }
                    // Enum
                    Gender enumGender = EnumUtils.isValidEnum(Gender.class, gender) ? Gender.valueOf(gender) : Gender.NONE;
                    DocumentIdType enumDocumentType = EnumUtils.isValidEnum(DocumentIdType.class, documentidType) ? DocumentIdType.valueOf(documentidType) : DocumentIdType.NONE;
                    UniCourse enumCourse = EnumUtils.isValidEnum(UniCourse.class, uniCourse) ? UniCourse.valueOf(uniCourse) : UniCourse.NONE;
                    Institutions iu = institutionsService.findById(institutionId);
                    SchoolCourse su = schoolCourseService.findById(schoolCourseId);
                    // Date
                    LocalDate dt = new LocalDate();
                    if (Validator.isValidDateFormat(dt_format, "dd/MM/yyyy")) {
                        dt = new LocalDate(Useful.getDateUSFromBR(dt_format));
                    } else if (Validator.isValidDate(birthdate)) {
                        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
                        dt = dtf.parseLocalDate(birthdate);
                    } else {
                        dt = null;
                    }
                    // Set
                    ub.setAll(id, officialEmail, surname, name, enumGender, dt, contactPhoneNumber, address, addressCity, addressState, addressPostalCode, addressNumber, addressCountry, documentNumber, enumDocumentType, homePhoneNumber,
                            enumCourse, uniIsSupplementaryYear, uniYear, device, notes, iu, su, mapLat, mapLng);
                    ub.setUniDepartment(uniDepartment);
                    ub.setUniStatus(uniCodCourse);
                    ub.setUniPhase(uniPhase);
                    ub.setUniStatus(uniStatus);
                    ub.setFileSource(fileSource);
                    ub.setCurrentDistrict(addressDistrict);
                    ub.setAuthorizeContact(authorizeContact);
                    ub.setAgeRange(ageRange);
                    ub.setHasAllowOmbudsman(hasAllowOmbudsman);
                    if(!Validator.isEmptyString(emailSecondary) && Validator.isValidEmail(emailSecondary)){
                        ub.setSecondaryEmail(emailSecondary);
                    }
                    // Save
                    User p = ub.build(true);
                    User rs = participantService.saveOrUpdate(p);
                    if (rs != null) {
                        response.setStatus(true);
                        response.setOutcome(rs.getId());
                    }
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Busca customizada
     *
     * @param json
     * @param count
     * @param offset
     * @return
     */
    @RequestMapping(value = "/protected/participant/search/{count}/{offset}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_COOPERATION_AGREEMENT','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
        // Response
        response.setStatus(true);
        response.setCount(count);
        response.setOffset(offset);
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        boolean isCloudDownload = false;
        boolean isAdvancedSearch = false;
        boolean isIssueSearch = false;
        Long taskId = 0L;
        // Request
        //System.out.println("ParticipantController search " + json.toString());
        try {
            ReceiveJson r = new ReceiveJson(json);
            isAdvancedSearch = r.getAsBoolean("isAdvancedSearch");
            isCloudDownload = r.getAsBoolean("isCloudDownload");
            isIssueSearch = r.getAsBoolean("isIssueSearch");
            if (isIssueSearch) {
                params.put("isIssueSearch", isIssueSearch);
            }
            taskId = r.getAsLong("taskId");
            // Task Id
            if (taskId > 0) {
                params.put("taskId", taskId);
            }
            // Search
            if (isAdvancedSearch) {
                // Json
                ReceiveAdvancedSearch[] res = r.getAsAdvancedSearch("hashmap");
                params = advancedQueryParameters(res, params);
            } else {
                String name = r.getAsString("name");
                if (!Validator.isEmptyString(name)) {
                    params.put("name", name);
                }
                String username = r.getAsString("username");
                if (!Validator.isEmptyString(username)) {
                    params.put("officialEmail", username);
                }
                String wildcard = r.getAsString("search");
                if (!Validator.isEmptyString(wildcard)) {
                    params.put("wildcard", wildcard);
                }
                String uniCourse = r.getAsString("uniCourse");
                if (!Validator.isEmptyString(uniCourse) && EnumUtils.isValidEnum(UniCourse.class, uniCourse)) {
                    UniCourse enumCourse = UniCourse.valueOf(uniCourse);
                    params.put("uniCourse", enumCourse);
                }
                long institutionId = Useful.convertStringToLong(r.getAsString("institutionId"));
                if (institutionId > 0) {
                    Institutions iu = institutionsService.findById(institutionId);
                    if (iu != null) {
                        params.put("institutionId", iu);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("ParticipantController search " + e.getMessage());
        }
        // Busca
        List<Object[]> items = participantService.search(params, PaginationUtil.pagerequest(offset, count));
        if (items.size() > 0) {
            // Translate
            int index = 0;
            for (Object[] item : items) {
                item[11] = messageSource.getMessage(item[11].toString().toLowerCase() + ".title", null, LocaleContextHolder.getLocale());
                Long progenitorId = item[18] != null ? Long.parseLong(item[18].toString()) : 0;
                item = Useful.appendValue(item, (progenitorId != null && progenitorId > 0) ? getSmallObject(participantService.findParticipantById(progenitorId)) : null);
                items.set(index, item);
                index++;
            }
            // Total
            response.setTotal(participantService.searchTotal(params));
        }
        response.setItems(items);
        // Para download?
        if (isCloudDownload) {
            if (items.size() > 0) {
                response.setResultType(ResultType.TYPE_INFO);
                response.setMessage(messageSource.getMessage("confirmation.exporting", null, LocaleContextHolder.getLocale()));
                createUserCsvFile(params);
            } else {
                response.setResultType(ResultType.TYPE_ERROR);
                response.setMessage(messageSource.getMessage("download.error.data", null, LocaleContextHolder.getLocale()));
            }
        }

        return response;
    }

    /**
     * Consulta customizada
     *
     * @return
     */
    public List<User> filter(ListMultimap<String, Object> params, PageRequest pageable) {
        return participantService.filter(params, pageable);
    }

    public Long searchTotal(ListMultimap<String, Object> params) {
        return participantService.searchTotal(params);
    }

    /**
     * Preformata os parametros de uma consulta avancada
     *
     * @param res
     * @param params
     * @return
     */
    public ListMultimap<String, Object> advancedQueryParameters(ReceiveAdvancedSearch[] res, ListMultimap<String, Object> params) {
        if (res != null && res.length > 0) {
            // Loop validacao
            for (ReceiveAdvancedSearch item : res) {

                System.out.println(item.getKey());
                System.out.println(EnumUtils.isValidEnum(FilterByUser.class, item.getKey()));
                // Validacao de existencia
                if (EnumUtils.isValidEnum(FilterByUser.class, item.getKey())) {
                    String name = FilterByUser.valueOf(item.getKey()).toString();
                    Object value = null;
                    boolean isOk = false;
                    System.out.println(name);
                    // Verificando itens enviados
                    if (Validator.isStringEquals(FilterByUser.FILTER_GENDER.name(), item.getKey()) && EnumUtils.isValidEnum(Gender.class, item.getItem())) {
                        Gender enumGender = Gender.valueOf(item.getItem());
                        params.put(name, enumGender);
                        isOk = true;
                    } else if (Validator.isStringEquals(FilterByUser.FILTER_UNICOURSE.name(), item.getKey()) && EnumUtils.isValidEnum(UniCourse.class, item.getItem())) {
                        UniCourse enumUniCourse = UniCourse.valueOf(item.getItem());
                        params.put(name, enumUniCourse);
                        isOk = true;
                    } else if (Validator.isStringEquals(FilterByUser.FILTER_DOCUMENTIDTYPE.name(), item.getKey()) && EnumUtils.isValidEnum(DocumentIdType.class, item.getItem())) {
                        DocumentIdType enumDocumentIdType = DocumentIdType.valueOf(item.getItem());
                        params.put(name, enumDocumentIdType);
                        isOk = true;
                    } else if (Validator.isStringEquals(FilterByUser.FILTER_SCHOOLCOURSEID.name(), item.getKey()) || Validator.isStringEquals(FilterByUser.FILTER_INSTITUTIONID.name(), item.getKey())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        // Object
                        if (num > 0) {
                            Object obj = (Validator.isStringEquals(FilterByUser.FILTER_SCHOOLCOURSEID.name(), item.getKey())) ? schoolCourseService.findById(num) : institutionsService.findById(num);
                            // Verify
                            if (obj != null) {
                                params.put(name, obj);
                                isOk = true;
                            }
                        }
                    } else if (Validator.isStringEquals(FilterByUser.FILTER_UNIYEAR.name(), item.getKey())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        if (num > 0) {
                            params.put(name, num);
                            isOk = true;
                        }

                    } else if (item.getKey().equals(FilterByUser.FILTER_TASKID.name())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        if (num > 0) {
                            params.put(name, num);
                            isOk = true;
                        }

                    } else if (item.getKey().equals(FilterByUser.FILTER_TASKID_RUNNING.name())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        if (num > 0) {
                            params.put(name, num);
                            isOk = true;
                        }
                        item.setLabel("warning");
                    } else if (item.getKey().equals(FilterByUser.FILTER_USERLISTID.name())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        if (num > 0) {
                            params.put(name, num);
                            isOk = true;
                        }
                        item.setLabel("warning");
                    } else if (item.getKey().equals(FilterByUser.FILTER_START.name())) {
                        // date string
                        String dt = item.getValue();
                        // Object
                        if (!Validator.isEmptyString(dt) && Validator.isValidDateFormat(dt, "dd/MM/yyyy")) {
                            String str = Useful.getDateUSFromBR(dt).toString();
                            if (!Validator.isEmptyString(str) && Validator.isValidDate(str)) {
                                params.put(FilterByUser.FILTER_CREATIONDATE.toString(), str);
                                isOk = true;
                            }
                        }
                    } else if (item.getKey().equals(FilterByUser.FILTER_USERID.name())) {
                        // Long
                        long num = Useful.convertStringToLong(item.getItem());
                        if (num > 0) {
                            params.put(name, num);
                            isOk = true;
                        }
                        item.setLabel("warning");
                    } else {
                        if (!Validator.isEmptyString(item.getValue())) {
                            item.setItem(item.getValue());
                            value = item.getValue();
                            params.put(name, value);
                            isOk = true;
                        }
                    }
                    if (!isOk) {
                        item.setLabel("danger");// Feedback para o
                        // usuario
                    }
                } else {
                    item.setLabel("danger");
                }
            }
        }
        // Feedback
        response.setItem(res);
        // Return
        return params;
    }

    /**
     * Solicita o download da consulta
     *
     * @param params
     */
    @Async
    private void createUserCsvFile(ListMultimap<String, Object> params) {
        // Id do usuario
        AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long parentId = current.getId();
        // Consulta
        List<Object[]> items = participantService.search(params, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
        fileExportService.createUserCsvFile(items, parentId);
    }
}