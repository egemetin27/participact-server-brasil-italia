package it.unibo.paserver.rest.controller.v2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.bergmannsoft.utils.Useful;
import it.unibo.paserver.domain.User;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.ReceiveJson;
import it.unibo.paserver.domain.ResponseJsonRest;
import it.unibo.paserver.web.security.v2.AuthenticatorDetails;

/**
 * Login do usuario
 *
 * @author Claudio
 */
@RestController
public class GuestRestfulController extends ApplicationRestfulController {
    /**
     * GUEST
     */
    @RequestMapping(value = {"/api/v2/public/guest"}, method = RequestMethod.POST)
    public @ResponseBody
    ResponseJsonRest guest(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        // Clear Session
        authenticatorService.doLogout(session, request);
        // Response
        response.setStatus(false);
        response.setMessage(messageSource.getMessage("error.login.taken", null, LocaleContextHolder.getLocale()));
        response.setItem(null);
        // Request
        ReceiveJson r = new ReceiveJson(json);
        try {
            //CATALINA.OUT JSON
            System.out.println(json.toString());

            String username = r.getAsString("username");
            String password = r.getAsString("password");
            // Validate
            if (Validator.isEmptyString(username)) {
                throw new Exception(messageSource.getMessage("protected.account.username", null, LocaleContextHolder.getLocale()) + '.' + messageSource.getMessage("error.field.empty", null, LocaleContextHolder.getLocale()));
            } else if (!Validator.isValidPassword(password)) {
                throw new Exception(messageSource.getMessage("label.password.insecure", null, LocaleContextHolder.getLocale()));
            } else {
                User u = authenticatorService.doChooseUsername(username);
                if (u == null) {
                    AuthenticatorDetails a = authenticatorService.doGuest(username, password, request);
                    if (a != null) {
                        authenticatorService.doSession(session, request, a);
                        response.setItem(a);
                        response.setStatus(true);
                        response.setMessage(null);
                    } else {
                        System.out.println("UnsupportedOperationException");
                        throw new UnsupportedOperationException();
                    }
                } else {
                    // BEGIN LOGIN
                    try {
                        AuthenticatorDetails a = authenticatorService.doLogin(username, password);
                        if (a != null) {
                            authenticatorService.doSession(session, request, a);
                            response.setItem(a);
                            response.setStatus(true);
                            response.setMessage(null);
                        } else {
                            response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
                        }
                    } catch (UsernameNotFoundException e) {
                        //Fix issue, App change password
                        if (u != null && !Validator.isEmptyString(username) && !Validator.isEmptyString(password) && Useful.containsIgnoreCase(username, password)) {
                            String newpassword = Useful.hashed(Useful.escapeSql(password), username);
                            u.setPassword(newpassword);
                            // Revert senha para default do guest
                            authenticatorService.doUpdateAccount(u);
                            Useful.uSleep(1000);
                            AuthenticatorDetails b = authenticatorService.doLogin(username, password);
                            if (b != null) {
                                authenticatorService.doSession(session, request, b);
                                response.setItem(b);
                                response.setStatus(true);
                                response.setMessage(null);
                            } else {
                                response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
                            }
                        }else{
							response.setMessage(messageSource.getMessage("error.login.failed", null, LocaleContextHolder.getLocale()));
						}
                    }
                    // END LOGIN
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