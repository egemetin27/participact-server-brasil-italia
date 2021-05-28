package it.unibo.paserver.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.PaginationUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import it.unibo.paserver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.support.IssueCategoryBuilder;
import it.unibo.paserver.domain.support.IssueSubCategoryBuilder;
import it.unibo.paserver.service.IssueCategoryService;
import it.unibo.paserver.service.IssueSubCategoryService;

/**
 * Categorias dos Problemas Publicos
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
@Controller
public class IssueCategoryController {
    @Autowired
    private IssueCategoryService issueCategoryService;
    @Autowired
    private IssueSubCategoryService issueSubCategoryService;
    @Autowired
    private MessageSource messageSource;

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/issue-category/index", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/issue-category/index");
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
    @RequestMapping(value = "/protected/issue-category/form", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public ModelAndView form(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/issue-category/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        return modelAndView;
    }

    /**
     * Edicao de item
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = {"/protected/issue-category/edit/{id}", "/protected/issue-category/edit/{id}/find"}, method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id) throws JsonProcessingException {
        // Response
        ResponseJson response = new ResponseJson();
        // Search
        List<IssueCategory> ic = issueCategoryService.findAll();
        if (ic != null && ic.size() > 0) {
            for (IssueCategory ic_item : ic) {
                List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
                List<IssueSubCategory> subcategories = ic_item.getSubcategories();
                if (subcategories != null && subcategories.size() > 0) {
                    for (IssueSubCategory is_item : subcategories) {
                        HashMap<String, Object> value = new HashMap<String, Object>();
                        if (!is_item.isRemoved()) {
                            value.put("id", is_item.getId());
                            value.put("urlAsset", is_item.getUrlAsset());
                            value.put("name", is_item.getName());
                            value.put("sequence", is_item.getSequence());

                            map.add(value);

                            //System.out.println(value.toString());
                        }
                    }
                }
                ic_item.setMap(map);
            }

            response.setStatus(true);
            response.setItem(ic);
        } else {
            response.setItem(new ArrayList<IssueCategory>());
        }
        return response;
    }

    /**
     * Salva/Atualiza um item e seus subitens
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/issue-category/save/", "/protected/issue-category/edit/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson save(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Values
        Map<Integer, JsonObject> categories = r.getAsJsonMap("category");
        // Validate
        try {
            if (categories.size() < 1) {
                response.setMessage(messageSource.getMessage("error.question.required", null, LocaleContextHolder.getLocale()));
            } else {
                // Remove All
                boolean flag = issueCategoryService.removeAll();
                Thread.sleep(1000);
                // Loop
                int index = 0;
                for (Integer key : categories.keySet()) {
                    JsonObject value = categories.get(key);
                    if (value.has("subcategories") && value.has("id") && value.has("name")) {
                        // Category
                        Long categoryId = value.get("id").isJsonNull() ? 0 : value.get("id").getAsLong();
                        String categoryName = value.get("name").isJsonNull() ? "N/A" : value.get("name").getAsString();
                        String categoryUrlAsset = value.get("urlAsset").isJsonNull() ? null : value.get("urlAsset").getAsString();
                        String categoryUrlIcon = value.get("urlIcon").isJsonNull() ? null : value.get("urlIcon").getAsString();
                        String color = value.get("color").isJsonNull() ? "#000000" : value.get("color").getAsString();

                        IssueCategory ic = null;
                        // Category
                        if (categoryId != null && categoryId > 0) {
                            ic = issueCategoryService.findById(categoryId);
                            if (ic != null) {
                                ic.setName(categoryName);
                                ic.setUrlAsset(categoryUrlAsset);
                                ic.setUrlIcon(categoryUrlIcon);
                                ic.setSequence(index);
                                ic.setRemoved(false);
                                ic.setColor(color);
                            }
                        } else {
                            IssueCategoryBuilder icb = new IssueCategoryBuilder();
                            icb.setId(null);
                            icb.setName(categoryName);
                            icb.setUrlAsset(categoryUrlAsset);
                            icb.setSequence(index);
                            icb.setRemoved(false);
                            ic = icb.build(true);
                        }
                        // Increment
                        index++;
                        // Save
                        if (ic != null) {
                            IssueCategory changed = issueCategoryService.saveOrUpdate(ic);
                            // Sub
                            // SubCategoris
                            // List<IssueSubCategory> subcategories = new ArrayList<IssueSubCategory>();
                            if (value.has("subcategories")) {
                                JsonArray subs = value.get("subcategories").getAsJsonArray();
                                int sequence = 0;
                                for (JsonElement elm : subs) {
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
                                            issueSubCategoryService.saveOrUpdate(s);
                                            // subcategories.add(s);
                                        }
                                    }
                                }
                            }
                            response.setStatus(true);
                            response.setMessage(messageSource.getMessage("updated.title", null, LocaleContextHolder.getLocale()));
                        }
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
     * Salva/Atualiza um item
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/issue-category/submit/", "/protected/issue-category/edit/submit/"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson submit(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Category
        Long id = r.getAsLong("id");
        int sequence = r.getAsInt("sequence");
        String name = r.getAsString("name");
        String urlAsset = r.getAsString("urlAsset");
        String urlAssetLight = r.getAsString("urlAssetLight");
        String urlIcon = r.getAsString("urlIcon");
        String color = r.getAsString("color");
        String type = r.getAsString("type");
        //Sub Category
        int level = r.getAsInt("level");
        long parentId = r.getAsLong("parentId");
        long categoryId = r.getAsLong("categoryId");
        try {
            //Validate
            if (Validator.isEmptyString(name)) {
                response.setMessage(messageSource.getMessage("error.name.required", null, LocaleContextHolder.getLocale()));
            } else if (Validator.isEmptyString(urlAsset)) {
                response.setMessage(messageSource.getMessage("error.url.asset.required", null, LocaleContextHolder.getLocale()));
            } else if (Validator.isEmptyString(urlIcon) && level == 0) {
                response.setMessage(messageSource.getMessage("error.url.icon.required", null, LocaleContextHolder.getLocale()));
            } else if (Validator.isEmptyString(urlAssetLight)) {
                response.setMessage(messageSource.getMessage("error.url.light.required", null, LocaleContextHolder.getLocale()));
            } else {
                Long rs = null;
                IssueCategory ic = null;
                if (type.equalsIgnoreCase("C")) {
                    // Category
                    if (id != null && id > 0) {
                        ic = issueCategoryService.findById(id);
                        if (ic != null) {
                            ic.setName(name);
                            ic.setUrlAsset(urlAsset);
                            ic.setUrlAssetLight(urlAssetLight);
                            ic.setUrlIcon(urlIcon);
                            ic.setSequence(sequence);
                            ic.setRemoved(false);
                            ic.setColor(color);
                        }
                    } else {
                        IssueCategoryBuilder icb = new IssueCategoryBuilder();
                        icb.setId(null);
                        icb.setName(name);
                        icb.setUrlAsset(urlAsset);
                        icb.setUrlAssetLight(urlAssetLight);
                        icb.setUrlIcon(urlIcon);
                        icb.setSequence(sequence);
                        icb.setRemoved(false);
                        icb.setColor(color);
                        icb.setEnabled(true);
                        ic = icb.build(true);
                    }
                    // Save
                    if (ic != null) {
                        IssueCategory obj = issueCategoryService.saveOrUpdate(ic);
                        rs = obj != null ? obj.getId() : null;
                    }
                } else if (type.equalsIgnoreCase("S")) {
                    ic = issueCategoryService.findById(categoryId);
                    if (ic != null) {
                        IssueSubCategory is = null;
                        if (id != null && id > 0) {
                            is = issueSubCategoryService.findById(id);
                        }
                        if (is == null) {
                            is = new IssueSubCategory();
                            is.setId(null);
                        }
                        is.setName(name);
                        is.setUrlAsset(urlAsset);
                        is.setUrlAssetLight(urlAssetLight);
                        is.setSequence(sequence);
                        is.setRemoved(false);
                        //Level
                        is.setCategory(ic);
                        is.setParentId(parentId);
                        is.setLevel(level);
                        is.setEnabled(true);
                        // Save
                        if (is != null) {
                            IssueSubCategory obj = issueSubCategoryService.saveOrUpdate(is);
                            rs = obj != null ? obj.getId() : null;
                        }
                    }
                }
                //Result
                if (rs != null) {
                    response.setOutcome(rs);
                    response.setStatus(true);
                    response.setMessage(messageSource.getMessage("updated.title", null, LocaleContextHolder.getLocale()));
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
     * Altera a ordem/sequencia dos itens
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/protected/issue-category/reorder/", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson reorder(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Category
        String type = r.getAsString("type");
        JsonArray orderly = r.getAsJsonArray("orderly");
        // Validate
        try {
            // Loop
            int index = 0;
            int count = 0;
            for (JsonElement elm : orderly) {
                long id = elm.getAsLong();
                if (type.equalsIgnoreCase("C")) {
                    issueCategoryService.reorder(id, index);
                    count++;
                }else if(type.equalsIgnoreCase("S")){
                    issueSubCategoryService.reorder(id, index);
                    count++;
                }
                index++;
            }
            response.setStatus(count > 0);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setMessage(e.getMessage());
        }
        // Result
        return response;
    }

    /**
     * Removed um item
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/protected/issue-category/removed/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/protected/issue-category/removed/", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson removed(@RequestBody String json) throws JsonProcessingException {
        // Request
        ReceiveJson r = new ReceiveJson(json);
        long id = r.getAsLong("id");
        String type = r.getAsString("type");
        // Removed
        boolean removed = false;
        if (type.equalsIgnoreCase("C")) {
            removed = issueCategoryService.removed(id);
        } else if (type.equalsIgnoreCase("S")) {
            removed = issueSubCategoryService.removed(id);
        }
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(removed);
        response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, LocaleContextHolder.getLocale())
                : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
        return response;
    }

    /**
     * Busca por categorias
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/issue-category/search/", "/protected/issue-category/search"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setCount(-1);
        response.setOffset(0);
        response.setItems(new ArrayList<Object[]>());
        try {
            //Search
            List<IssueCategory> items = issueCategoryService.findAll();
            if (items.size() > 0) {
                response.setItem(items);
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
    @RequestMapping(value = {"/protected/issue-subcategory/search/", "/protected/issue-subcategory/search"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RESEARCHER_OMBUDSMAN')")
    public @ResponseBody
    ResponseJson subCategories(@RequestBody String json) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setCount(-1);
        response.setOffset(0);
        response.setItems(new ArrayList<Object[]>());
        // Request
        ReceiveJson r = new ReceiveJson(json);
        long categoryId = r.getAsLong("categoryId");
        long parentId = r.getAsLong("parentId");
        int level = r.getAsInt("level");
        // Params / MultiMap
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        params.put("categoryId", categoryId);
        params.put("parentId", parentId);
        params.put("level", level);
        try {
            //Search
            List<IssueSubCategory> items = issueSubCategoryService.filter(params, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
            if (items.size() > 0) {
                response.setItem(items);
            }else{
                response.setMessage(messageSource.getMessage("nodata.title", null, LocaleContextHolder.getLocale()));
                response.setResultType(ResultType.TYPE_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            response.setMessage(e.getMessage());
        }
        // return
        return response;
    }
}