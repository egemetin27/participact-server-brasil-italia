package it.unibo.paserver.rest.controller.v2;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.rest.UserMessageRestResult;
import it.unibo.paserver.domain.rest.UserRestResult;
import it.unibo.paserver.service.*;
import it.unibo.paserver.web.controller.MailerController;
import it.unibo.paserver.web.security.v2.AuthenticatorDetails;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Usuario
 *
 * @author Claudio
 */
@RestController
public class UserRestfulController extends ApplicationRestfulController {
    @Autowired
    private RecoverPasswordService recoverPasswordService;
    @Autowired
    private InstitutionsService institutionsService;
    @Autowired
    private SchoolCourseService schoolCourseService;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ParticipantListService participantListService;
    // ####################### ####################### #######################
    // ####################### Registro
    // ####################### ####################### #######################

    /**
     * SIGNUP
     *
     * @param json
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/v2/protected/account/signup"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest signup(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            // CATALINA.OUT JSON
            System.out.println(json);

            String username = r.getAsString("username");
            String password = r.getAsString("password");

            String name = r.getAsString("name");
            String notes = r.getAsString("notes");
            long institutionId = r.getAsLong("institutionId");
            long schoolCourseId = r.getAsLong("schoolCourseId");
            String occupation = r.getAsString("occupation");
            String homePhoneNumber = Useful.removeAllNonNumeric(r.getAsString("homePhoneNumber"));
            String gender = r.getAsString("gender");
            String ageRange = r.getAsString("ageRange");

            String facebookUrl = r.getAsString("facebookUrl");
            String googleUrl = r.getAsString("googleUrl");
            String twitterUrl = r.getAsString("twitterUrl");
            String instagramUrl = r.getAsString("instagramUrl");
            String youtubeUrl = r.getAsString("youtubeUrl");
            // Validate
            if (!Validator.isValidEmail(username)) {
                throw new Exception(messageSource.getMessage("protected.participant.email", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("label.login.invalid.email", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidPassword(password)) {
                throw new Exception(messageSource.getMessage("label.password.insecure", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidStringLength(name, 1, 100)) {
                throw new Exception(messageSource.getMessage("protected.participant.name", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.exceeded", null, LocaleContextHolder.getLocale()));
            } else {
                if (authenticatorService.doChooseUsername(username) == null) {
                    Gender enumGender = EnumUtils.isValidEnum(Gender.class, gender) ? Gender.valueOf(gender) : Gender.NONE;
                    Institutions iu = institutionsService.findById(institutionId);
                    SchoolCourse su = schoolCourseService.findById(schoolCourseId);
                    // Save
                    AuthenticatorDetails a = authenticatorService.doSignUpAccount(username, password, name, notes, occupation, homePhoneNumber, ageRange, facebookUrl, googleUrl, twitterUrl, instagramUrl, youtubeUrl, enumGender, iu, su);
                    if (a != null) {
                        // Details
                        return login(json, request, session);
                    }
                } else {
                    throw new Exception(messageSource.getMessage("alreadyexists.addAccountForm.username", new Object[]{username}, LocaleContextHolder.getLocale()));
                }
            }
        } catch (UsernameNotFoundException e) {
            response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * LOGIN WITH FACEBOOK
     *
     * @param json
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/v2/protected/account/facebook"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest signInWithFacebook(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            // CATALINA.OUT JSON
            System.out.println(json);

            String fbToken = r.getAsString("fbToken");
            boolean isPrimary = r.getAsBoolean("isPrimary");
            // Validate
            if (Validator.isEmptyString(fbToken)) {
                throw new Exception(messageSource.getMessage("error.login.fbtoken", null, LocaleContextHolder.getLocale()));
            } else {
                try {
                    // Facebook
                    Facebook facebook = new FacebookTemplate(fbToken);
                    String me = facebook.fetchObject("me", String.class, Config.FACEBOOK_CLIENT_FIELDS);
                    // Facebook Profile
                    System.out.println(me);
                    ReceiveJson fbUser = new ReceiveJson(me);
                    Long fbId = fbUser.getAsLong("id");
                    String fbName = fbUser.getAsString("name");
                    String fbEmail = fbUser.getAsString("email");
                    if (!Validator.isValidEmail(fbEmail)) {
                        fbEmail = fbId + "@facebook.com";
                    }
                    String fbUrl = fbUser.getAsString("link");
                    String fbGender = fbUser.getAsString("gender");
                    String ageRange = "";

                    try {
                        JsonObject jsonAgeRange = fbUser.getAsJsonObject("age_range");
                        if (jsonAgeRange != null) {
                            ReceiveJson fbAgeRange = new ReceiveJson(fbUser.getAsJsonObject("age_range").toString());

                            if (fbAgeRange.has("min") && fbAgeRange.has("max")) {
                                ageRange = fbAgeRange.getAsString("min") + " - " + fbAgeRange.getAsString("max");
                            } else if (fbAgeRange.has("min")) {
                                ageRange = fbAgeRange.getAsString("min");
                            } else if (fbAgeRange.has("max")) {
                                ageRange = fbAgeRange.getAsString("max");
                            }
                        }
                    } catch (Exception ej) {
                        ej.printStackTrace(System.out);
                    }
                    // Json
                    JsonObject newObj = new JsonObject();
                    newObj.addProperty("name", fbName);
                    newObj.addProperty("username", fbEmail);
                    newObj.addProperty("password", fbId.toString());
                    newObj.addProperty("facebookUrl", fbUrl);
                    newObj.addProperty("gender", fbGender);
                    newObj.addProperty("ageRange", ageRange);
                    // Check
                    User u = authenticatorService.doChooseUsername(fbEmail);
                    if (u == null) {
                        System.out.println(newObj);
                        return signup(newObj.toString(), request, session);
                    } else {
                        u.setCredentials(fbEmail, fbId.toString());
                        u.setRemoved(false);
                        u.setIsActive(true);
                        authenticatorService.doUpdateAccount(u);
                        //Is Primary
                        if(isPrimary){
                            response = directLogin(fbEmail, fbId.toString(), request, session);
                        }
                    }
                    Useful.uSleep(500);
                    return login(newObj.toString(), request, session);
                } catch (Exception e) {
                    response.setMessage(e.getMessage());
                    e.printStackTrace(System.out);
                }
            }

        } catch (UsernameNotFoundException e) {
            response.setItem(null);
            response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            response.setItem(null);
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * LOGIN WITH GOOGLE
     *
     * @param json
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/v2/protected/account/google"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest signInWithGoogle(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            // CATALINA.OUT JSON
            System.out.println(json);

            String gToken = r.getAsString("gToken");
            boolean isPrimary = r.getAsBoolean("isPrimary");
            // Validate
            if (Validator.isEmptyString(gToken)) {
                throw new Exception(messageSource.getMessage("error.login.gtoken", null, LocaleContextHolder.getLocale()));
            } else {
                try {
                    // Google
                    Google google = new GoogleTemplate(gToken);
                    Person gUser = google.plusOperations().getGoogleProfile();
                    // Profile
                    String gId = gUser.getId();
                    String gName = gUser.getDisplayName();
                    String gEmail = gUser.getAccountEmail();
                    String gUrl = gUser.getUrl();
                    String gGender = gUser.getGender();
                    // Json
                    JsonObject newObj = new JsonObject();
                    newObj.addProperty("name", gName);
                    newObj.addProperty("username", gEmail);
                    newObj.addProperty("password", gId);
                    newObj.addProperty("googleUrl", gUrl);
                    newObj.addProperty("gender", gGender);

                    System.out.println(newObj.toString());
                    // Check
                    User u = authenticatorService.doChooseUsername(gEmail);
                    if (u == null) {
                        System.out.println(newObj);
                        return signup(newObj.toString(), request, session);
                    } else {
                        u.setCredentials(gEmail, gId);
                        u.setRemoved(false);
                        u.setIsActive(true);
                        authenticatorService.doUpdateAccount(u);
                        //Is Primary
                        if(isPrimary){
                            response = directLogin(gEmail, gId, request, session);
                        }
                    }
                    Useful.uSleep(500);
                    return login(newObj.toString(), request, session);
                } catch (Exception e) {
                    response.setItem(null);
                    response.setMessage(e.getMessage());
                    e.printStackTrace(System.out);
                }
            }

        } catch (UsernameNotFoundException e) {
            response.setItem(null);
            response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            response.setItem(null);
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    // ####################### ####################### #######################
    // ####################### Autenticacao
    // ####################### ####################### #######################

    /**
     * LOGIN
     *
     * @param json
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/v2/protected/login"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest login(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Clear Session
        // authenticatorService.doLogout(session, request);
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.unknown", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            // CATALINA.OUT JSON
            System.out.println(json);

            String username = r.getAsString("username");
            String password = r.getAsString("password");
            boolean isPrimary = r.getAsBoolean("isPrimary");
            // Validate
            if (!Validator.isValidEmail(username)) {
                throw new Exception(messageSource.getMessage("protected.participant.email", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("label.login.invalid.email", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidPassword(password)) {
                throw new Exception(messageSource.getMessage("label.password.insecure", null, LocaleContextHolder.getLocale()));
            } else {
                isUserDetails(request);
                if(isPrimary) {
                    response = directLogin(username, password, request, session);
                }else if (authenticatorService.doProgenitor(username, password, getUserId())) {
                    // User
                    AuthenticatorDetails a = new AuthenticatorDetails();
                    a.setToken(getHeaderAuthorization(request));
                    a.setId(getUserId());
                    a.setUuid(getUuid());
                    a.setInAppleReview(authenticatorService.doAppleReview());
                    // Response
                    response.setItem(a);
                    response.setStatus(true);
                    response.setMessage(null);
                } else {
                    response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
                }
            }
        } catch (UsernameNotFoundException e) {
            response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            // e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Login direto
     * @param username
     * @param password
     * @param request
     * @param session
     * @return
     * @throws AuthenticationException
     */
    private ResponseJsonRest directLogin(String username, String password, HttpServletRequest request, HttpSession session) throws AuthenticationException {
        AuthenticatorDetails a = authenticatorService.doLogin(username, password);
        if (a != null) {
            authenticatorService.doSession(session, request, a);
            response.setItem(a);
            response.setStatus(true);
            response.setMessage(null);
        } else {
            response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        }
        return response;
    }
    /**
     * RESET SENHA
     */
    @RequestMapping(value = {"/api/v2/protected/forgot"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest forgot(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Clear Session
        // authenticatorService.doLogout(session, request);
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.qrcode.used", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            // CATALINA.OUT JSON
            System.out.println(json);

            String username = r.getAsString("username");
            // Validate
            if (!Validator.isValidEmail(username)) {
                throw new Exception(messageSource.getMessage("protected.participant.email", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("label.login.invalid.email", null, LocaleContextHolder.getLocale()));
            } else {
                String uuid = UUID.randomUUID().toString();
                User u = authenticatorService.doChooseUsername(username);
                if (u != null) {
                    // Reset
                    RecoverPassword recoverPassword = new RecoverPassword();
                    DateTime now = new DateTime();
                    DateTime end = now.plusHours(1);
                    recoverPassword.setStartValidity(now);
                    recoverPassword.setEndValidity(end);
                    recoverPassword.setUser(u);
                    recoverPassword.setToken(uuid);
                    recoverPassword.setUser(u);
                    recoverPassword = recoverPasswordService.save(recoverPassword);
                    // Mail Send
                    MailerController mailer = new MailerController();
                    String subject = messageSource.getMessage("email.forgot.subject", null, LocaleContextHolder.getLocale());
                    String from = Config.emailFrom;
                    String to = u.getOfficialEmail();
                    String url = Config.PRODUCTION_HOST.contains("localhost") ? String.format(Config.PRODUCTION_HOST + "resetpassword?token=%s", uuid)
                            : String.format(Config.PRODUCTION_URL + Config.PRODUCTION_HOST + "resetpassword?token=%s", uuid);
                    Object[] args = new Object[]{u.getName(), url, request.getRemoteAddr()};
                    String body = messageSource.getMessage("email.forgot.body", args, LocaleContextHolder.getLocale());
                    mailer.senderHtml(subject, from, to, body, body);
                    // Response
                    response.setStatus(true);
                    response.setMessage(Useful.replaceNull(messageSource.getMessage("label.reset.send_email", null, LocaleContextHolder.getLocale())));
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
     * CONFIRMAR TOKEN
     */
    @RequestMapping(value = {"/api/v2/protected/qrcode"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest qrcode(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Clear Session
        // authenticatorService.doLogout(session, request);
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.qrcode.used", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            // CATALINA.OUT JSON
            System.out.println(json);

            String token = r.getAsString("token");
            // Validate
            if (!Validator.isValidPassword(token)) {
                throw new Exception(messageSource.getMessage("label.password.insecure", null, LocaleContextHolder.getLocale()));
            } else {
                isUserDetails(request);
                AuthenticatorDetails a = authenticatorService.doQrCode(token);
                if (a != null) {
                    // Reajustando Progenitor
                    authenticatorService.doGather(getUserId(), a.getId());
                    // authenticatorService.doSession(session, request, a);
                    // response.setItem(a);
                    response.setStatus(true);
                    response.setMessage(null);
                } else {
                    Task task = taskService.findByQrCode(token);
                    if (task != null && task.isPublish() && !task.isExpired() && task.isAssign()) {
                        UserListTask userListTask = participantListService.findUserListTask(task.getId());
                        if (userListTask != null) {
                            UserListItem userListItem = participantListService.addUserListItem(userListTask.getUserListId().getId(), getUserId());
                            if (userListItem != null) {
                                response.setStatus(true);
                                response.setMessage("OK");
                            }
                        } else {
                            response.setMessage(messageSource.getMessage("error.list.nofound", null, LocaleContextHolder.getLocale()));
                        }
                    } else {
                        response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
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

    // ####################### ####################### #######################
    // ####################### Profile
    // ####################### ####################### #######################
    @RequestMapping(value = {"/api/v2/protected/account/me"}, method = RequestMethod.GET)
    public @ResponseBody
    ResponseJsonRest me(HttpServletRequest request, HttpSession session) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        try {
            // Details
            isUserDetails(request);
            // IDs
            long guestId = getUserId();
            long progenitorId = getProgenitorId(guestId);
            UserRestResult ur = new UserRestResult();
            if (progenitorId > 0) {
                ur.transpose(getAccount(progenitorId));
            } else {
                ur.transpose(getUser());
            }
            // Response
            response.setStatus(true);
            response.setMessage(null);
            response.setItem(ur);
            response.setOutcome(guestId);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Notificacoes
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/v2/protected/messages/me"}, method = RequestMethod.GET)
    public @ResponseBody
    ResponseJsonRest unreadMessages(HttpServletRequest request, HttpSession session) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        try {
            // Details
            isUserDetails(request);
            // IDs
            long guestId = getUserId();
            long progenitorId = getProgenitorId(guestId);
            long uId = (progenitorId > 0) ? progenitorId : guestId;
            // Messagens
            List<Object[]> items = userMessageService.fetchAllUnread(uId);
            if (items.size() > 0) {
                List<UserMessageRestResult> posts = new ArrayList<UserMessageRestResult>();
                for (Object[] i : items) {
                    Long id = Long.parseLong(i[0].toString());
                    String message = (String) i[1].toString();

                    UserMessageRestResult umrr = new UserMessageRestResult();
                    umrr.setMessage(message);
                    umrr.setRead(false);
                    umrr.setId(id);
                    // Add
                    posts.add(umrr);
                }
                // Status
                response.setItem(posts);
                response.setTotal(posts.size());
                // Update
                userMessageService.readByUserId(uId);
            } else {
                response.setItem(new ArrayList<>());
                response.setTotal(0);
            }
            // Response
            response.setStatus(true);
            response.setMessage(null);
            response.setOutcome(guestId);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }

    /**
     * Atualizando Profile
     *
     * @param json
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/v2/protected/account/submit"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest aboutYou(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        try {
            // CATALINA.OUT JSON
            System.out.println(json);
            // Details
            isUserDetails(request);
            // IDs
            long guestId = getUserId();
            long progenitorId = getProgenitorId(guestId);
            User u = null;// Primario
            User u2 = null;// Secundario
            if (progenitorId > 0) {
                u = getAccount(progenitorId);// Atualiza o Primario
                u2 = getUser();// Atualiza o Secundario
            } else {
                u = getUser();
            }
            if (u != null) {
                // Request
                ReceiveJson r = new ReceiveJson(json);
                String password = r.getAsString("password");
                if (Validator.isValidPassword(password)) {
                    u.setCredentials(u.getOfficialEmail(), password);
                }
                String name = r.getAsString("name");
                if (!Validator.isEmptyString(name)) {
                    u.setName(name);
                }
                String email = r.getAsString("email");
                if (!Validator.isEmptyString(email)) {
                    email = email.replaceAll("\\s", "");
                    System.out.println(email);
                    System.out.println(Validator.isValidEmail(email));
                    if (Validator.isValidEmail(email)) {
                        u.setSecondaryEmail(email);
                    }
                }
                String notes = r.getAsString("notes");
                if (!Validator.isEmptyString(notes)) {
                    u.setNotes(notes);
                }
                String about = r.getAsString("about");
                if (!Validator.isEmptyString(about)) {
                    u.setNotes(about);
                }
                String occupation = r.getAsString("occupation");
                if (!Validator.isEmptyString(occupation)) {
                    u.setOccupation(occupation);
                }
                String homePhoneNumber = Useful.removeAllNonNumeric(r.getAsString("homePhoneNumber"));
                if (!Validator.isEmptyString(homePhoneNumber)) {
                    u.setHomePhoneNumber(homePhoneNumber);
                }
                String phone = Useful.removeAllNonNumeric(r.getAsString("phone"));
                if (!Validator.isEmptyString(phone)) {
                    u.setHomePhoneNumber(phone);
                }
                String ageRange = r.getAsString("ageRange");
                if (!Validator.isEmptyString(ageRange)) {
                    u.setAgeRange(ageRange);
                }
                String facebookUrl = r.getAsString("facebookUrl");
                if (!Validator.isEmptyString(facebookUrl)) {
                    u.setFacebookUrl(facebookUrl);
                }
                String googleUrl = r.getAsString("googleUrl");
                if (!Validator.isEmptyString(googleUrl)) {
                    u.setGoogleUrl(googleUrl);
                }
                String twitterUrl = r.getAsString("twitterUrl");
                if (!Validator.isEmptyString(twitterUrl)) {
                    u.setTwitterUrl(twitterUrl);
                }
                String instagramUrl = r.getAsString("instagramUrl");
                if (!Validator.isEmptyString(instagramUrl)) {
                    u.setInstagramUrl(instagramUrl);
                }
                String youtubeUrl = r.getAsString("youtubeUrl");
                if (!Validator.isEmptyString(youtubeUrl)) {
                    u.setYoutubeUrl(youtubeUrl);
                }
                // Special
                String education = r.getAsString("education");
                String eduration = r.getAsString("eduration");
                if (!Validator.isEmptyString(eduration)) {
                    education = eduration; // Fix bug name app
                }

                if (!Validator.isEmptyString(education)) {
                    education = StringUtils.stripAccents(education);
                    System.out.println(education);
                    UniCourse enumCourse = UniCourse.NONE;
                    if (education.equalsIgnoreCase("Educacao Infantil")) {
                        enumCourse = UniCourse.CHILD_EDUCATION;
                    } else if (education.equalsIgnoreCase("Ensino Fundamental")) {
                        enumCourse = UniCourse.ELEMENTARY_SCHOOL;
                    } else if (education.equalsIgnoreCase("Ensino Medio")) {
                        enumCourse = UniCourse.HIGH_SCHOOL;
                    } else if (education.equalsIgnoreCase("Ensino Tecnico")) {
                        enumCourse = UniCourse.TECHNICAL_EDUCATION;
                    } else if (education.equalsIgnoreCase("Superior") || education.equalsIgnoreCase("Ensino Superior") || education.equalsIgnoreCase("Graduacao")) {
                        enumCourse = UniCourse.GRADUATION;
                    } else if (education.equalsIgnoreCase("Mestrado")) {
                        enumCourse = UniCourse.MASTER_DEGREE;
                    } else if (education.equalsIgnoreCase("Pos Graduacao")) {
                        enumCourse = UniCourse.MASTER_DEGREE;
                    } else if (education.equalsIgnoreCase("Doutorado")) {
                        enumCourse = UniCourse.DOCTORATE_DEGREE;
                    } else if (education.equalsIgnoreCase("Pos Doutorado")) {
                        enumCourse = UniCourse.POSTDOCTORAL;
                    }
                    u.setUniCourse(enumCourse);
                }

                long institutionId = r.getAsLong("institutionId");
                if (institutionId > 0) {
                    Institutions iu = institutionsService.findById(institutionId);
                    u.setInstitutionId(iu);
                }
                long schoolCourseId = r.getAsLong("schoolCourseId");
                if (schoolCourseId > 0) {
                    SchoolCourse su = schoolCourseService.findById(schoolCourseId);
                    u.setSchoolCourseId(su);
                }
                String gender = r.getAsString("gender");
                if (!Validator.isEmptyString(gender)) {
                    gender = gender.toUpperCase();
                    Gender enumGender = EnumUtils.isValidEnum(Gender.class, gender) ? Gender.valueOf(gender) : Gender.NONE;
                    u.setGender(enumGender);
                }
                //Device
                String xPlataform = request.getHeader("x-plataform");
                String xDeviceModel = request.getHeader("x-device-model");
                String xOsVersion = request.getHeader("x-os-version");
                String xManufacturer = request.getHeader("x-manufacturer");
                String xAppVersionName = request.getHeader("x-app-version-name");
                String xAppVersionCode = request.getHeader("x-app-version-code");
                u.setxAppVersionCode(xAppVersionCode);
                u.setxAppVersionName(xAppVersionName);
                u.setxDeviceModel(xDeviceModel);
                u.setxManufacturer(xManufacturer);
                u.setxOsVersion(xOsVersion);
                u.setxPlataform(xPlataform);
                u.setDevice(String.format("%s %s", xManufacturer, xDeviceModel));
                //TNC
                boolean authorizeContact = r.getAsBoolean("authorizeContact");
                u.setAuthorizeContact(authorizeContact);
                // Reativando user
                u.setRemoved(false);
                u.setIsActive(true);
                // Update
                if (authenticatorService.doUpdateAccount(u) != null) {
                    // Contem Secundario?
                    if (u2 != null) {
                        try {
                            u2.setCredentials(u2.getOfficialEmail(), u.getPassword());
                            u2.setName(u.getName());
                            u2.setSecondaryEmail(u.getSecondaryEmail());
                            u2.setNotes(u.getNotes());
                            u2.setNotes(u.getNotes());
                            u2.setOccupation(u.getOccupation());
                            u2.setHomePhoneNumber(u.getHomePhoneNumber());
                            u2.setAgeRange(u.getAgeRange());
                            u2.setFacebookUrl(u.getFacebookEmail());
                            u2.setGoogleUrl(u.getGoogleUrl());
                            u2.setTwitterUrl(u.getTwitterUrl());
                            u2.setInstagramUrl(u.getInstagramUrl());
                            u2.setYoutubeUrl(u.getYoutubeUrl());
                            u2.setUniCourse(u.getUniCourse());
                            u2.setInstitutionId(u.getInstitutionId());
                            u2.setSchoolCourseId(u.getSchoolCourseId());
                            u2.setGender(u.getGender());
                            u2.setAuthorizeContact(authorizeContact);
                            u2.setRemoved(false);
                            u2.setIsActive(true);
                            u2.setxAppVersionCode(u.getxAppVersionCode());
                            u2.setxAppVersionName(u.getxAppVersionName());
                            u2.setxDeviceModel(u.getxDeviceModel());
                            u2.setxManufacturer(u.getxManufacturer());
                            u2.setxOsVersion(u.getxOsVersion());
                            u2.setxPlataform(u.getxPlataform());
                            u2.setDevice(u.getDevice());
                            authenticatorService.doUpdateAccount(u2);
                        } catch (Exception e) {
                            response.setMessage(e.getMessage());
                            e.printStackTrace(System.out);
                        }
                    }
                    // User Result
                    UserRestResult ur = new UserRestResult();
                    ur.transpose(u);
                    // Response
                    response.setStatus(true);
                    response.setMessage(null);
                    response.setItem(ur);
                    response.setOutcome(guestId);
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
     * Chave do Push
     *
     * @param json
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = {"/api/v2/protected/account/regid"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest pushKey(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        try {
            // Details
            isUserDetails(request);
            // IDs
            long guestId = getUserId();
            long progenitorId = getProgenitorId(guestId);
            User u = null;
            User u2 = null;
            if (progenitorId > 0) {
                u = getAccount(progenitorId);
                u2 = getUser();// Atualiza o Secundario
            } else {
                u = getUser();
            }
            if (u != null) {
                // Request
                ReceiveJson r = new ReceiveJson(json);
                String regid = r.getAsString("regid");
                // Validate Key
                if (!Validator.isEmptyString(regid)) {
                    // X Device
                    String xPlataform = request.getHeader("x-plataform");
                    String xDeviceModel = request.getHeader("x-device-model");
                    String xOsVersion = request.getHeader("x-os-version");
                    String xManufacturer = request.getHeader("x-manufacturer");
                    String xAppVersionName = request.getHeader("x-app-version-name");
                    String xAppVersionCode = request.getHeader("x-app-version-code");
                    u.setxAppVersionCode(xAppVersionCode);
                    u.setxAppVersionName(xAppVersionName);
                    u.setxDeviceModel(xDeviceModel);
                    u.setxManufacturer(xManufacturer);
                    u.setxOsVersion(xOsVersion);
                    u.setxPlataform(xPlataform);
                    u.setDevice(String.format("%s %s", xManufacturer, xDeviceModel));
                    // Set key
                    u.setGcmId(regid);
                    // Update
                    if (authenticatorService.doUpdateAccount(u) != null) {
                        if (u2 != null) {
                            try {
                                u2.setRemoved(false);
                                u2.setIsActive(true);
                                u2.setxAppVersionCode(u.getxAppVersionCode());
                                u2.setxAppVersionName(u.getxAppVersionName());
                                u2.setxDeviceModel(u.getxDeviceModel());
                                u2.setxManufacturer(u.getxManufacturer());
                                u2.setxOsVersion(u.getxOsVersion());
                                u2.setxPlataform(u.getxPlataform());
                                u2.setDevice(u.getDevice());
                                u2.setGcmId(u.getGcmId());
                                authenticatorService.doUpdateAccount(u2);
                            } catch (Exception e) {
                                response.setMessage(e.getMessage());
                                e.printStackTrace(System.out);
                            }
                        }
                        // User Result
                        UserRestResult ur = new UserRestResult();
                        ur.transpose(u);
                        // TEST PUSH
                        // Filtro
                        HashMap<String, String> hashmap = new HashMap<String, String>();
                        hashmap.put("key", "FILTER_USERID");
                        hashmap.put("value", u.getId().toString());
                        hashmap.put("item", u.getId().toString());
                        hashmap.put("label", "info");

                        Gson gson = new Gson();
                        String assignFilter = "[" + gson.toJson(hashmap) + "]";
                        // Log
                        /**
                         * 2018-09-27: Claudionor Desabilitando Push inicial
                         */
                        /*
                         * Long pushId = 0L; PushNotificationsBuilder pb = new
                         * PushNotificationsBuilder(); pb.setAll(pushId, 0L,
                         * PANotification.Type.MESSAGE, assignFilter, true,
                         * "Obrigado por utilizar o ParticipAct!"); pb.setPublish(true);
                         * PushNotifications p = pb.build(true); PushNotifications rs =
                         * pushNotificationsService.saveOrUpdate(p); if (rs != null) {
                         * taskPublishService.sendPushies(rs); }
                         */
                        // Response
                        response.setStatus(true);
                        response.setMessage(null);
                        response.setItem(ur);
                        response.setOutcome(guestId);
                    }
                } else {
                    response.setMessage("Push Key is invalid");
                }
            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            e.printStackTrace(System.out);
        }
        // Result
        return response;
    }
}
