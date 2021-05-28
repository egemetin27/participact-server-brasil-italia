package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.ManifestacaoResposta;
import br.gov.cgu.eouv.domain.OrgaoSiorgOuvidoria;
import br.gov.cgu.eouv.domain.Ouvidoria;
import br.gov.cgu.eouv.service.ManifestacaoService;
import br.gov.cgu.eouv.service.OrgaoSiorgOuvidoriaService;
import br.gov.cgu.eouv.service.OuvidoriaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.service.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Issue do Aplicativo
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@Controller
public class IssueReportController extends ApplicationController {
    @Autowired
    OuvidoriaService ouvidoriaService;
    @Autowired
    OrgaoSiorgOuvidoriaService orgaoSiorgOuvidoriaService;
    @Autowired
    ManifestacaoService manifestacaoService;
    @Autowired
    private IssueReportService issueReportService;
    @Autowired
    private IssueCategoryService issueCategoryService;
    @Autowired
    private IssueSubCategoryService issueSubCategoryService;
    @Autowired
    private FileExportService fileExportService;
    @Autowired
    private StorageFileService storageFileService;
    @Autowired
    private MessageSource messageSource;

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/issue-report/index", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        isRoot(request);
        modelAndView.setViewName("/protected/issue-report/index");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("categories", issueCategoryService.findAll());
        return modelAndView;
    }

    /**
     * Mapa inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/issue-report/map", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public ModelAndView map(ModelAndView modelAndView, HttpServletRequest request) {
        isRoot(request);
        modelAndView.setViewName("/protected/issue-report/map");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        return modelAndView;
    }

    /**
     * Formulario
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/issue-report/form", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ModelAndView form(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/issue-report/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        return modelAndView;
    }

    /**
     * Edicao de item
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/issue-report/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
        // Model view
        modelAndView.setViewName("/protected/issue-report/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("form", id);
        return modelAndView;
    }

    /**
     * Edicao de item
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/issue-report/edit/{id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
        // Response
        ResponseJson response = new ResponseJson();
        // Search
        IssueReport fr = issueReportService.findById(id);
        if (fr != null) {
            //Map
            HashMap<String, Object> map = new HashMap<String, Object>();
            //User
            User user = fr.getUser();
            HashMap<String, Object> issueParticipant = new HashMap<String, Object>();
            if (user != null) {
                map.put("user_name", user.getName());
                map.put("user_officialemail", user.getOfficialEmail());

                issueParticipant.put("id", user.getId());
                issueParticipant.put("name", user.getName());
                issueParticipant.put("email", user.getOfficialEmail());
                issueParticipant.put("secondary", fr.getPublicEmail());
            }
            map.put("issueParticipant", issueParticipant);
            //Category
            HashMap<String, Object> issueSubcategory = new HashMap<String, Object>();
            IssueSubCategory is = fr.getSubcategory();
            if (is != null) {
                issueSubcategory.put("id", is.getId());
                issueSubcategory.put("name", is.getName());
                issueSubcategory.put("icon", is.getUrlAsset());
                issueSubcategory.put("category", is.getCategory().getName());
            }
            map.put("issueSubcategory", issueSubcategory);
            //Ombudsman
            HashMap<String, Object> issueOmbudsman = new HashMap<String, Object>();
            Ouvidoria om = ouvidoriaService.find(fr.getOmbudsmanId());
            if (om != null) {
                issueOmbudsman.put("id", om.getIdOuvidoria());
                issueOmbudsman.put("name", om.getNomeOrgaoOuvidoria());
                issueOmbudsman.put("sphere", om.getDescEsfera());
                issueOmbudsman.put("county", om.getDescMunicipio());
            }
            map.put("issueOmbudsman", issueOmbudsman);
            //Ombudsman
            HashMap<String, Object> issueSiorg = new HashMap<String, Object>();
            OrgaoSiorgOuvidoria so = orgaoSiorgOuvidoriaService.find(fr.getSiOrgId());
            if (so != null) {
                issueSiorg.put("id", so.getCodOrg());
                issueSiorg.put("name", so.getNomOrgao());
            }
            map.put("issueSiorg", issueSiorg);
            //Historico
            map.put("issueCguManifestacaoHistorico", manifestacaoService.getListaManifestacaoHistorico(fr.getId()));
            map.put("issueCguManifestacaoResposta", manifestacaoService.getListaManifestacaoResposta(fr.getId()));
            //Resposta
            //readonly
            map.put("readonly", fr.isOmbudsman());
            //Fix
            fr.setPublicUrl(fr.getPrivateRelEouv());
            //Secundarias
            // Params / MultiMap
            ListMultimap<String, Object> params = ArrayListMultimap.create();
            params.put("parentId", fr.getId());
            params.put("isSecondary", true);
            map.put("issueOmbudsmanList", issueReportService.search(params, null));
            //Return
            response.setStatus(true);
            response.setItem(fr);
            response.setPayload(map);
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
    @RequestMapping(value = "/protected/issue-report/removed/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public @ResponseBody
    ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
        // Removed
        boolean removed = issueReportService.removed(id);
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(removed);
        response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale()) : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
        return response;
    }

    /**
     * Salva/Atualiza um item
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/issue-report/save/", "/protected/issue-report/edit/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public @ResponseBody
    ResponseJson save(@RequestBody String json, HttpServletRequest request) {
        // Root
        isRoot(request);
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Values
        String id = (r.getAsString("id") == null) ? "0" : r.getAsString("id");
        String comment = r.getAsString("comment");
        Long negativeScore = r.getAsLong("negativeScore");

        String formattedAddress = r.getAsString("formatted_address");
        String provider = r.getAsString("provider");
        Double latitude = r.getAsDouble("latitude");
        Double longitude = r.getAsDouble("longitude");
        Double altitude = r.getAsDouble("altitude");
        Double accuracy = r.getAsDouble("accuracy");
        Double speed = r.getAsDouble("speed");
        Double course = r.getAsDouble("course");
        Double floor = r.getAsDouble("floor");
        Double horizontalAccuracy = r.getAsDouble("horizontalAccuracy");
        Double verticalAccuracy = r.getAsDouble("verticalAccuracy");
        //files
        List<StorageFile> files = new ArrayList<StorageFile>();
        JsonArray issueFiles = r.getAsJsonArray("files");
        if (issueFiles != null && issueFiles.isJsonArray() && issueFiles.size() > 0) {
            for (JsonElement f : issueFiles) {
                if (f.isJsonObject()) {
                    JsonObject file = f.getAsJsonObject();
                    if (file.has("id") && file.has("assetUrl") && file.has("discriminatorType") && file.has("fileExtension")) {
                        //Required
                        Long fId = file.get("id").isJsonNull() ? null : file.get("id").getAsLong();
                        fId = fId != null ? fId : 0L;
                        //Find
                        StorageFile sf = null;
                        if (fId > 0) {
                            sf = storageFileService.findById(fId);
                        }
                        //Add
                        if (sf == null) {
                            //New
                            String assetUrl = file.get("assetUrl").getAsString();
                            String originalFilename = file.get("originalFilename").getAsString();
                            String discriminatorType = file.get("discriminatorType").getAsString();
                            String fileExtension = file.get("fileExtension").getAsString();
                            String fileName = file.get("fileName").getAsString();
                            //Storage
                            if (discriminatorType.equals("I")) {
                                sf = new StorageFileImage();
                                sf.setId(null);
                                sf.setAssetUrl(assetUrl);
                                sf.setAssetUrlFourth(assetUrl);
                                sf.setAssetUrlThird(assetUrl);
                                sf.setAssetUrlWeb(assetUrl);
                                sf.setFileName(fileName);
                                sf.setFileExtension(fileExtension);
                                sf.setOriginalFilename(originalFilename);
                                sf.setAccuracy(accuracy);
                                sf.setLatitude(latitude);
                                sf.setLongitude(longitude);
                                sf.setAltitude(altitude);
                                sf.setProvider(provider);
                                sf.setIpAddress(request.getRemoteAddr());
                                //Save
                                StorageFile s = storageFileService.saveOrUpdate(sf);
                                if (s != null) {
                                    files.add(s);
                                }
                            }
                        }
                    }
                }
            }
        }
        //issueOmbudsman
        Long ombudsmanId = 0L;
        JsonObject issueOmbudsman = r.getAsJsonObject("issueOmbudsman");
        if (issueOmbudsman.has("id")) {
            ombudsmanId = issueOmbudsman.get("id").getAsLong();
        }
        //issueParticipant
        String publicEmail = null;
        Long participantId = 0L;
        JsonObject issueParticipant = r.getAsJsonObject("issueParticipant");
        if (issueParticipant.has("id")) {
            participantId = issueParticipant.get("id").getAsLong();
            publicEmail = issueParticipant.get("secondary").getAsString();
        }
        //issueSubcategory
        Long subcategoryId = 0L;
        JsonObject issueSubcategory = r.getAsJsonObject("issueSubcategory");
        if (issueSubcategory.has("id")) {
            subcategoryId = issueSubcategory.get("id").getAsLong();
        }
        //issueSiorg
        Long siorgId = 0L;
        JsonObject issueSiorg = r.getAsJsonObject("issueSiorg");
        if (issueSiorg.has("id")) {
            siorgId = issueSiorg.get("id").getAsLong();
        }
        //System.out.println(issueSubcategory);
        // Validate
        try {
            if (!Validator.isValidNumeric(id)) {
                throw new Exception(messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidStringLength(comment, 1, 100)) {
                throw new Exception(messageSource.getMessage("comment.title", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else {
                // id
                long uuid = Long.parseLong(id);
                IssueReport ir = new IssueReport();
                if (uuid > 0) {// Edicao
                    ir = issueReportService.findById(uuid);
                    if (ir == null) {
                        ir = new IssueReport();
                        ir.setId(null);
                    }
                } else {
                    ir.setId(null);
                    //Date
                    ir.setSampleTimestamp(new DateTime());
                    ir.setDataReceivedTimestamp(new DateTime());
                }
                ir.setFormattedAddress(formattedAddress);
                ir.setComment(comment);
                ir.setNegativeScore(negativeScore);

                ir.setProvider(provider);
                ir.setAccuracy(accuracy);
                ir.setAltitude(altitude);
                ir.setCourse(course);
                ir.setFloor(floor);
                ir.setHorizontalAccuracy(horizontalAccuracy);
                ir.setVerticalAccuracy(verticalAccuracy);
                ir.setSpeed(speed);
                ir.setLatitude(latitude);
                ir.setLongitude(longitude);
                //Join
                //Sub Category
                IssueSubCategory is = issueSubCategoryService.findById(subcategoryId);
                ir.setSubcategory(is);
                //User
                User u = participantService.findParticipantById(participantId);
                ir.setUser(u);
                ir.setPublicEmail(publicEmail);
                //Ombudsman
                ir.setOmbudsmanId(ombudsmanId);
                //SiOrg
                ir.setSiOrgId(siorgId);
                //Flags
                ir.setOmbudsman((siorgId > 0 || ombudsmanId > 0));
                //Save/Update
                IssueReport rs = issueReportService.saveOrUpdate(ir);
                if (rs != null) {
                    //Files
                    List<StorageFile> fs = rs.getFiles();
                    fs.addAll(files);
                    rs.setFiles(fs);
                    //Return
                    IssueReport rowIssue = issueReportService.saveOrUpdate(rs);
                    response.setStatus(rowIssue != null);
                    response.setOutcome(rs.getId());
                    if (uuid == 0 && isAdmin) {
                        registerThirdPartyManifestation(rs);
                    }
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace(System.out);
            response.setMessage(e.getMessage());
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
    @RequestMapping(value = "/protected/issue-report/search/{count}/{offset}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json, @PathVariable int count, @PathVariable int offset) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setCount(count);
        response.setOffset(offset);
        response.setItems(new ArrayList<Object[]>());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        try {
            String search = r.getAsString("search");
            if (!Validator.isEmptyString(search)) {
                params.put("search", search);
            }
            String start = r.getAsString("start");
            if (!Validator.isEmptyString(start) && Validator.isValidDateFormat(start, "dd/MM/yyyy")) {
                start = Useful.converteDateToString(Useful.converteStringToDatePattern(start, "dd/MM/yyyy"), "yyyy-MM-dd HH:mm:ss");
                params.put("start", start);
            }
            String end = r.getAsString("end");
            if (!Validator.isEmptyString(end) && Validator.isValidDateFormat(end, "dd/MM/yyyy")) {
                end = Useful.converteDateToString(Useful.converteStringToDatePattern(end, "dd/MM/yyyy"), "yyyy-MM-dd HH:mm:ss").replace("00:00:00", "23:59:59");
                params.put("end", end);
            }
            String category_id = r.getAsString("category_id");
            if (!Validator.isEmptyString(category_id) && Validator.isValidNumeric(category_id)) {
                params.put("category_id", category_id);
            }
            // Municipality
            System.out.println(userMunicipality);
            if (!Validator.isEmptyString(userMunicipality)) {
                params.put("municipality", userMunicipality);
            }
            // Default
            params.put("isSecondary", false);
            //System.out.println(params.toString());
            // Search
            List<Object[]> items = issueReportService.searchByNativeQuery(params, PaginationUtil.pagerequest(offset, count));
            response.setItems(items);
            if (items.size() > 0) {
                for (Object[] item : items) {
                    if (item != null) {
                        Long userId = Long.parseLong(item[21].toString());
//                        item[5] = new DateTime(Long.parseLong(item[5].toString()));
//                        item[6] = new DateTime(Long.parseLong(item[6].toString()));
                        String i22 = item[22] != null ? "" : item[22].toString();
                        Long progenitorId = Long.parseLong(!Validator.isEmptyString(i22) ? i22 : "0");
                        boolean isGuest = Boolean.parseBoolean(item[23].toString());
                        try {
                            String secondaryEmail = item[24].toString();
                            if (!Validator.isEmptyString(secondaryEmail) && Validator.isValidEmail(secondaryEmail)) {
                                item[2] = secondaryEmail;
                            }
                        } catch (Exception e) {
                        }
                        // Check
                        if (isGuest && progenitorId != null && progenitorId > 0 && progenitorId.longValue() != userId.longValue()) {
                            User u = participantService.findParticipantById(progenitorId);
                            if (u != null) {
                                item[1] = u.getName();
                            }
                        }
                        // Contem ouvidoria?
                        boolean isOmbudsman = Boolean.parseBoolean(item[25].toString());
                        item[34] = "N/A";
                        if (isOmbudsman) {
                            ManifestacaoResposta resposta = manifestacaoService.getLastManifestacaoResposta(Long.parseLong(item[0].toString()));
                            if (resposta != null) {
                                item[34] = resposta.getTipo();
                            }
                        }
                        //Omb List
                        String ombList = item[35] != null ? item[35].toString() : null;
                        if (ombList != null && Validator.isValidJson(ombList)) {
                            item[35] = new Gson().fromJson(ombList, List.class);
                        } else {
                            item[35] = new ArrayList<>();
                        }
                    }
                }
                response.setTotal(issueReportService.searchTotal(params));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("IssueReport search " + e.getMessage());
        }
        // return
        return response;
    }

    /**
     * Exportando Pontos
     *
     * @param json
     * @param request
     * @return
     */
    @RequestMapping(value = "/protected/issue-report/export/", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_FIRST','ROLE_RESEARCHER_SECOND', 'ROLE_RESEARCHER_OMBUDSMAN', 'ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT', 'ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR', 'ROLE_RESEARCHER_OMBUDSMAN_EDITOR')")
    public @ResponseBody
    ResponseJson export(@RequestBody String json, HttpServletRequest request) {
        isRoot(request);
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setResultType(ResultType.TYPE_INFO);
        response.setMessage(messageSource.getMessage("confirmation.exporting", null, LocaleContextHolder.getLocale()));
        // Create File
        createIssueCsvFile();
        // Return
        return response;
    }

    /**
     * Background Process
     */
    @Async
    private void createIssueCsvFile() {
        fileExportService.createIssueCsvFile(userId, userMunicipality);
    }


    /**
     * Registrando uma manifestacao
     */
    @Async
    private void registerThirdPartyManifestation(IssueReport issue) {
        manifestacaoService.registerThirdPartyManifestation(issue);
    }
}