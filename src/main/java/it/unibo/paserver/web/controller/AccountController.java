package it.unibo.paserver.web.controller;

import br.com.bergmannsoft.utils.PaginationUtil;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.AccountBuilder;
import it.unibo.paserver.service.AccountService;
import it.unibo.paserver.service.InstitutionsService;
import it.unibo.paserver.web.security.v1.AccountAdminDetails;
import it.unibo.paserver.web.validator.AddAccountFormValidator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;
    @Autowired
    private InstitutionsService institutionsService;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("addAccountForm")
    public AddAccountForm getAddAccountForm() {
        return new AddAccountForm();
    }

    @InitBinder("addAccountForm")
    public void initBinder(WebDataBinder binder) {
        binder.setRequiredFields("username");
        binder.setValidator(new AddAccountFormValidator());
    }

    @RequestMapping(value = "/protected/account", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView account(ModelAndView modelAndView) {
        modelAndView.setViewName("protected/account");
        modelAndView.addObject("totalAccounts", accountService.getAccountsCount());
        List<Account> accounts = accountService.getAccounts();
        Collections.sort(accounts, new Comparator<Account>() {

            @Override
            public int compare(Account o1, Account o2) {
                return o1.getUsername().compareTo(o2.getUsername());
            }

        });
        modelAndView.addObject("accounts", accounts);
        return modelAndView;
    }

    @RequestMapping(value = "/protected/account/add", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView accountForm(ModelAndView modelAndView) {
        modelAndView.setViewName("protected/account/add");
        return modelAndView;
    }

    @RequestMapping(value = "/protected/account/addAccount", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView accountAdd(@ModelAttribute @Validated AddAccountForm addAccountForm,
                                   BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("protected/account/add");
            return modelAndView;
        }

        Account account = accountService.getAccount(addAccountForm.getUsername());
        if (account != null) {
            bindingResult.rejectValue("username", "alreadyexists", new String[]{addAccountForm.getUsername()},
                    "alreadyexists");
            modelAndView.setViewName("protected/account/add");
        } else {
            AccountBuilder ab = new AccountBuilder().credentials(addAccountForm.getUsername(),
                    addAccountForm.getPassword());
            if (Boolean.TRUE.equals(addAccountForm.getRoleAdmin())) {
                ab.addRole(Role.ROLE_ADMIN);
            }
            if (Boolean.TRUE.equals(addAccountForm.getRoleView())) {
                ab.addRole(Role.ROLE_VIEW);
            }
            ab.creationDate(new DateTime());
            Account newAccount = ab.build(true);
            logger.info("Saving new account: {}", newAccount.toString());
            accountService.save(newAccount);
            redirectAttributes.addFlashAttribute("successmessage",
                    String.format("Account \"%s\" successfully created", newAccount.getUsername()));
            modelAndView.setViewName("redirect:/protected/account");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/validUsername/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    Map<String, Object> validUsername(@PathVariable String username) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (username == null || username.length() == 0 || !username.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*$")) {
            result.put("valid", false);
            result.put("cause", "Invalid username");
            return result;
        }
        Account existingAccount = accountService.getAccount(username);
        if (existingAccount == null) {
            result.put("valid", true);
        } else {
            result.put("valid", false);
            result.put("cause", "Already registered");
        }
        return result;
    }

    @RequestMapping(value = "/protected/account/delete", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView accountForm(@RequestParam Integer id, ModelAndView modelAndView,
                                    RedirectAttributes redirectAttributes) {
        logger.trace("Received request to delete account {}", id);
        // if (accountService.deleteAccount(id)) {
        // redirectAttributes.addFlashAttribute("successmessage",
        // String.format("Account #\"%d\" successfully deleted", id));
        // } else {
        // redirectAttributes.addFlashAttribute("errormessage",
        // String.format("Unabe to delete account #\"%d\", please consult logs
        // for further information", id));
        // }
        modelAndView.setViewName("redirect:/protected/account");
        return modelAndView;
    }

    /**
     * Pagina inicial
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/account/index", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/account/index");
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
    @RequestMapping(value = "/protected/account/form", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView form(ModelAndView modelAndView) {
        modelAndView.setViewName("/protected/account/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("institutions", institutionsService.findAll());
        return modelAndView;
    }

    /**
     * Edicao de usuario
     *
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "/protected/account/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView edit(@PathVariable("id") long id, ModelAndView modelAndView) {
        // Model view
        modelAndView.setViewName("/protected/account/form");
        modelAndView.addObject("breadcrumb", modelAndView.getViewName());
        modelAndView.addObject("controller", this.getClass().getSimpleName());
        modelAndView.addObject("institutions", institutionsService.findAll());
        modelAndView.addObject("form", id);
        return modelAndView;
    }

    @RequestMapping(value = {"/protected/account/name/{id}/find"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    ResponseJson getName(@PathVariable("id") long id) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        // Busca
        Account account = accountService.findById(id);
        response.setMessage(account != null ? account.getName() : "");
        // result
        return response;
    }

    /**
     * Edicao de usuario
     *
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = {"/protected/account/edit/{id}/find",
            "/protected/researcher/edit/{id}/find"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
    public @ResponseBody
    ResponseJson find(@PathVariable("id") long id, HttpServletRequest request)
            throws JsonProcessingException {
        // isAdmin
        boolean isAdmin = request.isUserInRole("ROLE_ADMIN");
        long parentId = 0L;
        if (!isAdmin) {
            AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            parentId = current.getId();
        }
        // Response
        ResponseJson response = new ResponseJson();
        // Search
        Account u = accountService.findByIdAndParentId(id, parentId);
        if (u != null) {
            response.setStatus(true);
            response.setItem(u);
        }
        return response;
    }

    /**
     * Removed um item
     *
     * @param id
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = {"/protected/account/removed/{id}",
            "/protected/researcher/removed/{id}"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
    public @ResponseBody
    ResponseJson removed(@PathVariable("id") long id, HttpServletRequest request)
            throws JsonProcessingException {
        // isAdmin
        boolean isAdmin = request.isUserInRole("ROLE_ADMIN");
        long parentId = 0L;
        if (!isAdmin) {
            AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            parentId = current.getId();
        }
        // Removed
        boolean removed = accountService.deleteAccount(id, parentId);
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(removed);
        response.setMessage((removed) ? messageSource.getMessage("confirmation.remove.success", null, null)
                : messageSource.getMessage("confirmation.remove.fail", null, LocaleContextHolder.getLocale()));
        return response;
    }

    /**
     * Salva/Atualiza um item
     *
     * @param json
     * @return
     */
    @RequestMapping(value = {"/protected/account/save/", "/protected/account/edit/save/",
            "/protected/researcher/save/", "/protected/researcher/edit/save/"}, method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
    public @ResponseBody
    ResponseJson save(@RequestBody String json, HttpServletRequest request) {
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        // Request
        ReceiveJson r = new ReceiveJson(json);
        // Values
        String id = (r.getAsString("id") == null) ? "0" : r.getAsString("id");
        String name = r.getAsString("name");
        String email = r.getAsString("email");
        String phone = Useful.removeAllNonNumeric(r.getAsString("phone"));
        long instId = Useful.convertStringToLong(r.getAsString("institutionId"));
        // Credencias
        String username = Useful.toLowerCase(r.getAsString("username"));
        String password = r.getAsString("npassword");
        String rpassword = r.getAsString("rpassword");
        String opassword = r.getAsString("password");
        String municipality = r.getAsString("municipality");
        int privilege = r.getAsInt("privilege");
        int privilege2 = r.getAsInt("privilege2");
        // Validate
        try {
            if (!Validator.isValidNumeric(id)) {
                throw new Exception(messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidStringLength(name, 1, 100)) {
                throw new Exception(messageSource.getMessage("protected.account.name", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isEmptyString(email) && !Validator.isValidEmail(email)) {
                throw new Exception(messageSource.getMessage("protected.account.email", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("label.login.invalid.email", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidUsername(username)) {
                throw new Exception(messageSource.getMessage("protected.account.username", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("choose.subtitle", null, LocaleContextHolder.getLocale()));
                // Password
            } else if (Validator.isStringEquals(id, "0") && !Validator.isValidPassword(password)
                    || !Validator.isEmptyString(password) && !Validator.isValidPassword(password)) {
                throw new Exception(messageSource.getMessage("label.password.insecure", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isEmptyString(password) && !Validator.isStringEquals(password, rpassword)) {
                throw new Exception(messageSource.getMessage("error.password.mistach", null, LocaleContextHolder.getLocale()));
            } else {
                // id
                long uuid = Long.parseLong(id);
                // Check
                Account account = accountService.getAccount(username);
                if (account != null && account.getId() != uuid) {
                    throw new Exception(messageSource.getMessage("alreadyexists.addAccountForm.username", new Object[]{username}, LocaleContextHolder.getLocale()));
                } else {
                    // Roles
                    // isAdmin
                    boolean isAdmin = request.isUserInRole("ROLE_ADMIN");
                    // Usuario loggado
                    AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    // Referer
                    boolean isResearcher = Validator.isStringContains(request.getHeader("referer"), "/protected/researcher/");
                    // New or Update
                    AccountBuilder ab = new AccountBuilder();
                    if (uuid > 0) {// Edicao
                        // PARENT ID
                        Account isParent = accountService.findById(uuid);
                        if (!isAdmin) {
                            if (isParent.getParentId() != current.getId()) {
                                return response;
                            } else {
                                ab.setParentId(isParent.getParentId());
                            }
                        } else {
                            ab.setParentId(isParent.getParentId());
                        }
                        // UUID
                        ab.addUuid(uuid);
                        ab.addUsername(username);
                        if (account == null) {
                            ab.addPassword(opassword);
                        } else if (!Validator.isEmptyString(password)) {
                            ab.credentials(username, password);
                        } else {
                            ab.addPassword(account.getPassword());
                        }
                    } else {// Novo
                        ab.credentials(username, password);
                        ab.setParentId(current.getId());
                    }
                    // Privilegios
                    // Padrao
                    ab.addRole(Role.ROLE_RESEARCHER_SECOND).addRole(Role.ROLE_VIEW);
                    ab.setAdmin(false);
                    ab.setPrivilege(Role.ROLE_RESEARCHER_SECOND.ordinal());
                    // Roles
                    if (isAdmin) {
                        ab = this.setPrivilege(isResearcher, privilege, ab, true);
                        if (privilege2 > 2) {
                            ab = this.setPrivilege(isResearcher, privilege2, ab, false);
                        }
                    }
                    // Instituicoes
                    Institutions iu = institutionsService.findById(instId);
                    ab.setInstitutions(iu);
                    // Variaveis simples
                    ab.addEmail(email);
                    ab.addName(name);
                    ab.addPhone(phone);
                    ab.addMunicipality(municipality);
                    ab.creationDate(new DateTime());
                    // Finalizando e salvando
                    Account u = ab.build(true);
                    Account rs = accountService.saveOrUpdate(u);
                    if (rs != null) {
                        response.setStatus(true);
                        response.setOutcome(rs.getId());
                    }
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
            response.setMessage(e.getMessage());
        }
        // Result
        return response;
    }

    /**
     * @param json
     * @param count
     * @param offset
     * @param request
     * @return
     */
    @RequestMapping(value = {"/protected/account/search/{count}/{offset}",
            "/protected/researcher/search/{count}/{offset}"}, method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_RESEARCHER_FIRST')")
    public @ResponseBody
    ResponseJson search(@RequestBody String json, @PathVariable int count,
                        @PathVariable int offset, HttpServletRequest request) {
        // isAdmin
        boolean isAdmin = request.isUserInRole("ROLE_ADMIN");
        // Referer
        boolean isResearcher = Validator.isStringContains(request.getHeader("referer"), "/protected/account/");
        long parentId = 0L;
        if (!isAdmin) {
            AccountAdminDetails current = (AccountAdminDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            parentId = current.getId();
            isResearcher = false;
        }
        // Response
        ResponseJson response = new ResponseJson();
        response.setStatus(true);
        response.setCount(count);
        response.setOffset(offset);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        List<Object[]> items = accountService.search(isResearcher, parentId, r.getAsString("name"),
                r.getAsString("username"), r.getAsString("email"), r.getAsString("phone"), r.getAsString("last"),
                PaginationUtil.pagerequest(offset, count));
        response.setItems(items);
        if (items.size() > 0) {
            response.setTotal(accountService.searchTotal(isResearcher, parentId, r.getAsString("name"),
                    r.getAsString("username"), r.getAsString("email"), r.getAsString("phone"), r.getAsString("last")));
        }
        return response;
    }


    /**
     * Seta os privilegios para uma conta
     *
     * @param isResearcher
     * @param privilege
     * @param ab
     * @return
     */
    private AccountBuilder setPrivilege(boolean isResearcher, int privilege, AccountBuilder ab, boolean isPrimary) {
        if (!isResearcher) {
            ab.addRole(Role.ROLE_ADMIN).addRole(Role.ROLE_VIEW);
            ab.setAdmin(true);
            ab.setPrivilege(Role.ROLE_ADMIN.ordinal(), isPrimary);

        } else if (privilege == Role.ROLE_RESEARCHER_FIRST.ordinal()) {
            ab.addRole(Role.ROLE_RESEARCHER_FIRST).addRole(Role.ROLE_VIEW);
            ab.setPrivilege(Role.ROLE_RESEARCHER_FIRST.ordinal(), isPrimary);

        } else if (privilege == Role.ROLE_COOPERATION_AGREEMENT.ordinal()) {
            ab.addRole(Role.ROLE_COOPERATION_AGREEMENT).addRole(Role.ROLE_VIEW);
            ab.setPrivilege(Role.ROLE_COOPERATION_AGREEMENT.ordinal(), isPrimary);

        } else if (privilege == Role.ROLE_RESEARCHER_OMBUDSMAN.ordinal()) {
            ab.addRole(Role.ROLE_RESEARCHER_OMBUDSMAN).addRole(Role.ROLE_VIEW);
            ab.setPrivilege(Role.ROLE_RESEARCHER_OMBUDSMAN.ordinal(), isPrimary);

        } else if (privilege == Role.ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT.ordinal()) {
            ab.addRole(Role.ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT).addRole(Role.ROLE_VIEW);
            ab.setPrivilege(Role.ROLE_RESEARCHER_OMBUDSMAN_CONSULTANT.ordinal(), isPrimary);

        } else if (privilege == Role.ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR.ordinal()) {
            ab.addRole(Role.ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR).addRole(Role.ROLE_VIEW);
            ab.setPrivilege(Role.ROLE_RESEARCHER_OMBUDSMAN_COLLABORATOR.ordinal(), isPrimary);

        } else if (privilege == Role.ROLE_RESEARCHER_OMBUDSMAN_EDITOR.ordinal()) {
            ab.addRole(Role.ROLE_RESEARCHER_OMBUDSMAN_EDITOR).addRole(Role.ROLE_VIEW);
            ab.setPrivilege(Role.ROLE_RESEARCHER_OMBUDSMAN_EDITOR.ordinal(), isPrimary);
        }

        return ab;
    }
}