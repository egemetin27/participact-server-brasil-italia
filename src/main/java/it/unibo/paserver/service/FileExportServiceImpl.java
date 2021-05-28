package it.unibo.paserver.service;

import br.com.bergmannsoft.config.Config;
import br.com.bergmannsoft.utils.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.support.NotificationBarBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

@SuppressWarnings("Duplicates")
@Service
public class FileExportServiceImpl implements FileExportService {
    @Autowired
    ServletContext servletContext;
    @Autowired
    private NotificationBarService notificationBarService;
    // @Autowired
    // private TaskResultService taskResultService;
    @Autowired
    private TaskReportService taskReportService;
    @Autowired
    private DataService dataService;
    @Autowired
    private IssueReportService issueReportService;
    @Autowired
    private MessageSource messageSource;
    private String message = null;
    private ResultType resultType = ResultType.TYPE_SUCCESS;
    private File directory;
    private CSVWriter writer;

    /**
     * Cria um CSV file com dados de usuario
     */
    @Override
    @Async
    public void createUserCsvFile(List<Object[]> items, long parentId) {
        // File
        final String CSV = parentId + "-" + UUID.randomUUID().toString() + ".csv";
        String filename = servletContext.getRealPath(Config.PRODUCTION_RESOURCES_FILES)
                + System.getProperty("file.separator") + CSV;
        String alink = Config.defaultAlink;
        try {
            writer = new CSVWriter(new FileWriter(filename, true), ';', CSVWriter.NO_QUOTE_CHARACTER);
            String[] header = {"id", "name", "surname", "email", "photo", "contactPhoneNumber", "homePhoneNumber",
                    "currentAddress", "currentCity", "currentProvince", "currentZipCode", "gender", "removed",
                    "institutionId", "schoolCourseId"};
            writer.writeNext(header);
            for (Object[] item : items) {
                List<Object> nextLine = Lists.newArrayList(item);
                String[] row = new String[nextLine.size()];
                int index = 0;
                for (Object value : nextLine) {
                    row[index] = value != null ? (String) value.toString() : "";
                    index++;
                }
                writer.writeNext(row);
            }
            writer.close();
            // Ready
            alink = Config.PRODUCTION_HOST + Config.PRODUCTION_RESOURCES_FILES + System.getProperty("file.separator")
                    + CSV;
            message = messageSource.getMessage("confirmation.download", new Object[]{alink},
                    LocaleContextHolder.getLocale());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = messageSource.getMessage("download.error.written", null, LocaleContextHolder.getLocale());
            resultType = ResultType.TYPE_ERROR;
            e.printStackTrace();
        }
        // Notification bar
        NotificationBarBuilder nb = new NotificationBarBuilder();
        nb.setAll(0, parentId, message, false, resultType);
        nb.setAlink(alink);
        NotificationBar n = nb.build(true);
        notificationBarService.saveOrUpdate(n);
    }

    /**
     * Cria um CSV file com os dados da campanha
     */
    @Override
    @Async
    public void createTaskCsvFile(List<Task> items, long userId) {
        // Pasta principal
        final String foldername = userId + "-" + UUID.randomUUID().toString();
        String separator = System.getProperty("file.separator");
        String rootPath = servletContext.getRealPath(Config.PRODUCTION_RESOURCES_FILES) + separator;
        String path = rootPath + foldername;
        String alink = Config.defaultAlink;
        boolean hasData = false;
        ArrayList<Long> duplicates = new ArrayList<Long>();
        ArrayList<String> files = new ArrayList<String>();
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        try {
            directory = new File(path);
            if (!directory.exists() && directory.mkdir() && !items.isEmpty()) {
                // Lista com todos
                String taskFile = path + separator + Task.class.getSimpleName() + ".csv";
                PrintWriter writer = new PrintWriter(taskFile, "UTF-8");
                writer.println(Useful.getTaskValueAsString(items));
                writer.close();
                // Dados de cada item
                for (Task item : items) {
                    Long taskId = item.getId();
                    if (!Validator.isValueinArray(duplicates, taskId)) {
                        duplicates.add(taskId);
                        String subpath = path + separator + taskId;
                        directory = new File(subpath);
                        if (!directory.exists() && directory.mkdir()) {
                            Set<Action> actions = item.getActions();
                            if (!actions.isEmpty() && actions.size() > 0) {
                                // Lista de usuarios
                                List<Object[]> listUsers = taskReportService.getTaskReportsByTaskUniqueUser(taskId);
                                if (listUsers.size() > 0) {
                                    for (Object itemUser : listUsers) {
                                        Long itemUserId = Long.parseLong((String) itemUser.toString());
                                        // Begin for Action
                                        for (Action a : actions) {
                                            String actionName = Useful.getTranslatedActionType(a).toUpperCase();
                                            String actionFile = path + separator + taskId + separator + actionName + ".csv";
                                            String actionString = Useful.getActionValueAsString(a);
                                            // Print CSV Action
                                            writer = new PrintWriter(actionFile, "UTF-8");
                                            writer.println(actionString);
                                            writer.close();
                                            params = ArrayListMultimap.create();
                                            // System.out.println(actionFile);
                                            // Print CSV DATA
                                            if (a instanceof ActionSensing) {
                                                // process sensing actions
                                                Class<? extends Data> clazz = ((ActionSensing) a).getDataClass();
                                                String className = clazz.getName();
                                                // Writer
                                                String dataFile = path + separator + taskId + separator + actionName + "_" + clazz.getSimpleName().toUpperCase() + ".csv";
                                                this.writer = new CSVWriter(new FileWriter(dataFile, true), ';', CSVWriter.NO_QUOTE_CHARACTER);
                                                // Search values
                                                List<? extends Data> data = dataService.search((Class<? extends Data>) Class.forName(className), item.getStart(), item.getDeadline(), itemUserId, params, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
                                                List<String[]> dataString = Useful.getDataValueAsString(data, className,
                                                        this.writer, !files.contains(actionFile));
                                                hasData = true;
                                                // Add
                                                if (!files.contains(actionFile) && data.size() > 0) {
                                                    files.add(actionFile);
                                                    // System.out.println(actionFile.toString());
                                                }
                                            } else if (a instanceof ActionActivityDetection) {
                                                // process sensing actions
                                                String className = DataActivityRecognitionCompare.class.getName();
                                                // Writer
                                                String dataFile = path + separator + taskId + separator + actionName
                                                        + "_" + DataActivityRecognitionCompare.class.getSimpleName()
                                                        .toUpperCase()
                                                        + ".csv";
                                                this.writer = new CSVWriter(new FileWriter(dataFile, true), ';', CSVWriter.NO_QUOTE_CHARACTER);
                                                // Search values
                                                List<? extends Data> data = dataService.search((Class<? extends Data>) Class.forName(className), item.getStart(), item.getDeadline(), itemUserId, params, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
                                                List<String[]> dataString = Useful.getDataValueAsString(data, className, this.writer, !files.contains(actionFile));
                                                hasData = true;
                                                if (!files.contains(actionFile) && data.size() > 0) {
                                                    files.add(actionFile);
                                                }
                                            } else if (a instanceof ActionPhoto) {
                                                String dataPath = path + separator + taskId + separator + actionName
                                                        + "_" + ActionPhoto.class.getSimpleName().toUpperCase();
                                                directory = new File(dataPath);
                                                if ((directory.exists()) || (!directory.exists() && directory.mkdir())) {
                                                    List<DataPhoto> photos = dataService.searchActionPhoto(taskId, itemUserId, a.getId(), PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
                                                    for (DataPhoto p : photos) {
                                                        String owerName = String.format("_%s_%s", Useful.removeAllSpecialChar(p.getUser().getName()), Useful.removeAllSpecialChar(p.getUser().getOfficialEmail()));
                                                        String suffix = Useful.getFileSuffix(p.getFile().getContent());
                                                        String filename = dataPath + separator + p.getId() + owerName
                                                                + "." + suffix;
                                                        // System.out.println(filename);
                                                        Useful.convertByteArrayToImage(p.getFile().getContent(),
                                                                filename, suffix);
                                                    }
                                                    hasData = true;
                                                }
                                            } else if (a instanceof ActionQuestionaire) {
                                                // Perguntas fechadas
                                                List<Object[]> dataClosed = dataService
                                                        .searchActionQuestionClosed(a.getId());
                                                if (!dataClosed.isEmpty() && dataClosed.size() > 0) {
                                                    // Writer
                                                    String dataFile = path + separator + taskId + separator + actionName + "_" + a.getId() + "_MULTIQUESTION.csv";
                                                    this.writer = new CSVWriter(new FileWriter(dataFile, true), ';', CSVWriter.NO_QUOTE_CHARACTER);
                                                    Useful.getQuestionValueAsString(dataClosed, this.writer);
                                                    hasData = true;
                                                }
                                                // Perguntas Abertas
                                                List<Object[]> dataOpen = dataService
                                                        .searchActionQuestionOpen(a.getId());
                                                if (!dataOpen.isEmpty() && dataOpen.size() > 0) {
                                                    // Writer
                                                    String dataFile = path + separator + taskId + separator + actionName
                                                            + "_" + a.getId() + "_OPEN.csv";
                                                    this.writer = new CSVWriter(new FileWriter(dataFile, true), ';', CSVWriter.NO_QUOTE_CHARACTER);
                                                    Useful.getQuestionValueAsString(dataOpen, this.writer);
                                                    hasData = true;
                                                }
                                            }
                                        }
                                        // End for Action
                                    } // End for user itens
                                }
                            }

                        } else {
                            message = messageSource.getMessage("download.error.mkdir", null, LocaleContextHolder.getLocale());
                            resultType = ResultType.TYPE_ERROR;
                        }
                    }
                }
                // Has Data
                if (hasData) {
                    // Zip
                    String zipFileName = foldername + ".zip";
                    String zipFilePath = rootPath + zipFileName;
                    String zipDirPath = path;
                    ZipUtils.zipDir(zipFilePath, zipDirPath);
                    // Ready
                    alink = Config.PRODUCTION_HOST + Config.PRODUCTION_RESOURCES_FILES + separator + zipFileName;
                    message = messageSource.getMessage("confirmation.download", new Object[]{alink},
                            LocaleContextHolder.getLocale());
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = messageSource.getMessage("download.error.written", null, LocaleContextHolder.getLocale());
            resultType = ResultType.TYPE_ERROR;
            e.printStackTrace();
        }
        // Notification bar
        NotificationBarBuilder nb = new NotificationBarBuilder();
        nb.setAll(0, userId, message, false, resultType);
        nb.setAlink(alink);
        NotificationBar n = nb.build(true);
        notificationBarService.saveOrUpdate(n);
    }

    @Override
    @Async
    public void createGpsCsvFile(long userId) {
        // Pasta principal
        final String foldername = userId + "-" + UUID.randomUUID().toString();
        String separator = System.getProperty("file.separator");
        String rootPath = servletContext.getRealPath(Config.PRODUCTION_RESOURCES_FILES) + separator;
        String path = rootPath + foldername;
        String alink = Config.defaultAlink;
        ArrayList<String> files = new ArrayList<String>();
        ListMultimap<String, Object> params = ArrayListMultimap.create();
        try {
            directory = new File(path);
            if (!directory.exists() && directory.mkdir()) {
                // Date/Time
                DateTime s = new DateTime().minusYears(3);
                DateTime e = new DateTime().plusMinutes(10);
                ActionActivityDetection a = new ActionActivityDetection();
                String actionName = Useful.getTranslatedActionType(a).toUpperCase();
                String actionFile = path + separator + actionName + ".csv";
                String actionString = Useful.getActionValueAsString(a);
                // Print CSV Action
                PrintWriter writer = new PrintWriter(actionFile, "UTF-8");
                writer.println(actionString);
                writer.close();
                params = ArrayListMultimap.create();
                // process sensing actions
                Class<? extends Data> clazz = DataLocation.class;
                String className = clazz.getName();
                // Writer
                String dataFile = path + separator + clazz.getSimpleName().toUpperCase() + ".csv";
                this.writer = new CSVWriter(new FileWriter(dataFile, true), ';', CSVWriter.NO_QUOTE_CHARACTER);
                // Search values
                List<? extends Data> data = dataService.search((Class<? extends Data>) Class.forName(className), s, e, 0L, params, PaginationUtil.pagerequest(Config.SELECT_MIN_OFFSET, Config.SELECT_MAX_COUNT));
                List<String[]> dataString = Useful.getDataValueAsString(data, className, this.writer, !files.contains(actionFile));
                // Add
                if (!files.contains(actionFile) && data.size() > 0) {
                    files.add(actionFile);
                    // System.out.println(actionFile.toString());
                }
                // Zip
                String zipFileName = foldername + ".zip";
                String zipFilePath = rootPath + zipFileName;
                String zipDirPath = path;
                ZipUtils.zipDir(zipFilePath, zipDirPath);
                // Ready
                alink = Config.PRODUCTION_HOST + Config.PRODUCTION_RESOURCES_FILES + separator + zipFileName;
                message = messageSource.getMessage("confirmation.download", new Object[]{alink},
                        LocaleContextHolder.getLocale());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = messageSource.getMessage("download.error.written", null, LocaleContextHolder.getLocale());
            resultType = ResultType.TYPE_ERROR;
            e.printStackTrace();
        }
        // Notification bar
        NotificationBarBuilder nb = new NotificationBarBuilder();
        nb.setAll(0, userId, message, false, resultType);
        nb.setAlink(alink);
        NotificationBar n = nb.build(true);
        notificationBarService.saveOrUpdate(n);
    }

    /**
     * Arquivo CSV Padrao
     */
    @Override
    public void createIssueCsvFile(long userId, String userMunicipality) {
        // Csv
        final String foldername = userId + "-" + UUID.randomUUID().toString();
        String separator = System.getProperty("file.separator");
        String rootPath = servletContext.getRealPath(Config.PRODUCTION_RESOURCES_FILES) + separator;
        String path = rootPath + foldername;
        String alink = Config.defaultAlink;
        Gson gson = new Gson();
        directory = new File(path);
        if (!directory.exists() && directory.mkdir()) {
            try {
                String csvFile = path + separator + "ISSUES.csv";
                FileWriter writer = new FileWriter(csvFile);
                // for header
                CSVUtils.writeLine(writer,
                        Arrays.asList("id", "comment", "negativeScore", "longitude", "latitude", "accuracy", "provider",
                                "altitude", "horizontalAccuracy", "verticalAccuracy", "course", "speed", "floor",
                                "creationDate", "updateDate", "editDate", "queryAt", "sampleTimestamp",
                                "dataReceivedTimestamp", "User Id", "Name", "Username", "Email", "isGuest",
                                "progenitorId", "Device", "Files", "category", "subcategory"));
                //Rows
                List<IssueReport> data = new ArrayList<>();
                // Municipality
                if (!Validator.isEmptyString(userMunicipality)) {
                    data = issueReportService.fetchAll(userMunicipality);
                } else {
                    data = issueReportService.fetchAll();
                }
                if (data.size() > 0) {
                    for (IssueReport d : data) {
                        List<String> list = new ArrayList<String>();

                        list.add(String.valueOf(d.getId()));
                        list.add(d.getComment());
                        list.add(String.valueOf(d.getNegativeScore()));

                        list.add(String.valueOf(d.getLongitude()));
                        list.add(String.valueOf(d.getLatitude()));
                        list.add(String.valueOf(d.getAccuracy()));
                        list.add(String.valueOf(d.getProvider()));
                        list.add(String.valueOf(d.getAltitude()));
                        list.add(String.valueOf(d.getHorizontalAccuracy()));
                        list.add(String.valueOf(d.getVerticalAccuracy()));
                        list.add(String.valueOf(d.getCourse()));
                        list.add(String.valueOf(d.getSpeed()));
                        list.add(String.valueOf(d.getFloor()));

                        list.add(String.valueOf(d.getCreationDate()));
                        list.add(String.valueOf(d.getUpdateDate()));
                        list.add(String.valueOf(d.getEditDate()));
                        list.add(String.valueOf(d.getQueryAt()));
                        list.add(String.valueOf(d.getSampleTimestamp()));
                        list.add(String.valueOf(d.getDataReceivedTimestamp()));

                        User u = d.getUser();
                        list.add(String.valueOf(u.getId()));
                        list.add(String.valueOf(u.getName()));
                        list.add(String.valueOf(u.getOfficialEmail()));
                        list.add(String.valueOf(u.getOfficialEmail()));
                        list.add(String.valueOf(u.isGuest()));
                        list.add(String.valueOf(u.getProgenitorId()));
                        list.add(String.valueOf(u.getDevice()));

                        List<StorageFile> storages = d.getFiles();
                        ArrayList<String> files = new ArrayList<String>();
                        if (storages != null && storages.size() > 0) {
                            for (StorageFile s : storages) {
                                files.add(s.getAssetUrl());
                            }
                        }
                        list.add(gson.toJson(files));
                        list.add(d.getSubcategory().getCategory().getName());
                        list.add(d.getSubcategory().getName());

                        CSVUtils.writeLine(writer, list);
                    }
                }
                writer.flush();
                writer.close();
                // Zip
                String zipFileName = foldername + ".zip";
                String zipFilePath = rootPath + zipFileName;
                String zipDirPath = path;
                ZipUtils.zipDir(zipFilePath, zipDirPath);
                // Ready
                alink = Config.PRODUCTION_HOST + Config.PRODUCTION_RESOURCES_FILES + separator + zipFileName;
                System.out.println(alink);
                message = messageSource.getMessage("confirmation.download", new Object[]{alink},
                        LocaleContextHolder.getLocale());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                message = messageSource.getMessage("download.error.written", null, LocaleContextHolder.getLocale());
                resultType = ResultType.TYPE_ERROR;
                e.printStackTrace();
            }
            // Notification bar
            NotificationBarBuilder nb = new NotificationBarBuilder();
            nb.setAll(0, userId, message, false, resultType);
            nb.setAlink(alink);
            NotificationBar n = nb.build(true);
            notificationBarService.saveOrUpdate(n);
        } else {
            System.out.println(path);
        }
    }

    @Override
    public void createPrettyCSV(ListMultimap<String, Object> params, long userId) {
        try {
            Gson gson = new Gson();
            String cmd = String.format(Config.PYTHON_SCRIPT_EXCEL_EXPORT, gson.toJson(params.asMap()), userId);
            System.out.println(cmd);
            Process p = Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            // Notification bar
            NotificationBarBuilder nb = new NotificationBarBuilder();
            nb.setAll(0, userId, e.getMessage(), false, ResultType.TYPE_ERROR);
            NotificationBar n = nb.build(true);
            notificationBarService.saveOrUpdate(n);
        }
    }
}