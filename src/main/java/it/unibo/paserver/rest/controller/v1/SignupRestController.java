package it.unibo.paserver.rest.controller.v1;

import java.util.HashSet;
import java.util.UUID;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.ResponseMessage;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.service.UserService;

@Controller
public class SignupRestController {
	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(SignupRestController.class);

	@RequestMapping(value = "/api/v2/public/signup", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage signup(@RequestBody String data) {
		ResponseMessage response = new ResponseMessage();
		response.setResultCode(200);
		logger.info(data.toString());
		try {
			JSONObject json = new JSONObject(data);
			String name = (json.has("name")) ? Useful.decodeStringToUTF8(json.getString("name")) : "";
			String password = (json.has("password")) ? json.getString("password") : "";
			String provider = (json.has("provider")) ? json.getString("provider") : "NONE";
			String email = (json.has("email")) ? json.getString("email").trim() : "";
			String social = (json.has("social")) ? json.getString("social") : "NORMAL";
			String country = (json.has("country")) ? Useful.decodeStringToUTF8(json.getString("country")) : " ";
			String city = (json.has("city")) ? Useful.decodeStringToUTF8(json.getString("city")) : " ";
			String device = (json.has("device")) ? Useful.decodeStringToUTF8(json.getString("device")) : "None";
			String photo = json.has("photo") ? json.getString("photo") : "";
			String ageRange = json.has("ageRange") ? json.getString("ageRange") : "";
			String uuid = UUID.randomUUID().toString();
			// Validacao basica
			if (Validator.isEmptyString(name)) {
				throw new Exception("Name, this field cannot be empty");
			}

			if (Validator.isEmptyString(password)) {
				throw new Exception("Password, this field cannot be empty");
			}

			if (!Validator.isValidEmail(email)) {
				throw new Exception("Email, this field cannot be empty");
			}
			// Executando
			logger.info(name + " " + password + " " + provider + " " + email + " " + uuid);
			logger.info(data.toString());

			if (userService.getUser(email) == null) {
				User user = createUser(name, email, password, device, social, photo, city, country, uuid, ageRange);
				response.setProperty("status", "true");
				response.setProperty("data", user.getId().toString());
			} else {
				String[] hasSocial = { "FACEBOOK", "GOOGLE" };
				User ua = null;
				if (Validator.isValueinArray(hasSocial, social.toUpperCase())) {
					User hasUser = userService.getUser(email);
					if (hasUser == null) {
						ua = createUser(name, email, password, device, social, photo, city, country, uuid, ageRange);
					} else {
						hasUser.setName(name);
						hasUser.setSocial(social.toUpperCase());
						hasUser.setCurrentCountry(country);
						hasUser.setCurrentCity(city);
						hasUser.setDevice(device);
						hasUser.setPhoto(photo);
						hasUser.setCredentials(email, password);
						hasUser.setAgeRange(ageRange);
						// Update
						ua = userService.save(hasUser);
					}
				} else {
					ua = userService.findByEmailAndPassword(email, Useful.hashed(password, email));
				}

				if (ua == null) {
					throw new Exception("Username is already taken, Forgot your password?");
				} else {
					response.setProperty("status", "true");
					response.setProperty("data", ua.getId().toString());

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setResultCode(500);
			response.setProperty("status", "false");
			response.setProperty("message", Useful.replaceNull(e.getMessage()));
			// e.printStackTrace();
		}

		return response;
	}

	public User createUser(String name, String email, String password, String device, String social, String photo, String city, String country, String uuid, String ageRange) {
		User newUser = new UserBuilder().setCredentials(email, password).setSocial(social.toUpperCase()).setPhoto(photo).setSurname(" . ").setCurrentCity(city).setName(name).setCurrentProvince(country).setOfficialEmail(email)
				.setProjectEmail(email).setDevice(device).setCf(uuid).setIccid(uuid).setNewIccid(uuid).setCurrentAddress(" ").setBirthdate(null).setRegistrationDateTime(new DateTime()).setCurrentZipCode(" ").setGender(Gender.NONE)
				.setDocumentIdType(DocumentIdType.NONE).setDocumentId("N/A").setContactPhoneNumber(" ").setProjectPhoneNumber(" ").setUniCity(UniCity.FLORIANOPOLIS).setUniCourse(UniCourse.SOCIAL).setUniYear(0).setUniDegree("N/A")
				.setUniDepartment(" ").setUniSchool(UniSchool.OUTRO).setUniIsSupplementaryYear(false).setHasPhone(true).setWantsPhone(false).setIsActive(true).setHasSIM(false).setSimStatus(SimStatus.NO).setBadges(new HashSet<Badge>())
				.setAgeRange(ageRange).build(true);

		return userService.save(newUser);
	}
}
