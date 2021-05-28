package it.unibo.paserver.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.unibo.paserver.domain.Devices;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserDevice;
import it.unibo.paserver.domain.support.UserDeviceBuilder;
import it.unibo.paserver.service.DevicesService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.UserDeviceService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.paserver.web.validator.AddUserDeviceFormValidator;
import it.unibo.paserver.web.validator.EditUserDeviceFormValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

@Controller
public class UserDeviceController {
	
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
	private UserDeviceService userDeviceService;
	
	@Autowired
	private EditUserDeviceFormValidator editUserDeviceFormValidator;
	
	@Autowired
	private AddUserDeviceFormValidator addUserDeviceFormValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DevicesService deviceService;
	
	@Autowired
	private TaskReportService taskReportService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserDeviceController.class);
	
	@ModelAttribute("editUserDeviceForm")
	public EditUserDeviceForm getEditUserDeviceForm() {
		return new EditUserDeviceForm();
	}
	
	@ModelAttribute("addUserDeviceForm")
	public AddUserDeviceForm getAddUserDeviceForm() {
		return new AddUserDeviceForm();
	}

	@InitBinder("editUserDeviceForm")
	public void initBinderEdit(WebDataBinder binder) {
		binder.setValidator(editUserDeviceFormValidator);
	}
	
	@InitBinder("addUserDeviceForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(addUserDeviceFormValidator);
	}
	
	@RequestMapping(value = "/protected/userdevice", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listUserDevices(ModelAndView modelAndView) {
		List<UserDevice> userDevices = userDeviceService.getUserDevices();
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		modelAndView.addObject("userdevices", userDevices);
		modelAndView.addObject("totalUserDevices",
				userDevices.size());
		modelAndView.setViewName("/protected/userdevice");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/protected/userdevice/add", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addUserDevice(ModelAndView modelAndView) {
		addUserDeviceFormOptions(modelAndView);
		AddUserDeviceForm addUserDeviceForm = new AddUserDeviceForm();
		addUserDeviceForm.setPriority((long)99);
		modelAndView.addObject("addUserDeviceForm", addUserDeviceForm);
		modelAndView.setViewName("protected/userdevice/add");
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/userdevice/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView editUserDevice(@PathVariable Long id,
			@ModelAttribute EditUserDeviceForm editUserDeviceForm,
			ModelAndView modelAndView) {
		UserDevice userDevice = userDeviceService.getUserDevice(id);
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		if (userDevice == null) {
			throw new ResourceNotFoundException();
		}
		addUserDeviceFormOptions(modelAndView);
		editUserDeviceForm.initFormUserDevice(userDevice);
		modelAndView.addObject("userDeviceId", id);
		modelAndView.setViewName("protected/userdevice/edit");
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/userdevice/show/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView showUserDevice(@PathVariable Long id,
			ModelAndView modelAndView) {
		UserDevice userDevice = userDeviceService.getUserDevice(id);
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		if (userDevice == null) {
			throw new ResourceNotFoundException();
		}
		modelAndView.setViewName("protected/userdevice/show");
		modelAndView.addObject(userDevice);
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/protected/userdevice/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView postUserDeviceAdd(
			@ModelAttribute @Validated AddUserDeviceForm addUserDeviceForm,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (bindingResult.hasErrors()) {
			addUserDeviceFormOptions(modelAndView);
			modelAndView.addObject("formerror", Boolean.TRUE);
			modelAndView.setViewName("/protected/userdevice/add");
			modelAndView.addObject("controller", this.getClass().getSimpleName());
			return modelAndView;
		}

		UserDeviceBuilder udb = new UserDeviceBuilder();
		udb.setName(addUserDeviceForm.getName());
		udb.setImei(addUserDeviceForm.getUuid());
	    udb.setPriority(addUserDeviceForm.getPriority());
	    User user = userService.getUser(addUserDeviceForm.getUserId());
	    udb.setUser(user);
	    Devices device = deviceService.findById(addUserDeviceForm.getDeviceId());
		udb.setDevice(device);
		
		UserDevice userDevice = udb.build(true);
		userDevice = userDeviceService.save(userDevice);
		addUserDeviceFormOptions(modelAndView);
		modelAndView.setViewName("/protected/userdevice/confirm");
		modelAndView.addObject("userdevice", userDevice);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/userdevice/edit/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView postUserDeviceEdit(@PathVariable Long id,
			@ModelAttribute @Validated EditUserDeviceForm editUserDeviceForm,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes, Principal principal) {
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		if (bindingResult.hasErrors()) {
			addUserDeviceFormOptions(modelAndView);
			modelAndView.addObject("formerror", Boolean.TRUE);
			modelAndView.addObject("userDeviceId", id);
			modelAndView.setViewName("/protected/userdevice/edit");
			return modelAndView;
		}
		UserDevice userDevice = userDeviceService.getUserDevice(id);

	    userDevice.setName(editUserDeviceForm.getName());
	    userDevice.setImei(editUserDeviceForm.getUuid());
	    userDevice.setPriority(editUserDeviceForm.getPriority());
		userDevice.setUser(userService.getUser(editUserDeviceForm.getUserId()));
		userDevice.setDevice(deviceService.findById(editUserDeviceForm.getDeviceId()));
		
		userDevice = userDeviceService.save(userDevice);
		modelAndView.setViewName("redirect:/protected/userdevice/show/" + id);	
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/userdevice/delete", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView userDeviceForm(@RequestParam Integer id,
			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
		logger.info("Received request to delete user device {}", id);
		modelAndView.addObject("controller", this.getClass().getSimpleName());
		if (userDeviceService.deleteUserDevice(id)) {
			redirectAttributes.addFlashAttribute("successmessage",
					String.format("User Device #\"%d\" successfully deleted", id));
		} else {
			String msg = String
					.format("Unabe to delete user device #\"%d\", please consult logs for further information",
							id);
			redirectAttributes.addFlashAttribute("errormessage", msg);
		}
		modelAndView.setViewName("redirect:/protected/userdevice");
		return modelAndView;
	}

	@RequestMapping(value = "/userdevice/{userdeviceid}/stats", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> showStats(
			@PathVariable Long userdeviceid) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<TaskReport> trs = taskReportService.getTaskReportsByUserDevice(userdeviceid);
		String result = "[" + userdeviceid + "]";

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
				months[m] = "gennaio";
				break;
			case 2:
				months[m] = "febbario";
				break;
			case 3:
				months[m] = "marzo";
				break;
			case 4:
				months[m] = "aprile";
				break;
			case 5:
				months[m] = "maggio";
				break;
			case 6:
				months[m] = "giugno";
				break;
			case 7:
				months[m] = "luglio";
				break;
			case 8:
				months[m] = "agosto";
				break;
			case 9:
				months[m] = "settembre";
				break;
			case 10:
				months[m] = "ottobre";
				break;
			case 11:
				months[m] = "novembre";
				break;
			case 12:
				months[m] = "dicembre";
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

		List<UserDeviceStats> container = new ArrayList<UserDeviceStats>();

		stateT[] n = stateT.values();

		for (int i = 0; i < 6; ++i) {
			UserDeviceStats uds = new UserDeviceStats();
			uds.setName(n[i].name());
			int[] vec = new int[5];
			for (int m = 0; m < 5; ++m) {
				vec[m] = count[m][i];
			}
			uds.setData(vec);
			container.add(uds);
		}

		UserDeviceMonths udm = new UserDeviceMonths();
		udm.setUserDevice(container);
		udm.setMonths(months);
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(udm);
		} catch (JsonProcessingException e) {
			logger.error("Error while writing JSON", e);
		}

		return new ResponseEntity<String>(result, headers, HttpStatus.OK);

	}

	private void addUserDeviceFormOptions(ModelAndView modelAndView) {
		modelAndView.addObject("users", getUsers());
		modelAndView.addObject("devices", getDevices());
	}

	private Map<Long, String> getDevices() {
		List<Devices> devices = deviceService.findAll();
		Map<Long, String> result = new LinkedHashMap<Long, String>();
		result.put(null, "");
		for (Devices device : devices ) {
			result.put(device.getId(), device.getModel());
		}
		return result;
	}
	
	private Map<Long, String> getUsers() {
		List<User> users = userService.getUsers();
		Map<Long, String> result = new LinkedHashMap<Long, String>();
		result.put(null, "");
		for (User user : users ) {
			result.put(user.getId(), user.getOfficialEmail());
		}
		return result;
	}
		
}
