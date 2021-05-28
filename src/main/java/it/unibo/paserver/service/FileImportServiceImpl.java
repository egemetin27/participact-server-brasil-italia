package it.unibo.paserver.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.Useful;
import br.com.bergmannsoft.utils.Validator;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.FileUpload;
import it.unibo.paserver.domain.FilterByUser;
import it.unibo.paserver.domain.FilterByUserEsag;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.Institutions;
import it.unibo.paserver.domain.NotificationBar;
import it.unibo.paserver.domain.ResultType;
import it.unibo.paserver.domain.SchoolCourse;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.NotificationBarBuilder;
import it.unibo.paserver.domain.support.SchoolCourseBuilder;
import it.unibo.paserver.domain.support.UserBuilder;

@Service
public class FileImportServiceImpl implements FileImportService {
	private CSVReader reader;
	@Autowired
	private ParticipantService participantService;
	@Autowired
	private NotificationBarService notificationBarService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private SchoolCourseService schoolCourseService;
	@Autowired
	private InstitutionsService institutionsService;
	@Autowired
	ServletContext servletContext;

	/**
	 * Importacao via Scripts
	 * 
	 * @param f
	 */
	@Override
	@Async
	public void importUsersByScript(FileUpload f) {
		try {
			// System.out.println(f.getFilename());
			// System.out.println(f.getParentId());
			// System.out.println(f.getFileSource());
			// System.out.println(f.getUploadedFile().getAbsolutePath());

			String filename = f.getUploadedFile().getAbsolutePath();
			Long parentId = f.getParentId();
			String fileSource = f.getFileSource();

			String cmd = String.format(Config.PYTHON_SCRIPT_IMPORT, filename, fileSource, parentId);
			System.out.println(cmd);
			Process p = Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	/**
	 * Importacao via Java
	 */
	@Deprecated
	@Override
	@Async
	public void importUsersByFile(FileUpload f) {
		
		int error = 0;
		int success = 0;
		int rows = 0;
		int highestRow = 0;
		String message = null;
		long userId = 0L;
		ResultType resultType = ResultType.TYPE_NONE;
		try {
			char separator = ',';
			reader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(f.getFile())), separator);
			String[] headerRow = reader.readNext();
			int highestColumn = headerRow.length;
			if (null == headerRow || highestColumn <= 1) {
				throw new Exception(messageSource.getMessage("file.error.invalid", null, LocaleContextHolder.getLocale()));
			} else {
				// Vars
				highestRow = reader.getSkipLines();
				if (highestRow > 40000) {
					throw new Exception(messageSource.getMessage("file.error.rows", null, LocaleContextHolder.getLocale()));
				}
				if (highestColumn > FilterByUser.COUNT) {
					throw new Exception(messageSource.getMessage("file.error.cols", null, LocaleContextHolder.getLocale()));
				}
				// Header
				Map<Integer, String> headerIndex = new HashMap<Integer, String>();
				Map<String, Integer> headerCols = new HashMap<String, Integer>();
				int index = 0;
				int cols = 0;
				for (String h : headerRow) {
					String col = Useful.convertStringColumnName(h);
					String colEsag = h;
					if (FilterByUserEsag.hasEnumByValue(colEsag)) {
						headerIndex.put(index, colEsag);
						headerCols.put(colEsag, index);
						cols++;
					} else if (FilterByUser.hasEnumByValue(col)) {
						headerIndex.put(index, col);
						headerCols.put(col, index);
						cols++;
					}
					index++;
				}
				// Alguma coluna valida?
				if (cols == 0) {
					throw new Exception(messageSource.getMessage("file.error.any", null, LocaleContextHolder.getLocale()));
				}
				// Possui as colunas principais?
				String[] haystack = { "name", "email" };
				String[] esagHaystack = { "Nome", "Email" };
				if (!Validator.isCollectionInArray(haystack, headerIndex.values())) {
					if (!Validator.isCollectionInArray(esagHaystack, headerIndex.values())) {
						System.out.println(headerIndex.values().toString());
						throw new Exception(messageSource.getMessage("file.error.required", null, LocaleContextHolder.getLocale()));
					}
				}
				String[] nextLine;
				while ((nextLine = reader.readNext()) != null) {
					userId = 0L;
					rows++;
					// System.out.println(Arrays.toString(nextLine));
					// Credencias
					String name = headerCols.containsKey("name") ? nextLine[headerCols.get("name")] : null;
					if (Validator.isEmptyString(name)) {
						name = headerCols.containsKey("Nome") ? nextLine[headerCols.get("Nome")] : null;
						if (Validator.isEmptyString(name)) {
							break;
						}
					}

					String surname = headerCols.containsKey("surname") ? nextLine[headerCols.get("surname")] : null;
					if (Validator.isEmptyString(surname)) {
						surname = " . ";
					}

					String officialEmail = headerCols.containsKey("officialEmail") ? nextLine[headerCols.get("officialEmail")] : null;
					if (Validator.isEmptyString(officialEmail) && headerCols.containsKey("email")) {
						officialEmail = nextLine[headerCols.get("email")];
					} else if (Validator.isEmptyString(officialEmail) && headerCols.containsKey("Email")) {
						officialEmail = nextLine[headerCols.get("Email")];
					}

					String password = headerCols.containsKey("password") ? nextLine[headerCols.get("password")] : null;
					if (!Validator.isValidPassword(password)) {
						password = Config.defaultUserPassword;
					}

					String birthdate = headerCols.containsKey("birthdate") ? nextLine[headerCols.get("birthdate")] : null;
					if (Validator.isEmptyString(birthdate) && headerCols.containsKey("Data Nascimento")) {
						birthdate = nextLine[headerCols.get("Data Nascimento")];
					}

					String gender = headerCols.containsKey("gender") ? nextLine[headerCols.get("gender")] : null;
					if (Validator.isEmptyString(gender) && headerCols.containsKey("G�nero")) {
						gender = nextLine[headerCols.get("G�nero")];
					}

					Gender enumGender = Gender.NONE;
					if (!Validator.isEmptyString(gender) && gender.equals("M")) {
						enumGender = Gender.MALE;
					} else if (!Validator.isEmptyString(gender) && gender.equals("F")) {
						enumGender = Gender.FEMALE;
					}
					// Geo/Address
					String currentAddress = headerCols.containsKey("currentAddress") ? nextLine[headerCols.get("currentAddress")] : null;
					if (Validator.isEmptyString(currentAddress) && headerCols.containsKey("Rua")) {
						currentAddress = nextLine[headerCols.get("Rua")];
					}

					String currentDistrict = headerCols.containsKey("currentDistrict") ? nextLine[headerCols.get("currentDistrict")] : null;
					if (Validator.isEmptyString(currentDistrict) && headerCols.containsKey("Bairro")) {
						currentDistrict = nextLine[headerCols.get("Bairro")];
					}

					String currentZipCode = headerCols.containsKey("currentZipCode") ? nextLine[headerCols.get("currentZipCode")] : null;
					if (Validator.isEmptyString(currentZipCode) && headerCols.containsKey("CEP")) {
						currentZipCode = nextLine[headerCols.get("CEP")];
					}

					String currentCity = headerCols.containsKey("currentCity") ? nextLine[headerCols.get("currentCity")] : null;
					String currentNumber = headerCols.containsKey("currentNumber") ? Useful.removeAllNonNumeric(nextLine[headerCols.get("currentNumber")]) : null;
					String currentProvince = headerCols.containsKey("currentProvince") ? nextLine[headerCols.get("currentProvince")] : null;
					String currentCountry = headerCols.containsKey("currentCountry") ? nextLine[headerCols.get("currentCountry")] : null;
					String mapLat = headerCols.containsKey("mapLat") ? nextLine[headerCols.get("mapLat")] : null;
					String mapLng = headerCols.containsKey("mapLng") ? nextLine[headerCols.get("mapLng")] : null;
					// Contact
					String contactPhoneNumber = headerCols.containsKey("contactPhoneNumber") ? Useful.removeAllNonNumeric(nextLine[headerCols.get("contactPhoneNumber")]) : null;
					if (Validator.isEmptyString(contactPhoneNumber) && headerCols.containsKey("Fone")) {
						contactPhoneNumber = Useful.removeAllNonNumeric(nextLine[headerCols.get("Fone")]);
					}

					String homePhoneNumber = headerCols.containsKey("homePhoneNumber") ? Useful.removeAllNonNumeric(nextLine[headerCols.get("homePhoneNumber")]) : null;
					if (Validator.isEmptyString(homePhoneNumber) && headerCols.containsKey("Cel")) {
						homePhoneNumber = Useful.removeAllNonNumeric(nextLine[headerCols.get("Cel")]);
					}

					String device = headerCols.containsKey("device") ? nextLine[headerCols.get("device")] : null;
					String notes = headerCols.containsKey("notes") ? nextLine[headerCols.get("notes")] : null;
					// Course
					Institutions institutionId = null;
					UniCourse uniCourse = UniCourse.NONE;

					String uniDepartment = headerCols.containsKey("uniDepartment") ? nextLine[headerCols.get("uniDepartment")] : null;
					if (Validator.isEmptyString(uniDepartment) && headerCols.containsKey("Centro")) {
						uniDepartment = nextLine[headerCols.get("Centro")];
						institutionId = institutionsService.findById(752);
						uniCourse = UniCourse.GRADUATION;
					}

					String uniCodCourse = headerCols.containsKey("uniCodCourse") ? nextLine[headerCols.get("uniCodCourse")] : null;
					if (Validator.isEmptyString(uniCodCourse) && headerCols.containsKey("Cod Cur")) {
						uniCodCourse = nextLine[headerCols.get("Cod Cur")];
					}

					String schoolCourse = headerCols.containsKey("schoolCourse") ? nextLine[headerCols.get("schoolCourse")] : null;
					if (Validator.isEmptyString(schoolCourse) && headerCols.containsKey("Curso")) {
						schoolCourse = nextLine[headerCols.get("Curso")];
					}

					SchoolCourse schoolCourseId = schoolCourseService.findByName(schoolCourse);

					if (schoolCourseId == null && !Validator.isEmptyString(schoolCourse)) {
						// Build
						SchoolCourseBuilder sc = new SchoolCourseBuilder();
						sc.setAll(0L, schoolCourse, UniCourse.NONE, "");
						SchoolCourse s = sc.build(true);
						schoolCourseId = schoolCourseService.saveOrUpdate(s);
					}

					String uniPhase = headerCols.containsKey("uniPhase") ? nextLine[headerCols.get("uniPhase")] : "0";
					if (uniPhase == "0" && headerCols.containsKey("Fase")) {
						uniPhase = Useful.removeAllNonNumeric(nextLine[headerCols.get("Fase")]);
					}

					Integer pUniPhase = 0;
					if (!Validator.isEmptyString(uniPhase) && Validator.isValidNumeric(uniPhase)) {
						pUniPhase = Integer.parseInt(uniPhase);
					}

					String uniStatus = headerCols.containsKey("uniStatus") ? nextLine[headerCols.get("uniStatus")] : null;
					if (Validator.isEmptyString(uniStatus) && headerCols.containsKey("Status")) {
						uniStatus = nextLine[headerCols.get("Status")];
					}

					// Validacao restrita
					if (!Validator.isValidEmail(officialEmail)) {
						error++;
						continue;
					} else {
						User currentUser = participantService.findByEmail(officialEmail);
						if (currentUser != null) {
							userId = currentUser.getId();
						}
					}
					// Validacao basica
					LocalDate dt = new LocalDate();
					try {
						if (!Validator.isEmptyString(birthdate)) {
							if (Validator.isValidDateFormat(birthdate, "dd/MM/yyyy")) {
								dt = new LocalDate(Useful.getDateUSFromBR(birthdate));
							} else if (Validator.isValidDate(birthdate)) {
								DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
								dt = dtf.parseLocalDate(birthdate);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace(System.out);
					}
					// Salvando
					// Builder
					UserBuilder builder = new UserBuilder();
					builder.setCredentials(officialEmail, password);
					builder.setAll(userId, officialEmail, surname, name, enumGender, dt, homePhoneNumber, currentAddress, currentCity, currentProvince, currentZipCode, currentNumber, currentCountry, null, DocumentIdType.NONE,
							contactPhoneNumber, uniCourse, false, null, device, notes, institutionId, schoolCourseId, mapLat, mapLng);

					builder.setUniDepartment(uniDepartment);
					builder.setUniCodCourse(uniCodCourse);
					builder.setUniPhase(pUniPhase);
					builder.setUniStatus(uniStatus);
					builder.setFileSource(f.getFileSource());
					builder.setCurrentDistrict(currentDistrict);
					User u = builder.build(true);

					if (participantService.saveOrUpdate(u) != null) {
						success++;
					} else {
						error++;
					}
					// System.out.println(rows);
				}
			}
			Object[] args = new Object[] { rows, success, error };
			message = messageSource.getMessage("confirmation.file.result", args, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			// TODO: handle exception
			message = e.getMessage();
			resultType = ResultType.TYPE_ERROR;
			e.printStackTrace(System.out);
		}
		// Resultado
		if (!resultType.equals(ResultType.TYPE_ERROR)) {
			resultType = success == 0 ? ResultType.TYPE_WARNING : ResultType.TYPE_SUCCESS;
		}
		NotificationBarBuilder nb = new NotificationBarBuilder();
		NotificationBar n = nb.setAll(0, f.getParentId(), message, false, resultType).build(true);
		notificationBarService.saveOrUpdate(n);
	}
}
