package it.unibo.paserver.web.controller;

import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.BinaryDocumentType;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.service.AccountService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.paserver.web.functions.ItalianCitiesUtils;
import it.unibo.paserver.web.validator.AddAdminUserFormValidator;
import it.unibo.paserver.web.validator.EditAdminUserFormValidator;
import it.unibo.paserver.web.validator.EditUserFormValidator;

@Controller
public class UserController {

	private enum stateT {
		COMPLETED_WITH_FAILURE("COMPLETED_WITH_FAILURE"), COMPLETED_WITH_SUCCESS(
				"COMPLETED_WITH_SUCCESS"), IGNORED("IGNORED"), REJECTED(
				"REJECTED"), RUNNING("RUNNING"), AVAILABLE("AVAILABLE");

		private String statename;

		private stateT(String state) {
			this.statename = state;
		}

		@Override
		public String toString() {
			return statename;
		}
	}

	@Autowired
	TaskReportService taskReportService;

	@Autowired
	TaskService taskService;
	
	@Autowired
	TaskUserService taskUserService;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AddAdminUserFormValidator addUserFormValidator;

	@Autowired
	private EditAdminUserFormValidator editAdminUserFormValidator;

	@Autowired
	private EditUserFormValidator editUserFormValidator;

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@ModelAttribute("addAdminUserForm")
	public AddAdminUserForm getAddUserForm() {
		return new AddAdminUserForm();
	}

	@ModelAttribute("editAdminUserForm")
	public EditAdminUserForm getEditAdminUserForm() {
		return new EditAdminUserForm();
	}

	/*@ModelAttribute("editUserForm")
	public EditUserForm getEditUserForm() {
		return new EditUserForm();
	}*/

	@InitBinder("addAdminUserForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(addUserFormValidator);
	}

	@InitBinder("editAdminUserForm")
	public void initBinderEdit(WebDataBinder binder) {
		binder.setValidator(editAdminUserFormValidator);
	}

	/*@InitBinder("editUserForm")
	public void initBinderEditUser(WebDataBinder binder) {
		binder.setValidator(editUserFormValidator);
	}*/

	@RequestMapping(value = "/protected/user", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listUsers(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/user");
		modelAndView.addObject("users", userService.getUsers());
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/add", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addUser(ModelAndView modelAndView) {
		addUserFormOptions(modelAndView);
		AddAdminUserForm auf = new AddAdminUserForm();
		auf.setWantsPhone(true);
		modelAndView.addObject("addAdminUserForm", auf);
		modelAndView.setViewName("protected/user/add");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView editUser(@PathVariable Long id,
			@ModelAttribute EditAdminUserForm editUserForm,
			ModelAndView modelAndView) {
		User user = userService.getUser(id, true);
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		addUserFormOptions(modelAndView);
		editUserForm.initFromUser(user);
		modelAndView.addObject("userId", id);
		modelAndView.setViewName("protected/user/edit");
		return modelAndView;
	}

	/*@RequestMapping(value = "/protected/user/edit", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView editUser(Principal principal,
			@ModelAttribute EditUserForm editUserForm, ModelAndView modelAndView) {
		User user = userService.getUser(principal.getName());
		if (user == null) {
			throw new ResourceNotFoundException();
		}

		editUserForm.initFromUsers(user);
		modelAndView.addObject("userId", user.getId());
		modelAndView.setViewName("protected/webuser/profile/edit");

		return modelAndView;
	}*/

	@RequestMapping(value = "/protected/user/show/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView showAccount(@PathVariable Long id,
			ModelAndView modelAndView) {
		User user = userService.getUser(id);
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		modelAndView.setViewName("protected/user/show");
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/show", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showAccount(Principal principal,
			ModelAndView modelAndView) {
		User user = userService.getUser(principal.getName());
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		modelAndView.setViewName("protected/webuser/profile/show");
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/informativaprivacy/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView showInformativaPrivacy(@PathVariable Long id,
			ItalianCitiesUtils italianCitiesUtils, ModelAndView modelAndView) {
		User user = userService.getUser(id);
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		modelAndView.setViewName("protected/user/informativaprivacy");
		modelAndView.addObject("citybornname",
				italianCitiesUtils.getBornCity(user.getCf()));
		modelAndView.addObject("citybornprov",
				italianCitiesUtils.getBornCityProv(user.getCf()));
		modelAndView.addObject("today", new DateTime());
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/presaconsegnaphone/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView showPresaConsegnaPhone(@PathVariable Long id,
			ItalianCitiesUtils italianCitiesUtils, ModelAndView modelAndView) {
		User user = userService.getUser(id);
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		modelAndView.setViewName("protected/user/presaconsegnaphone");
		modelAndView.addObject("citybornname",
				italianCitiesUtils.getBornCity(user.getCf()));
		modelAndView.addObject("citybornprov",
				italianCitiesUtils.getBornCityProv(user.getCf()));
		modelAndView.addObject("today", new DateTime());
		modelAndView.addObject("uniSchoolTxt", user.getUniSchool().toString());
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/presaconsegnasim/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView showPresaConsegnaSim(@PathVariable Long id,
			ItalianCitiesUtils italianCitiesUtils, ModelAndView modelAndView) {
		User user = userService.getUser(id);
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		modelAndView.setViewName("protected/user/presaconsegnasim");
		modelAndView.addObject("citybornname",
				italianCitiesUtils.getBornCity(user.getCf()));
		modelAndView.addObject("citybornprov",
				italianCitiesUtils.getBornCityProv(user.getCf()));
		modelAndView.addObject("today", new DateTime());
		modelAndView.addObject("uniSchoolTxt", user.getUniSchool().toString());
		modelAndView.addObject(user);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/doc/{id}/{doctype}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<byte[]> downloadDoc(@PathVariable Long id,
			@PathVariable String doctype) {
		BinaryDocument bd = null;
		if ("idscan".equals(doctype)) {
			bd = getDocument(id, BinaryDocumentType.ID_SCAN);
		} else if ("lastinvoice".equals(doctype)) {
			bd = getDocument(id, BinaryDocumentType.LAST_INVOICE);
		} else if ("privacy".equals(doctype)) {
			bd = getDocument(id, BinaryDocumentType.PRIVACY);
		} else if ("presaconsegnaphone".equals(doctype)) {
			bd = getDocument(id, BinaryDocumentType.PRESA_CONSEGNA_PHONE);
		} else if ("presaconsegnasim".equals(doctype)) {
			bd = getDocument(id, BinaryDocumentType.PRESA_CONSEGNA_SIM);
		}
		if (bd == null) {
			throw new ResourceNotFoundException();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(bd.getContentType(), bd
				.getContentSubtype()));
		String filename = bd.getFilename().replaceAll("[^A-Za-z0-9. ]", "");
		headers.set("Content-Disposition",
				String.format("attachment; filename=\"%s\"", filename));
		return new ResponseEntity<byte[]>(bd.getContent(), headers,
				HttpStatus.CREATED);
	}

	private BinaryDocument getDocument(Long id, BinaryDocumentType type) {
		BinaryDocument bd = null;
		switch (type) {
		case ID_SCAN:
			bd = userService.getIdScan(id);
			break;
		case LAST_INVOICE:
			bd = userService.getLastInvoiceScan(id);
			break;
		case PRIVACY:
			bd = userService.getPrivacyScan(id);
			break;
		case PRESA_CONSEGNA_PHONE:
			bd = userService.getPresaConsegnaPhone(id);
			break;
		case PRESA_CONSEGNA_SIM:
			bd = userService.getPresaConsegnaSIM(id);
			break;
		default:
			bd = null;
		}
		if (bd == null) {
			logger.warn("Unable to retrieved user {} document {}", id, type);
			return null;
		}
		return bd;
	}

	@RequestMapping(value = "/protected/user/addAccount", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView postUserAdd(
			@ModelAttribute @Validated AddAdminUserForm auf,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (bindingResult.hasErrors()) {
			addUserFormOptions(modelAndView);
			modelAndView.addObject("formerror", Boolean.TRUE);
			modelAndView.setViewName("/protected/user/add");
			return modelAndView;
		}

		UserBuilder ub = new UserBuilder();
		auf.setAllFields(ub);
		ub.setRegistrationDateTime(new DateTime());
		ub.setIsActive(true);
		ub.setHasPhone(false);
		ub.setHasSIM(false);
		ub.setWantsPhone(true);
		ub.setIsMyCompanyRegistered(false);
		User user = ub.build(true);
		user = userService.save(user);

		modelAndView.setViewName("/protected/user/confirm");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/edit/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView postUserEdit(@PathVariable Long id,
			@ModelAttribute @Validated EditAdminUserForm euf,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (bindingResult.hasErrors()) {
			addUserFormOptions(modelAndView);
			modelAndView.addObject("formerror", Boolean.TRUE);
			modelAndView.addObject("userId", id);
			modelAndView.setViewName("/protected/user/edit");
			return modelAndView;
		}
		User user = userService.getUser(id, true);
		Set<Long> docIdsToDel = new TreeSet<Long>();
		euf.updateUser(user, docIdsToDel);
		user = userService.save(user);
		for (Long docid : docIdsToDel) {
			userService.deleteBinaryDocument(docid);
		}
		modelAndView.setViewName("redirect:/protected/user/show/" + id);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/edit", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView postUserEdit(Principal principal,
			@ModelAttribute @Validated EditUserForm euf,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes) {

		User user = userService.getUser(principal.getName());

		if (bindingResult.hasErrors()) {
			modelAndView.addObject("formerror", Boolean.TRUE);
			modelAndView.addObject("userId", user.getId());
			modelAndView.setViewName("/protected/webuser/profile/edit");
			return modelAndView;
		}
		euf.updateUser(user);
		user = userService.save(user);
		modelAndView.setViewName("redirect:/protected/user/show");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/user/delete", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView accountForm(@RequestParam Integer id,
			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
		logger.trace("Received request to delete user {}", id);
		if (userService.deleteUser(id)) {
			redirectAttributes.addFlashAttribute("successmessage",
					String.format("User #\"%d\" successfully deleted", id));
		} else {
			String msg = String
					.format("Unabe to delete user #\"%d\", please consult logs for further information",
							id);
			redirectAttributes.addFlashAttribute("errormessage", msg);
		}
		modelAndView.setViewName("redirect:/protected/user");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/secrettoken/{size}/{token}")
	public ResponseEntity<byte[]> encodeToken(
			@PathVariable("token") String token, @PathVariable("size") int size) {
		final HttpHeaders headers = new HttpHeaders();
		if (size < 30 || size > 1024 || token.length() > 512) {
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		QRCodeWriter qrwriter = new QRCodeWriter();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BitMatrix matrix = qrwriter.encode(token, BarcodeFormat.QR_CODE,
					size, size);
			MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
			baos.flush();
			headers.setContentType(MediaType.IMAGE_PNG);
			baos.close();
			return new ResponseEntity<byte[]>(baos.toByteArray(), headers,
					HttpStatus.CREATED);
		} catch (Exception e) {
			logger.warn("Error: {}", e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/user/{userid}/usertaskstats", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> showUserTaskStats(
			@PathVariable Long userid) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String result = "[" + userid + "]";

		List<TaskUser> taskUser = taskUserService.getTaskUserByOwner(userid);

		// group by month
		Map<String, List<TaskUser>> taskUserMap = new TreeMap<String, List<TaskUser>>();

		int yearmax = 0;
		int monthmax = 0;

		for (TaskUser tr : taskUser) {
			int year = tr.getTask().getStart().getYear();
			int month = tr.getTask().getStart().getMonthOfYear();
			if (year > yearmax || year == yearmax && month > monthmax) {
				yearmax = year;
				monthmax = month;
			}

			String yearmonth = String.format("%04d %02d", year, month);
			if (!taskUserMap.containsKey(yearmonth)) {
				taskUserMap.put(yearmonth, new LinkedList<TaskUser>());
			}
			taskUserMap.get(yearmonth).add(tr);
		}

		int count[][] = new int[5][4];
		String months[] = new String[5];

		int year = yearmax;

		for (int m = 0; m < 5; ++m) {
			int month = monthmax - m;
			if (month <= 0) {
				year = yearmax - 1;
				month = month + 12;
			}
			String today = String.format("%04d %02d", year, month);

			switch (month) {
			case 1:
				months[m] = "January";
				break;
			case 2:
				months[m] = "February";
				break;
			case 3:
				months[m] = "March";
				break;
			case 4:
				months[m] = "April";
				break;
			case 5:
				months[m] = "May";
				break;
			case 6:
				months[m] = "June";
				break;
			case 7:
				months[m] = "July";
				break;
			case 8:
				months[m] = "August";
				break;
			case 9:
				months[m] = "September";
				break;
			case 10:
				months[m] = "October";
				break;
			case 11:
				months[m] = "November";
				break;
			case 12:
				months[m] = "December";
				break;

			}

			// controllo
			if (taskUserMap.containsKey(today)) {
				int listl = taskUserMap.get(today).size();
				for (int i = 0; i < listl; ++i) {
					switch (taskUserMap.get(today).get(i).getValutation()) {
					case PENDING:
						count[m][0]++;
						break;
					case APPROVED:
						count[m][1]++;
						break;
					case REFUSED:
						count[m][2]++;
						break;
					default:
						count[m][3]++;
					}
				}
			}

		}

		result = "[]";

		List<UserStats> container = new ArrayList<UserStats>();

		TaskValutation[] valutations = TaskValutation.values();

		for (int i = 0; i < 3; ++i) {
			UserStats us = new UserStats();
			us.setName(valutations[i].name());
			int[] vec = new int[5];
			for (int m = 0; m < 5; ++m) {
				vec[m] = count[m][i];
			}
			us.setData(vec);
			container.add(us);
		}

		UserMonths um = new UserMonths();
		um.setUser(container);
		um.setMonths(months);
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(um);
		} catch (JsonProcessingException e) {
			logger.error("Error while writing JSON", e);
		}

		return new ResponseEntity<String>(result, headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/{userid}/stats", method = RequestMethod.GET)
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public @ResponseBody ResponseEntity<String> showStats(
			@PathVariable Long userid) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<TaskReport> trs = taskReportService.getTaskReportsByUser(userid);
		String result = "[" + userid + "]";

		// group taskreports by month
		Map<String, List<TaskReport>> taskReportMap = new TreeMap<String, List<TaskReport>>();
		int yearmax = 0;
		int monthmax = 0;

		for (TaskReport tr : trs) {
			int year = tr.getTask().getStart().getYear();
			int month = tr.getTask().getStart().getMonthOfYear();
			if (year > yearmax || year == yearmax && month > monthmax) {
				yearmax = year;
				monthmax = month;
			}

			String yearmonth = String.format("%04d %02d", year, month);
			if (!taskReportMap.containsKey(yearmonth)) {
				taskReportMap.put(yearmonth, new LinkedList<TaskReport>());
			}
			taskReportMap.get(yearmonth).add(tr);
		}

		int count[][] = new int[5][7];

		String months[] = new String[5];

		int year = yearmax;
		// int month = monthmax;
		for (int m = 0; m < 5; ++m) {
			int month = monthmax - m;
			if (month <= 0) {
				year = yearmax - 1;
				month = month + 12;
			}
			String today = String.format("%04d %02d", year, month);

			switch (month) {
			case 1:
				months[m] = "January";
				break;
			case 2:
				months[m] = "February";
				break;
			case 3:
				months[m] = "March";
				break;
			case 4:
				months[m] = "April";
				break;
			case 5:
				months[m] = "May";
				break;
			case 6:
				months[m] = "June";
				break;
			case 7:
				months[m] = "July";
				break;
			case 8:
				months[m] = "August";
				break;
			case 9:
				months[m] = "September";
				break;
			case 10:
				months[m] = "October";
				break;
			case 11:
				months[m] = "November";
				break;
			case 12:
				months[m] = "December";
				break;

			}

			// controllo
			if (taskReportMap.containsKey(today)) {
				int listl = taskReportMap.get(today).size();
				for (int i = 0; i < listl; ++i) {
					switch (taskReportMap.get(today).get(i).getCurrentState()) {
					case COMPLETED_WITH_FAILURE:
						count[m][0]++;
						break;
					case COMPLETED_WITH_SUCCESS:
						count[m][1]++;
						break;
					case IGNORED:
						count[m][2]++;
						break;
					case REJECTED:
						count[m][3]++;
						break;
					case RUNNING:
						count[m][4]++;
						break;
					case AVAILABLE:
						count[m][5]++;
						break;
					default:
						count[m][6]++;
					}
				}
			}

		}

		result = "[]";

		List<UserStats> container = new ArrayList<UserStats>();

		stateT[] n = stateT.values();

		for (int i = 0; i < 6; ++i) {
			UserStats us = new UserStats();
			us.setName(n[i].name());
			int[] vec = new int[5];
			for (int m = 0; m < 5; ++m) {
				vec[m] = count[m][i];
			}
			us.setData(vec);
			container.add(us);
		}

		UserMonths um = new UserMonths();
		um.setUser(container);
		um.setMonths(months);
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(um);
		} catch (JsonProcessingException e) {
			logger.error("Error while writing JSON", e);
		}

		return new ResponseEntity<String>(result, headers, HttpStatus.OK);

	}

	private void addUserFormOptions(ModelAndView modelAndView) {
		modelAndView.addObject("documentTypes", getDocumentTypes());
		modelAndView.addObject("uniCities", getUniCities());
		modelAndView.addObject("uniSchools", getUniSchools());
		modelAndView.addObject("uniCourses", getUniCourses());
		modelAndView.addObject("genders", getGenders());
		modelAndView.addObject("simStatuses", getSimStatuses());
		modelAndView.addObject("devices", getDevices());
	}
	
	private Map<String, String> getDevices() {
		Map<String, String> uniCities = new LinkedHashMap<String, String>();
		uniCities.put("iphone4", "Iphone 4");
		uniCities.put("iphone4s", "Iphone 4s");
		uniCities.put("iphone5", "Iphone 5");
		uniCities.put("iphone5c", "Iphone 5c");
		uniCities.put("iphone5s", "Iphone 5s");
		uniCities.put("iphone6", "Iphone 6");
		uniCities.put("iphone6+", "Iphone 6+");
		uniCities.put("SonyXperia Z2, Z2 Tablet", "XperiaZ2");
		uniCities.put("SonyXperia Z1 Compact", "ZperiaZ1");
		uniCities.put("SonyXperia Z Tablet Z", "ZperiaZ");
		uniCities.put("SamsungGalaxy Mini S5", "GalaxiS5");
		uniCities.put("SamsungGalaxy Note 4", "Galaxy4");
		uniCities.put("SamsungGalaxy Note 4 Edge", "GalaxyN4");
		uniCities.put("SamsungAlpha Galaxy", "GalaxyAlpha");
		uniCities.put("SamsungGalaxy S4", "GalaxyS4");
		uniCities.put("SamsungGalaxy Note 3", "Galaxy3");
		uniCities.put("SamsungGalaxy Note 2", "Galaxy2");
		uniCities.put("SamsungGalaxy S3", "GalaxyS3");
		uniCities.put("SamsungS Galaxy Tab 8.4 and 10. 5", "GalaxyS");
		uniCities.put("SamsungGalaxy Tab Pro / Note 10.1 and 12.2 (2014)", "GalaxyTab");
		uniCities.put("HTCM8 Mini One", "HTCM8");
		uniCities.put("HTCOne M7", "HTMM7");
		uniCities.put("HTCDesire EYE", "HTCEye");
		uniCities.put("HTCDesire 816", "HTC816");
		uniCities.put("LG G2", "LGS2");
		uniCities.put("LG G3S", "LGS3S");
		uniCities.put("Google Nexus 5", "gNexus5");
		uniCities.put("Google Nexus 6", "gNexus6");
		uniCities.put("Google Nexus 9", "gNexus9");
		uniCities.put("Google Nexus 10", "gNexus10");
		uniCities.put("MotorolaMoto X 2013", "motoX2013");
		uniCities.put("MotorolaMoto G 2014", "motoX2014");
		uniCities.put("MotorolaMoto G 4G", "moto4G");
		uniCities.put("MotorolaMoto G 2013", "moto2013");
		uniCities.put("MotorolaMoto E", "motoE");
		uniCities.put("MotorolaMoto E 4G (2015)", "motoE4G");
		
		return uniCities;
	}

	private Map<String, String> getDocumentTypes() {
		Map<String, String> documentTypes = new LinkedHashMap<String, String>();
		documentTypes.put("", "");
		documentTypes.put(DocumentIdType.NATIONAL_ID.toString(),
				"Identity");
		documentTypes.put(DocumentIdType.DRIVING_LICENCE.toString(),
				"Driver's licence");
		documentTypes.put(DocumentIdType.PASSPORT.toString(), "Passport");
		return documentTypes;
	}

	private Map<String, String> getUniCities() {
		Map<String, String> uniCities = new LinkedHashMap<String, String>();
		uniCities.put("", "");
		uniCities.put(UniCity.FLORIANOPOLIS.toString(), "Florianopolis");
		uniCities.put(UniCity.SAO_JOSE.toString(), "Sao Jose");
		uniCities.put(UniCity.BIGUACU.toString(), "Biguacu");
		uniCities.put(UniCity.PALHOCA.toString(), "Palhoca");
		uniCities.put(UniCity.SANTO_AMARO_IMPERATRIZ.toString(), "Santo Amaro da Imperatriz");
		uniCities.put(UniCity.BOLOGNA.toString(), "Bologna");
		uniCities.put(UniCity.CESENA.toString(), "Cesena");
		return uniCities;
	}
	
	private Map<String, String> getGenders() {
		Map<String, String> genders = new LinkedHashMap<String, String>();
		genders.put(Gender.MALE.toString(), "Male");
		genders.put(Gender.FEMALE.toString(), "Female");
		return genders;
	}

	private Map<String, String> getUniSchools() {
		Map<String, String> uniSchools = new LinkedHashMap<String, String>();
		uniSchools.put("", "");
		
		uniSchools.put(UniSchool.ADM.toString(),"Administracao Empresarial");
		uniSchools.put(UniSchool.ADM_PUBLIC.toString(),"Administracao Publica");
		uniSchools.put(UniSchool.AGRONOMIA.toString(),"Agronomia");
		uniSchools.put(UniSchool.ARQUITETURA.toString(),"Arquitetura e Urbanismo");
		uniSchools.put(UniSchool.ARTES_BACH.toString(),"Artes Visuais (Bacharelado)");
		uniSchools.put(UniSchool.ARTES_LIC.toString(),"Artes Visuais (Licenciatura)");
		uniSchools.put(UniSchool.BIBLIOTECONOMIA.toString(),"Biblioteconomia - Gestao da Informacao (Bacharelado)");
		uniSchools.put(UniSchool.COMPUTACAO.toString(),"Ciencia da Computacao");
		uniSchools.put(UniSchool.ECONOMICA.toString(),"Ciencias Economicas");
		uniSchools.put(UniSchool.CONTABEIS.toString(),"Ciencias Contabeis (Bacharelado)");
		uniSchools.put(UniSchool.DESIGN_GRAFICO.toString(),"Design - Habilitacao em Design Grafico");
		uniSchools.put(UniSchool.DESIGN_INDUSTRIAL.toString(),"Design - Habilitacao em Design Industrial");
		uniSchools.put(UniSchool.ED_FISICA_LIC.toString(),"Educacao Fisica (Licenciatura)");
		uniSchools.put(UniSchool.ED_FISICA_BACH.toString(),"Educacao Fisica (Bacharelado)");
		uniSchools.put(UniSchool.ENFERMAGEM.toString(),"Enfermagem - enfase em Saude Publica");
		uniSchools.put(UniSchool.AMBIENTAL.toString(),"Engenharia Ambiental");
		uniSchools.put(UniSchool.CIVIL.toString(),"Engenharia Civil");
		uniSchools.put(UniSchool.PESCA.toString(),"Engenharia da Pesca");
		uniSchools.put(UniSchool.ALIMENTOS.toString(),"Engenharia de Alimentos");
		uniSchools.put(UniSchool.PETROLEO.toString(),"Engenharia de Petroleo");
		uniSchools.put(UniSchool.PRODUCAO_MEC.toString(),"Engenharia de Producao Habilitacao Mecanica");
		uniSchools.put(UniSchool.PRODUCAO_SIS.toString(),"Engenharia de Producao e Sistemas");
		uniSchools.put(UniSchool.ENG_SOFTWARE.toString(),"Engenharia de Software");
		uniSchools.put(UniSchool.ENG_ELETRICA.toString(),"Engenharia Eletrica");
		uniSchools.put(UniSchool.ENG_FLORESTAL.toString(),"Engenharia Florestal");
		uniSchools.put(UniSchool.ENG_MECANICA.toString(),"Engenharia Mecanica");
		uniSchools.put(UniSchool.ENG_QUIMICA.toString(),"Engenharia Quimica");
		uniSchools.put(UniSchool.ENG_SANITARIA.toString(),"Engenharia Sanitaria");
		uniSchools.put(UniSchool.FISICA.toString(),"Fisica (Licenciatura)");
		uniSchools.put(UniSchool.FISIOTERAPIA.toString(),"Fisioterapia");
		uniSchools.put(UniSchool.GEOGRAFIA_BACH.toString(),"Geografia (Bacharelado)");
		uniSchools.put(UniSchool.GEOGRAFIA_LIC.toString(),"Geografia (Licenciatura)");
		uniSchools.put(UniSchool.HISTORIA_BACH.toString(),"Historia (Bacharelado)");
		uniSchools.put(UniSchool.HISTORIA_LIC.toString(),"Historia (Licenciatura)");
		uniSchools.put(UniSchool.MATEMATICA.toString(),"Matematica (Licenciatura)");
		uniSchools.put(UniSchool.MEDICINA_VET.toString(),"Medicina Veterinaria");
		uniSchools.put(UniSchool.MODA.toString(),"Moda");
		uniSchools.put(UniSchool.PIANO.toString(),"Musica - Piano (Bacharelado)");
		uniSchools.put(UniSchool.VIOLAO.toString(),"Musica - Violao (Bacharelado)");
		uniSchools.put(UniSchool.VIOLINO.toString(),"Musica - Violino ou Viola (Bacharelado)");
		uniSchools.put(UniSchool.VIOLONCELO.toString(),"Musica - Violoncelo (Bacharelado)");
		uniSchools.put(UniSchool.MUSICA.toString(),"Musica (Licenciatura)");
		uniSchools.put(UniSchool.PEDAGOGIA.toString(),"Pedagogia");
		uniSchools.put(UniSchool.QUIMICA.toString(),"Quimica ( Licenciatura )");
		uniSchools.put(UniSchool.SISTEMA_INFORMACAO.toString(),"Sistemas de Informacao");
		uniSchools.put(UniSchool.TEATRO.toString(),"Teatro ( Licenciatura)");
		uniSchools.put(UniSchool.ANALISE_SISTEMAS.toString(),"Tecnologia em Analise e Desenvolvimento de Sistemas");
		uniSchools.put(UniSchool.PROD_MOVELEIRA.toString(),"Tecnologia em Producao Moveleira");
		uniSchools.put(UniSchool.ZOOTECNICA.toString(),"Zootecnia - enfase em Producao Animal Sustentavel");
		
		/*uniSchools.put(UniSchool.AGRARIA_E_MEDICINA_VETERINARIA.toString(),
				"Agraria e Medicina veterinaria");
		uniSchools.put(UniSchool.ECONOMIA_MANAGEMENT_E_STATISTICA.toString(),
				"Economia, Management e Statistica");
		uniSchools.put(
				UniSchool.FARMACIA_BIOTECNOLOGIE_E_SCIENZE_MOTORIE.toString(),
				"Farmacia, Biotecnologie e Scienze Motorie");
		uniSchools.put(UniSchool.GIURISPRUDENZA.toString(), "Giurisprudenza");
		uniSchools.put(UniSchool.INGEGNERIA_E_ARCHITETTURA.toString(),
				"Ingegneria e Architettura");
		uniSchools.put(UniSchool.LETTERE_E_BENI_CULTURALI.toString(),
				"Lettere e Beni Culturali");
		uniSchools.put(
				UniSchool.LINGUE_E_LETTERATURE_TRADUZIONE_E_INTERPRETAZIONE
						.toString(),
				"Lingue e Letterature, Traduzione e Interpretazione");
		uniSchools.put(UniSchool.MEDICINA_E_CHIRURGIA.toString(),
				"Medicina e Chirurgia");
		uniSchools.put(
				UniSchool.PSICOLOGIA_E_SCIENZE_DELLA_FORMAZIONE.toString(),
				"Psicologia e Scienze della Formazione");
		uniSchools.put(UniSchool.SCIENZE.toString(), "Scienze");
		uniSchools.put(UniSchool.SCIENZE_POLITICHE.toString(),
				"Scienze Politiche");
				*/
				
		return uniSchools;
	}

	private Map<String, String> getUniCourses() {
		Map<String, String> uniCourses = new LinkedHashMap<String, String>();
		uniCourses.put("", "");
		uniCourses.put(UniCourse.GRADUACAO.toString(), "Graduacao");
		uniCourses.put(UniCourse.MESTRADO.toString(), "Mestrado");
		uniCourses.put(UniCourse.DOUTORADO.toString(), "Doutorado");
		return uniCourses;
	}

	private Map<String, String> getSimStatuses() {
		Map<String, String> simStatuses = new LinkedHashMap<String, String>();
		simStatuses.put("", "");
		simStatuses.put(SimStatus.PORTABILITY.toString(), "Portabilit??");
		simStatuses.put(SimStatus.NEW_SIM.toString(),
				"Nuova SIM (no portabilt??)");
		simStatuses.put(SimStatus.NO.toString(), "Nessuna SIM");
		return simStatuses;
	}
}
