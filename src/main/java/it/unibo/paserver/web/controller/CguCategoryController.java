package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Validator;
import br.gov.cgu.eouv.domain.Ouvidoria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.IssueCategoryBuilder;
import it.unibo.paserver.domain.support.IssueSubCategoryBuilder;
import it.unibo.paserver.service.IssueCategoryService;
import it.unibo.paserver.service.IssueSubCategoryHasRelationshipService;
import it.unibo.paserver.service.IssueSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Categorias vinculadas a Cgu
 */
@SuppressWarnings("Duplicates")
@Controller
public class CguCategoryController {
    @Autowired
    private IssueCategoryService issueCategoryService;
    @Autowired
    private IssueSubCategoryService issueSubCategoryService;
    @Autowired
    private IssueSubCategoryHasRelationshipService issueSubCategoryHasRelationshipService;
    @Autowired
    private MessageSource messageSource;

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/cgu-category/index", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-category/index");
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
    @RequestMapping(value = "/protected/cgu-category/form", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView form(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/cgu-category/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        return modelAndView;
    }

    /**
     * Edicao de item
     *
     * @param id
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/cgu-category/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
        // Model view
        modelAndView.setViewName("/protected/cgu-category/form");
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
    @RequestMapping(value = "/protected/cgu-category/edit/{id}/find", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
        // Response
        ResponseJson response = new ResponseJson();
        // Search
        IssueCategory ic = issueCategoryService.findById(id);
        if (ic != null) {
            List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
            List<IssueSubCategory> subcategories = ic.getSubcategories();
            if (subcategories != null && subcategories.size() > 0) {
                for (IssueSubCategory is_item : subcategories) {
                    HashMap<String, Object> value = new HashMap<String, Object>();
                    if (!is_item.isRemoved()) {
                        //Relacoes
                        List<Ouvidoria> ouvs = new ArrayList<>();
                        List<Object[]> rels = issueSubCategoryHasRelationshipService.getListRelationOuvidoria(is_item.getId());
                        if (rels != null && rels.size() > 0) {
                            for (Object[] r : rels) {
                                Long idOuvidoria = Long.parseLong(r[2].toString());
                                String nomeOuvidoria = r[3].toString();
                                //Add
                                ouvs.add(new Ouvidoria(idOuvidoria, nomeOuvidoria));
                            }
                        }
                        //Map
                        value.put("id", is_item.getId());
                        value.put("urlAsset", is_item.getUrlAsset());
                        value.put("name", is_item.getName());
                        value.put("sequence", is_item.getSequence());
                        value.put("ombudsmen", ouvs);
                        map.add(value);
                        //System.out.println(value.toString());
                    }
                }
            }
            ic.setMap(map);
            //Set
            response.setStatus(true);
            response.setItem(ic);
        } else {
            response.setItem(new ArrayList<IssueCategory>());
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
    @RequestMapping(value = "/protected/cgu-category/removed/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson removed(@PathVariable("id") long id) throws JsonProcessingException {
        // Removed
        boolean removed = issueCategoryService.removed(id);
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(removed);
        response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale())
                : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
        return response;
    }

    /**
     * Salva/Atualiza um item
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/cgu-category/save/", "/protected/cgu-category/edit/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson save(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            IssueCategory ic = null;
            Long categoryId = r.getAsLong("id");
            String categoryName = r.getAsString("name");
            String color = r.getAsString("color");
            String categoryUrlAsset = r.getAsString("urlAsset");
            String categoryUrlIcon = r.getAsString("urlIcon");

            Map<Integer, JsonObject> subcategories = r.getAsJsonMap("subcategories");
            if (subcategories.size() < 1) {
                response.setMessage(messageSource.getMessage("error.subcategories.required", null, LocaleContextHolder.getLocale()));
            } else {
                // Update
                if (categoryId != null && categoryId > 0) {
                    ic = issueCategoryService.findById(categoryId);
                    if (ic != null) {
                        ic.setName(categoryName);
                        ic.setUrlAsset(categoryUrlAsset);
                        ic.setUrlIcon(categoryUrlIcon);
                        ic.setSequence(0);
                        ic.setRemoved(false);
                        ic.setColor(color);
                    }
                }
                //New
                if (ic == null) {
                    IssueCategoryBuilder icb = new IssueCategoryBuilder();
                    icb.setId(null);
                    icb.setName(categoryName);
                    icb.setUrlAsset(categoryUrlAsset);
                    icb.setUrlIcon(categoryUrlIcon);
                    icb.setSequence(0);
                    icb.setRemoved(false);
                    icb.setColor(color);
                    ic = icb.build(true);
                }
                //Check
                IssueCategory changed = issueCategoryService.saveOrUpdate(ic);
                if (changed != null) {
                    //Remove All
                    issueSubCategoryService.removeAll(changed.getId());
                    //Subcategories
                    int sequence = 0;
                    for (JsonElement elm : subcategories.values()) {
                        if (elm.isJsonObject()) {
                            JsonObject ll = elm.getAsJsonObject();
                            if (ll.has("name") && !Validator.isEmptyString(ll.get("name").getAsString())) {
                                Long subId = ll.get("id").isJsonNull() || !ll.has("id") ? 0 : ll.get("id").getAsLong();
                                String subName = ll.get("name").isJsonNull() || !ll.has("name") ? "N/A" : ll.get("name").getAsString();
                                String subUrlAsset = null;
                                try {
                                    subUrlAsset = ll.get("urlAsset").isJsonNull() || !ll.has("urlAsset") ? null : ll.get("urlAsset").getAsString();
                                } catch (Exception e) {
                                }
                                // Sub
                                IssueSubCategoryBuilder sb = new IssueSubCategoryBuilder();
                                sb.setId(subId != null && subId > 0 ? subId : null);
                                sb.setSequence(sequence++);
                                sb.setName(subName);
                                sb.setUrlAsset(subUrlAsset);
                                sb.setRemoved(false);
                                sb.setCategory(changed);
                                IssueSubCategory s = sb.build(true);
                                // Add
                                IssueSubCategory sub = issueSubCategoryService.saveOrUpdate(s);
                                if (sub != null) {
                                    //Remove Relations
                                    issueSubCategoryHasRelationshipService.deleteBySubcategoryId(sub.getId());
                                    //Check
                                    if (ll.has("ombudsmen")) {
                                        JsonArray ombudsmen = ll.get("ombudsmen").getAsJsonArray();
                                        for (JsonElement omb : ombudsmen) {
                                            if (elm.isJsonObject()) {
                                                JsonObject men = omb.getAsJsonObject();
                                                if (men.has("idOuvidoria")) {
                                                    Long idOuvidoria = men.get("idOuvidoria").isJsonNull() ? 0 : men.get("idOuvidoria").getAsLong();
                                                    if (idOuvidoria > 0) {
                                                        IssueSubCategoryHasRelationship rel = new IssueSubCategoryHasRelationship();
                                                        rel.setIssueSubcategoryId(sub.getId());
                                                        rel.setRelationshipId(idOuvidoria);
                                                        //Save
                                                        issueSubCategoryHasRelationshipService.save(rel);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //Res
                response.setStatus(true);
                response.setMessage(messageSource.getMessage("updated.title", null, LocaleContextHolder.getLocale()));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setMessage(e.getMessage());
        }
        // Result
        return response;
    }

    /**
     * Busca por categorias
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/cgu-category/search/", "/protected/cgu-category/search"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setCount(10);
        response.setOffset(0);
        response.setItems(new ArrayList<Object[]>());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        try {
            // Search
            String search = r.getAsString("search");
            if (!Validator.isEmptyString(search)) {
                params.put("search", search);
            }
            // Limits
            int count = r.has("count") ? r.getAsInt("count") : 10;
            int offset = r.has("offset") ? r.getAsInt("offset") : 0;
            //Search
            List<Object[]> items = issueCategoryService.search(params, PaginationUtil.pagerequest(offset, count));
            response.setItems(items);
            response.setCount(count);
            response.setOffset(offset);
            //Total
            if (items.size() > 0) {
                response.setTotal(issueCategoryService.searchTotal(params));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setMessage(e.getMessage());
        }
        // return
        return response;
    }

    /**
     * Busca por subcategorias
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/cgu-subcategory/search/", "/protected/cgu-subcategory/search"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson searchSubCategory(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setCount(10);
        response.setOffset(0);
        response.setItems(new ArrayList<Object[]>());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        try {
            // Search
            String search = r.getAsString("search");
            if (!Validator.isEmptyString(search)) {
                params.put("search", search);
            }
            // Limits
            int count = r.has("count") ? r.getAsInt("count") : 10;
            int offset = r.has("offset") ? r.getAsInt("offset") : 0;
            //Search
            List<Object[]> items = issueSubCategoryService.search(params, PaginationUtil.pagerequest(offset, count));
            response.setItems(items);
            response.setCount(count);
            response.setOffset(offset);
            //Total
            if (items.size() > 0) {
                response.setTotal(issueSubCategoryService.searchTotal(params));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setMessage(e.getMessage());
        }
        // return
        return response;
    }
}