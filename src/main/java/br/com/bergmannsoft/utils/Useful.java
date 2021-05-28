package br.com.bergmannsoft.utils;

import br.com.bergmannsoft.config.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.opencsv.CSVWriter;
import it.unibo.paserver.domain.*;
import it.unibo.paserver.domain.format.ActionQuestionaireFormat;
import it.unibo.paserver.domain.format.TaskFormat;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Classe para funcoes comuns
 *
 * @author Claudio
 */
@SuppressWarnings("Duplicates")
public class Useful {
    private static BufferedReader br;

    /**
     * Realiza um post via json
     *
     * @param gObj
     * @param url
     * @return
     */
    @SuppressWarnings("finally")
    public static JsonObject makeJsonRequest(JsonObject gObj, String url) {
        // Default return
        JsonObject rs = new JsonObject();
        // Request
        HttpRequestWithBody request = Unirest.post(url).header("content-type", "application/json;charset=UTF-8").header("accept", "application/json").header("cache-control", "no-cache");
        // Json to String
        // Serialization
        Gson gson = new Gson();
        String json = gson.toJson(gObj);
        request.body(json);
        // Response
        HttpResponse<JsonNode> response;
        try {
            response = request.asJson();
            if (response.getCode() == HttpServletResponse.SC_OK) {
                // Deserialization
                String body = response.getBody().toString();
                JsonParser par = new JsonParser();
                JsonElement elm = par.parse(body);
                rs = elm.getAsJsonObject();
            }
        } catch (Exception e) {
            
            e.printStackTrace();
        } finally {
            return rs;
        }
    }

    /**
     * Resposta padrao
     *
     * @param status
     * @param data
     * @param b
     * @param j
     * @param i
     * @param long1
     * @return
     */
    public static @ResponseBody
    ResponseMessage printingJson(Boolean status, String data, Boolean b, int i, int j, Long long1) {

        ResponseMessage response = new ResponseMessage();
        response.setResultCode(200);
        response.setProperty("status", status.toString());
        response.setProperty("outcome", b + "");
        response.setProperty("data", data);
        response.setProperty("count", i + "");
        response.setProperty("offset", j + "");
        response.setProperty("total", long1 + "");
        return response;
    }

    /**
     * Se estring for null, troca para empty
     *
     * @param str
     * @return
     */
    public static String replaceNull(String str) {
        return str == null || str == "null" ? "" : str;
    }

    /**
     * Remove todos os caracteres nao numerico
     *
     * @param str
     * @return
     */
    public static String removeAllNonNumeric(String str) {
        if (str != null && str != "") {
            return str.replaceAll("[^\\d]", "");
        }
        return "";
    }

    /**
     * Converte uma String para long
     *
     * @param str
     * @return
     */
    public static long convertStringToLong(String str) {
        String res = Useful.removeAllNonNumeric(str);
        if (res == null || res == "" || Validator.isEmptyString(res)) {
            return 0;
        }
        return Validator.isValidNumeric(res) ? Long.parseLong(res) : 0;
    }

    /**
     * Obtem as crecendias de um HTTP basic authentication
     *
     * @param request
     * @return
     */
    public static String[] getCredentialsFromAuthBasic(HttpServletRequest request) {
        // Response
        String[] credentials = null;
        // Getter
        final String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Basic")) {
            // Authorization: Basic base64credentials
            String encoded = header.substring("Basic".length()).trim();
            String decoded = new String(Base64.decodeBase64(encoded));
            // credentials = username:password
            credentials = decoded.split(":", 2);
        }

        return credentials;
    }

    /**
     * Obtem a senha de um HTTP basic authentication
     *
     * @param request
     * @return
     */
    public static String getPasswordFromAuthBasic(HttpServletRequest request) {
        String[] credentials = getCredentialsFromAuthBasic(request);
        return credentials.length == 2 ? credentials[1] : "";
    }

    /**
     * String para minuscula
     *
     * @param str
     * @return
     */
    public static String toLowerCase(String str) {
        return (str != null) ? str.toLowerCase() : "";
    }

    /**
     * Ordenada um Hashmap pelas chaves
     *
     * @param map
     * @param ascending, 1 Ascending order, -1 Descending order
     * @return
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortMapValuesByKey(final Map<K, V> map, int ascending) {
        Comparator<K> valueComparator = new Comparator<K>() {
            private int ascending;

            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0)
                    return 1;
                else
                    return ascending * compare;
            }

            public Comparator<K> setParam(int ascending) {
                this.ascending = ascending;
                return this;
            }
        }.setParam(ascending);

        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    /**
     * Hash uma string para para SHA-256
     *
     * @param text
     * @param salt
     * @return
     */
    public static String hashed(String text, String salt) {
        try {
            // Define hash algorithm
            MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("sha-256");
            return encoder.encodePassword(text, salt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retorna uma string gerada aleatoriamente
     *
     * @return
     */
    public static String secureRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(128, secureRandom).toString(Character.MAX_RADIX);
    }

    /**
     * Convert a string based locale into a Locale Object. Assumes the string has
     * form "{language}_{country}_{variant}". Examples: "en", "de_DE", "_GB",
     * "en_US_WIN", "de__POSIX", "fr_MAC"
     *
     * @param localeString The String
     * @return the Locale
     * @see '//www.java2s.com/Code/Java/Network-Protocol/GetLocaleFromString.htm'
     */
    public static Locale getLocaleFromString(String localeString) {
        if (localeString == null) {
            localeString = Config.PRODUCTION_LANG;
        }
        localeString = localeString.trim();
        if (localeString.toLowerCase().equals("default")) {
            return Locale.getDefault();
        }

        // Extract language
        int languageIndex = localeString.indexOf('_');
        String language = null;
        if (languageIndex == -1) {
            // No further "_" so is "{language}" only
            return new Locale(localeString, "");
        } else {
            language = localeString.substring(0, languageIndex);
        }

        // Extract country
        int countryIndex = localeString.indexOf('_', languageIndex + 1);
        String country = null;
        if (countryIndex == -1) {
            // No further "_" so is "{language}_{country}"
            country = localeString.substring(languageIndex + 1);
            return new Locale(language, country);
        } else {
            // Assume all remaining is the variant so is
            // "{language}_{country}_{variant}"
            country = localeString.substring(languageIndex + 1, countryIndex);
            String variant = localeString.substring(countryIndex + 1);
            return new Locale(language, country, variant);
        }
    }

    /**
     * @param res
     * @return
     * @throws IOException
     */
    public static String getStringFromResource(Resource res) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(res.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error while closing resource");
                }
            }
        }
    }

    /**
     * Converte um arquivo para base64
     *
     * @param filename
     * @return
     */
    public static String encodeFileToBase64Binary(String filename) {
        File originalFile = new File(filename);
        String encodedBase64 = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            byte[] bytes = new byte[(int) originalFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedBase64;
    }

    /**
     * Obte a string de um arquivo com o get file
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static String getFile(String fileName) throws FileNotFoundException {
        StringBuilder result = new StringBuilder("");
        // Get file from resources folder
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            result.append(line).append("\n");
        }
        scanner.close();
        return result.toString();
    }

    /**
     * Leitura de Arquivo
     *
     * @param filename
     * @return
     */
    public static String readFile(String filename) {
        String result = "";
        try {
            // br = new BufferedReader(new FileReader(filename));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "ISO-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Converte uma string para um nome padrao de coluna
     *
     * @param str
     * @return
     */
    public static String convertStringColumnName(String str) {
        
        if (!Validator.isEmptyString(str)) {
            try {
                String caps = WordUtils.capitalize(str);
                String trim = caps.replaceAll("\\s+", "");
                String res = Character.toLowerCase(trim.charAt(0)) + trim.substring(1);
                return res;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return null;
    }

    /**
     * Retorna a diferenca em segundos entre duas datas
     *
     * @param start
     * @param deadline
     * @param str
     * @return
     */
    public static long getDateDifferent(String start, String deadline, String str) {
        
        SimpleDateFormat format = new SimpleDateFormat(str);
        Date d1 = null;
        Date d2 = null;
        long diffSeconds = 0;
        try {
            d1 = format.parse(start);
            d2 = format.parse(deadline);
            // in milliseconds
            long diff = d2.getTime() - d1.getTime();
            diffSeconds = diff / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffSeconds;
    }

    /**
     * Converte uma string em BR para formato US
     *
     * @param dt
     * @return
     */
    public static String getDateUSFromBR(String dt) {
        Date initDate = null;
        try {
            initDate = new SimpleDateFormat("dd/MM/yyyy").parse(dt);
        } catch (ParseException e) {
            
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(initDate);
    }

    /**
     * Converte uma string para datetime
     *
     * @param dt
     * @return
     */
    public static DateTime converteStringToDate(String dt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.parseDateTime(dt);
    }

    /**
     * Converte uma data para String
     *
     * @param dt
     * @param pattern
     * @return
     */
    public static String converteDateToString(Date dt, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        return formatter.format(dt);
    }

    /**
     * Converte uma string para uma Date de acordo com a pattern
     *
     * @param dt
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date converteStringToDatePattern(String dt, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        return format.parse(dt);
    }

    /**
     * Converte uma string para Date sem a timezone
     *
     * @param dt
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDatePattern(String dt, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dt);
    }

    /**
     * Retorna os meses entre duas datas
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<DateTime> getMonthsBetweenDates(DateTime startDate, DateTime endDate) {
        List<DateTime> res = new ArrayList<DateTime>();
        while (startDate.isBefore(endDate)) {
            startDate = startDate.plusMonths(1);
            res.add(startDate);
        }
        // Return
        return res;
    }

    /**
     * Converte bytes para image file
     *
     * @param bytes
     * @param filename
     */
    public static void convertByteArrayToImage(byte[] bytes, String filename) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
        // ImageIO is a class containing static methods for locating
        // ImageReaders
        // and ImageWriters, and performing simple encoding and decoding.
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis;
        try {
            iis = ImageIO.createImageInputStream(source);

            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();

            Image image = reader.read(0, param);
            // got an image file

            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // bufferedImage is the RenderedImage to be written

            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, null, null);

            File imageFile = new File(filename);
            ImageIO.write(bufferedImage, "jpg", imageFile);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    /**
     * Converte bytes para image file
     *
     * @param bytes
     * @param filename
     */
    public static void convertByteArrayToImage(byte[] bytes, String filename, String suffix) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName(suffix);
        // ImageIO is a class containing static methods for locating
        // ImageReaders
        // and ImageWriters, and performing simple encoding and decoding.
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis;
        try {
            iis = ImageIO.createImageInputStream(source);

            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();

            Image image = reader.read(0, param);
            // got an image file

            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // bufferedImage is the RenderedImage to be written

            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, null, null);

            File imageFile = new File(filename);
            ImageIO.write(bufferedImage, suffix, imageFile);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    /**
     * formato por array
     *
     * @param bt
     * @return
     * @throws IOException
     */
    public static String getFileSuffix(byte[] bt) throws IOException {
        String result = "jpg";
        String hex = bytesToHexString(bt).substring(0, 4);
        //System.out.println(hex);
        if (hex.equals("ffd8")) {
            result = "jpg";
        } else if (hex.equals("4749")) {
            result = "gif";
        } else if (hex.equals("8950")) {
            result = "png";
        }

        return result;
    }

    /**
     * Get Suffix
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Check format img file
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public static Boolean isJPEG(File filename) throws Exception {
        DataInputStream ins = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
        try {
            if (ins.readInt() == 0xffd8ffe0) {
                return true;
            } else {
                return false;

            }
        } finally {
            ins.close();
        }
    }

    /**
     * Conversao de idioma padrao de acordo com o action type
     *
     * @param a
     * @return
     */
    public static String getTranslatedActionType(Action a) {
        String keyword = "type";
        if (a.getType().name().equals(ActionType.ACTIVITY_DETECTION.name())) {
            keyword = ActionType.ACTIVITY_DETECTION.name().toLowerCase();
        } else if (a.getType().name().equals(ActionType.PHOTO.name())) {
            keyword = ActionType.PHOTO.name().toLowerCase();
        } else if (a.getType().name().equals(ActionType.QUESTIONNAIRE.name())) {
            keyword = ActionType.QUESTIONNAIRE.name().toLowerCase();
        } else if (a.getType().name().equals(ActionType.SENSING_MOST.name())) {
            ActionSensing type = (ActionSensing) a;
            keyword = type.getPipelineType().name().toLowerCase();
        }
        return keyword;
    }

    /**
     * Retorna uma Action String mappeada para CSV
     *
     * @param a
     * @return
     */
    public static String getActionValueAsString(Action a) {
        String res = "";
        // Mapper
        try {
            CsvMapper actionMapper = new CsvMapper();
            if (a.getType().name().equals(ActionType.ACTIVITY_DETECTION.name())) {
                List<ActionActivityDetection> listOf = new ArrayList<ActionActivityDetection>();
                listOf.add((ActionActivityDetection) a);
                CsvSchema actionSchema = actionMapper.schemaFor(ActionActivityDetection.class).withHeader().withNullValue("NULL").withoutEscapeChar().withColumnSeparator(';');
                res = actionMapper.writer(actionSchema).writeValueAsString(listOf);
            } else if (a.getType().name().equals(ActionType.PHOTO.name())) {
                List<ActionPhoto> listOf = new ArrayList<ActionPhoto>();
                listOf.add((ActionPhoto) a);
                CsvSchema actionSchema = actionMapper.schemaFor(ActionPhoto.class).withHeader().withNullValue("NULL").withoutEscapeChar().withColumnSeparator(';');
                res = actionMapper.writer(actionSchema).writeValueAsString(listOf);

            } else if (a.getType().name().equals(ActionType.QUESTIONNAIRE.name())) {
                List<ActionQuestionaire> listOf = new ArrayList<ActionQuestionaire>();
                listOf.add((ActionQuestionaire) a);
                CsvSchema actionSchema = actionMapper.schemaFor(ActionQuestionaire.class).withHeader().withNullValue("NULL").withoutEscapeChar().withColumnSeparator(';');
                actionMapper.addMixInAnnotations(ActionQuestionaire.class, ActionQuestionaireFormat.class);
                res = actionMapper.writer(actionSchema).writeValueAsString(listOf);

            } else if (a.getType().name().equals(ActionType.SENSING_MOST.name())) {
                List<ActionSensing> listOf = new ArrayList<ActionSensing>();
                listOf.add((ActionSensing) a);
                CsvSchema actionSchema = actionMapper.schemaFor(ActionSensing.class).withHeader().withNullValue("NULL").withoutEscapeChar().withColumnSeparator(';');
                res = actionMapper.writer(actionSchema).writeValueAsString(listOf);
            }
        } catch (JsonProcessingException e) {
            
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Mapeamento de valores dos IssueReport
     *
     * @param ir
     * @return
     */
    public static String getIssueReportValueAsString(IssueReport ir) {
        String res = "";
        // Mapper
        try {
            CsvMapper issueMapper = new CsvMapper();
            List<IssueReport> listOf = new ArrayList<IssueReport>();
            listOf.add(ir);
            CsvSchema actionSchema = issueMapper.schemaFor(IssueReport.class).withHeader().withNullValue("NULL").withoutEscapeChar();
            res = issueMapper.writer(actionSchema).writeValueAsString(listOf);
        } catch (JsonProcessingException e) {
            
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Padronizacao do CSV para Tarefas
     *
     * @param listOfTasks
     * @return
     * @throws JsonProcessingException
     */
    @SuppressWarnings("deprecation")
    public static String getTaskValueAsString(List<Task> listOfTasks) throws JsonProcessingException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Task.class).withHeader().withNullValue("NULL").withoutEscapeChar();
        // mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.addMixInAnnotations(Task.class, TaskFormat.class);
        return mapper.writer(schema).writeValueAsString(listOfTasks);
    }

    public static void getIssueReportValueAsString(List<IssueReport> data, String className, CSVWriter writer, boolean b) {
        

    }

    /**
     * Retorna uma Data/object String mappeada para CSV
     *
     * @param items
     * @param className
     * @param writer
     * @param hasHeader
     * @return
     */
    public static List<String[]> getDataValueAsString(List<? extends Data> items, String className, CSVWriter writer, boolean hasHeader) {
        List<Object[]> listOf = getDataToObject(items, className);
        List<String[]> rows = new ArrayList<String[]>();

        if (!listOf.isEmpty() && listOf.size() > 0) {
            String[] header = new String[listOf.get(0).length];
            header[0] = "ID";
            header[1] = "USER ID";
            header[2] = "OFFICIALEMAIL";
            header[3] = "TIMESTAMP";
            // Especifico
            if (className.equals(DataAccelerometer.class.getName())) {
                header[4] = "X";
                header[5] = "Y";
                header[6] = "Z";
            } else if (className.equals(DataMagneticField.class.getName())) {
                header[4] = "X";
                header[5] = "Y";
                header[6] = "Z";
            } else if (className.equals(DataGyroscope.class.getName())) {
                header[4] = "X";
                header[5] = "Y";
                header[6] = "Z";
            } else if (className.equals(DataAccelerometerClassifier.class.getName()) || className.equals(DataLight.class.getName())) {
                header[4] = "Value";
            } else if (className.equals(DataActivityRecognitionCompare.class.getName())) {
                header[4] = "Google Ar Confidence";
                header[5] = "Google Ar Timestamp";
                header[6] = "Google Ar Value";
                header[7] = "Most Ar Timestamp";
                header[8] = "Most Ar Value";
                header[9] = "User Activity";
            } else if (className.equals(DataAppNetTraffic.class.getName())) {
                header[4] = "Name";
                header[5] = "Rx Bytes";
                header[6] = "Tx Bytes";
            } else if (className.equals(DataAppOnScreen.class.getName())) {
                header[4] = "App Name";
                header[5] = "Start Time";
                header[6] = "End Time";
            } else if (className.equals(DataBattery.class.getName())) {
                header[4] = "Level";
                header[5] = "Scale";
                header[6] = "Temperature";
                header[7] = "Voltage";
                header[8] = "Plugged";
                header[9] = "Status";
                header[10] = "Health";
            } else if (className.equals(DataCell.class.getName())) {
                header[4] = "Gsm Cell Id";
                header[5] = "Gsm Lac";
                header[6] = "Base Station Id";
                header[7] = "Base Station Latitude";
                header[8] = "Base Station Longitude";
                header[9] = "Base Network Id";
                header[10] = "Base System Id";
            } else if (className.equals(DataConnectionType.class.getName())) {
                header[4] = "Connection Type";
                header[5] = "Mobile Network Type";
            } else if (className.equals(DataDeviceNetTraffic.class.getName())) {
                header[4] = "Rx Bytes";
                header[5] = "Tx Bytes";
            } else if (className.equals(DataDR.class.getName())) {
                header[4] = "State";
                header[5] = "Pole";
                header[6] = "Latitude";
                header[7] = "Longitude";
                header[8] = "Accuracy";
                header[9] = "Status";
            } else if (className.equals(DataGoogleActivityRecognition.class.getName())) {
                header[4] = "Classifier value";
                header[5] = "Confidence";
            } else if (className.equals(DataInstalledApps.class.getName())) {
                header[4] = "PkgName";
                header[5] = "Version Code";
                header[6] = "Version Name";
            } else if (className.equals(DataLocation.class.getName())) {
                // header[2] = "NAME";
                // header[3] = "SURNAME";
                header[3] = "TIMESTAMP";
                header[4] = "Latitude";
                header[5] = "Longitude";
                header[6] = "Accuracy";
                header[7] = "Provider";
            } else if (className.equals(DataPhoneCallDuration.class.getName())) {
                header[4] = "Call Start";
                header[5] = "Call End";
                header[6] = "Is Incoming";
                header[7] = "PhoneNumber";
            } else if (className.equals(DataPhoneCallEvent.class.getName())) {
                header[4] = "Is Incoming Call";
                header[5] = "Is Start";
                header[6] = "PhoneNumber";
            } else if (className.equals(DataSystemStats.class.getName())) {
                header[4] = "BOOT_TIME";
                header[5] = "CONTEXT_SWITCHES";
                header[6] = "CPU_FREQUENCY";

                header[7] = "CPU_HARDIRQ";
                header[8] = "CPU_IDLE";
                header[9] = "CPU_IOWAIT";

                header[10] = "CPU_NICED";
                header[11] = "CPU_SOFTIRQ";
                header[12] = "CPU_SYSTEM";

                header[13] = "CPU_USER";
                header[14] = "MEM_ACTIVE";
                header[15] = "MEM_FREE";

                header[16] = "MEM_INACTIVE";
                header[17] = "MEM_TOTAL";
            } else if (className.equals(DataWifiScan.class.getName())) {
                header[4] = "Bssid";
                header[5] = "Ssid";
                header[6] = "Capabilities";

                header[7] = "Frequency";
                header[8] = "Level";
            } else if (className.equals(DataPhoto.class.getName())) {
                header[4] = "Id";
                header[5] = "Width";
                header[6] = "Height";
            }
            // Build
            for (Object[] item : listOf) {
                List<Object> nextLine = Lists.newArrayList(item);
                String[] row = new String[nextLine.size()];
                int index = 0;
                for (Object value : nextLine) {
                    row[index] = value != null ? (String) value.toString() : "";
                    index++;
                }
                rows.add(row);
            }
            // Criando
            try {
                if (hasHeader) {
                    writer.writeNext(header);
                    // System.out.println(header.toString());
                }
                writer.writeAll(rows);
                writer.close();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
        }
        // Retorno
        return rows;
    }

    /**
     * Converte um data question object em uma string padrao
     *
     * @param listOf
     * @param writer
     */
    public static void getQuestionValueAsString(List<Object[]> listOf, CSVWriter writer) {
        List<String[]> rows = new ArrayList<String[]>();
        if (!listOf.isEmpty() && listOf.size() > 0) {
            String[] header = {"Question ID", "Title", "Description", "USER ID", "Name", "Surname", "Username", "Answer ID", "Answer", "Date Answer"};
            // Build
            for (Object[] item : listOf) {
                List<Object> nextLine = Lists.newArrayList(item);
                String[] row = new String[nextLine.size()];
                int index = 0;
                for (Object value : nextLine) {
                    row[index] = value != null ? (String) value.toString() : "";
                    index++;
                }
                rows.add(row);
            }
            // Criando
            try {
                writer.writeNext(header);
                writer.writeAll(rows);
                writer.close();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
        }
        return;
    }

    /**
     * Retorna o agrupador default de um data object
     *
     * @param className
     * @return
     */
    public static String getDataGroupBy(String className) {
        String str = "name";
        if (className.equals(DataAppOnScreen.class.getName())) {
            str = "appName";
        }
        // Return
        return str;
    }

    /**
     * Preformata os parametros de uma consulta avancada
     *
     * @param res
     * @param params
     * @return
     */
    public static ListMultimap<String, Object> getDataQueryParameters(ReceiveAdvancedSearch[] res, ListMultimap<String, Object> params, String className) {
        if (res != null && res.length > 0) {
            // Loop validacao
            for (ReceiveAdvancedSearch item : res) {
                // Validacao de existencia
                if (className.equals(DataAppOnScreen.class.getName())) {
                    if (!Validator.isEmptyString(item.getValue())) {
                        params.put(getDataGroupBy(className), item.getValue());
                    }
                }
            }
        }
        // Return
        return params;
    }

    /**
     * Converte um data object para um generic object[]
     *
     * @param items
     * @param className
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<Object[]> getDataToObject(List<? extends Data> items, String className) {
        List<Object[]> res = new ArrayList<Object[]>();
        for (Data item : items) {
            // Padrao
            ArrayList data = new ArrayList();
            data.add(item.getId());
            data.add(item.getUser().getId());
            data.add(item.getUser().getOfficialEmail());
            // data.add(item.getUser().getSurname());
            // data.add(item.getDataReceivedTimestamp());
            data.add(item.getSampleTimestamp());
            // Especifica
            if (className.equals(DataAccelerometer.class.getName())) {
                // Object
                DataAccelerometer e = (DataAccelerometer) item;
                // Array
                data.add(e.getX());
                data.add(e.getY());
                data.add(e.getZ());
                res.add(data.toArray());
            } else if (className.equals(DataAccelerometerClassifier.class.getName())) {
                // Object
                DataAccelerometerClassifier e = (DataAccelerometerClassifier) item;
                // Array
                data.add(e.getValue());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataActivityRecognitionCompare.class.getName())) {
                // Object
                DataActivityRecognitionCompare e = (DataActivityRecognitionCompare) item;
                // Array
                data.add(e.getGoogleArConfidence());
                data.add(e.getGoogleArTimestamp());
                data.add(e.getGoogleArValue());
                data.add(e.getMostArTimestamp());
                data.add(e.getMostArValue());
                data.add(e.getUserActivity());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataAppNetTraffic.class.getName())) {
                // Object
                DataAppNetTraffic e = (DataAppNetTraffic) item;
                // Array
                data.add(e.getName());
                data.add(e.getRxBytes());
                data.add(e.getTxBytes());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataAppOnScreen.class.getName())) {
                // Object
                DataAppOnScreen e = (DataAppOnScreen) item;
                // Array
                data.add(e.getAppName());
                data.add(e.getStartTime());
                data.add(e.getEndTime());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataBattery.class.getName())) {
                // Object
                DataBattery e = (DataBattery) item;
                // Array
                data.add(e.getLevel());
                data.add(e.getScale());
                data.add(e.getTemperature());
                data.add(e.getVoltage());
                data.add(e.getPlugged());
                data.add(e.getStatus());
                data.add(e.getHealth());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataCell.class.getName())) {
                // Object
                DataCell e = (DataCell) item;
                // Array
                data.add(e.getGsmCellId());
                data.add(e.getGsmLac());
                data.add(e.getBaseStationId());
                data.add(e.getBaseStationLatitude());
                data.add(e.getBaseStationLongitude());
                data.add(e.getBaseNetworkId());
                data.add(e.getBaseSystemId());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataConnectionType.class.getName())) {
                // Object
                DataConnectionType e = (DataConnectionType) item;
                // Array
                data.add(e.getConnectionType());
                data.add(e.getMobileNetworkType());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataDeviceNetTraffic.class.getName())) {
                // Object
                DataDeviceNetTraffic e = (DataDeviceNetTraffic) item;
                // Array
                data.add(e.getRxBytes());
                data.add(e.getTxBytes());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataDR.class.getName())) {
                // Object
                DataDR e = (DataDR) item;
                // Array
                data.add(e.getState());
                data.add(e.getPole());
                data.add(e.getLatitude());
                data.add(e.getLongitude());
                data.add(e.getAccuracy());
                data.add(e.getStatus());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataGoogleActivityRecognition.class.getName())) {
                // Object
                DataGoogleActivityRecognition e = (DataGoogleActivityRecognition) item;
                // Array
                data.add(e.getClassifier_value());
                data.add(e.getConfidence());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataGyroscope.class.getName())) {
                // Object
                DataGyroscope e = (DataGyroscope) item;
                // Array
                data.add(e.getRotationX());
                data.add(e.getRotationY());
                data.add(e.getRotationZ());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataInstalledApps.class.getName())) {
                // Object
                DataInstalledApps e = (DataInstalledApps) item;
                // Array
                data.add(e.getPkgName());
                data.add(e.getVersionCode());
                data.add(e.getVersionName());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataLight.class.getName())) {
                // Object
                DataLight e = (DataLight) item;
                // Array
                data.add(e.getValue());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataLocation.class.getName())) {
                // Object
                DataLocation e = (DataLocation) item;
                // Array
                data.add(e.getLatitude());
                data.add(e.getLongitude());
                data.add(e.getAccuracy());
                data.add(e.getProvider());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataMagneticField.class.getName())) {
                // Object
                DataMagneticField e = (DataMagneticField) item;
                // Array
                data.add(e.getMagneticFieldX());
                data.add(e.getMagneticFieldY());
                data.add(e.getMagneticFieldZ());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataPhoneCallDuration.class.getName())) {
                // Object
                DataPhoneCallDuration e = (DataPhoneCallDuration) item;
                // Array
                data.add(e.getCallStart());
                data.add(e.getCallEnd());
                data.add(e.getIsIncoming());
                data.add(e.getPhoneNumber());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataPhoneCallEvent.class.getName())) {
                // Object
                DataPhoneCallEvent e = (DataPhoneCallEvent) item;
                // Array
                data.add(e.getIsIncomingCall());
                data.add(e.getIsStart());
                data.add(e.getPhoneNumber());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataSystemStats.class.getName())) {
                // Object
                DataSystemStats e = (DataSystemStats) item;
                // Array
                data.add(e.getBOOT_TIME());
                data.add(e.getCONTEXT_SWITCHES());
                data.add(e.getCPU_FREQUENCY());

                data.add(e.getCPU_HARDIRQ());
                data.add(e.getCPU_IDLE());
                data.add(e.getCPU_IOWAIT());

                data.add(e.getCPU_NICED());
                data.add(e.getCPU_SOFTIRQ());
                data.add(e.getCPU_SYSTEM());

                data.add(e.getCPU_USER());
                data.add(e.getMEM_ACTIVE());
                data.add(e.getMEM_FREE());

                data.add(e.getMEM_INACTIVE());
                data.add(e.getMEM_TOTAL());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataWifiScan.class.getName())) {
                // Object
                DataWifiScan e = (DataWifiScan) item;
                // Array
                data.add(e.getBssid());
                data.add(e.getSsid());
                data.add(e.getCapabilities());

                data.add(e.getFrequency());
                data.add(e.getLevel());
                // add
                res.add(data.toArray());
            } else if (className.equals(DataPhoto.class.getName())) {
                // Object
                DataPhoto e = (DataPhoto) item;
                // Array
                data.add(e.getId());
                data.add(e.getWidth());
                data.add(e.getHeight());
                // Add
                res.add(data.toArray());
            }
        }
        return res;
    }

    /**
     * Retorna o menor divisor comum
     *
     * @param a
     * @param b
     * @return
     */
    public static int getGreatestCommonDivisor(long a, long b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }

    /**
     * Menor muitplicar comum entre dois numeros
     *
     * @return
     */
    public static long getLeastCommonMultiple(long a, long b) {
        if (a >= 0 && b >= 0) {
            long gcd = getLeastCommonMultiple(-a, -b);
            return (a * b) / gcd; // lcm(a,b) * gcd(a,b) == |a*b|
        }
        // here we simulate euclid's gcd()
        a = -a;
        b = -b;
        if (b == 0)
            return a;
        else
            return getLeastCommonMultiple(-b, -(a % b)); // neg -> simulate
        // gcd()
    }

    /**
     * Cores de acordo com o tipo
     *
     * @param key
     * @return
     */
    public static Object getColorValueState(String key) {
        
        String str = "#2ab4c0";
        if (key.equals("AVAILABLE")) {
            str = "#2ab4c0";
        } else if (key.equals("ACCEPTED")) {
            str = "#3598dc";
        } else if (key.equals("RUNNING")) {
            str = "#9A12B3";
        } else if (key.equals("REJECTED")) {
            str = "#d05454";
        } else if (key.equals("COMPLETED")) {
            str = "#525e64";
        } else if (key.equals("NONE")) {
            str = "#f3c200";
        } else if (key.equals("TOTAL")) {
            str = "#E91E63";
        }
        return str;
    }

    /**
     * Retorna uma cor rgb aleatorio
     *
     * @return
     * @see //stackoverflow.com/questions/4246351/creating-random-colour-in-java
     */
    public static Color getRandomColor() {
        Random rand = new Random();
        // Java 'Color' class takes 3 floats, from 0 to 1.
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        return randomColor;
    }

    /**
     * Converte um Color para RGB
     *
     * @param color
     * @return
     */
    public static String toRGB(Color color) {
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }
        return hex;
    }

    /**
     * Retorna um RGB aleatorio
     *
     * @return
     */
    public static String getRandomRGB() {
        return toRGB(getRandomColor());
    }

    /**
     * Percentual sobre o total
     *
     * @param value
     * @param total
     * @return
     */
    public static float getPercent(long value, long total) {
        
        if (total > 0) {
            return round(((float) value * 100) / total, 2);
        }
        return 0L;
    }

    /**
     * Arrendonda um valor
     *
     * @param value
     * @param places
     * @return
     */
    public static float round(float value, int places) {
        if (places > 0) {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.floatValue();
        }
        return 0F;
    }

    /**
     * Converte para UTF8
     *
     * @param str
     * @return
     */
    public static String decodeStringToUTF8(String str) {
        if (!Validator.isEmptyString(str)) {
            try {
                byte[] bytes = str.getBytes();
                return new String(bytes, "UTF-8"); // Charset with which bytes
                // were encoded
            } catch (UnsupportedEncodingException e) {
                
                e.printStackTrace();
            } // Charset to encode into
        }
        return str;
    }

    public static String converetUTF8toISO(String message) {
        Charset utf8charset = Charset.forName("UTF-8");
        Charset iso88591charset = Charset.forName("ISO-8859-1");

        ByteBuffer inputBuffer = ByteBuffer.wrap(message.getBytes());

        // decode UTF-8
        CharBuffer data = utf8charset.decode(inputBuffer);

        // encode ISO-8559-1
        ByteBuffer outputBuffer = iso88591charset.encode(data);
        byte[] outputData = outputBuffer.array();

        return new String(outputData);
    }

    /**
     * Divisao com arrendodamento
     *
     * @param num
     * @param divisor
     * @return
     */
    public static long roundUp(long num, long divisor) {
        int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
    }

    /**
     * Retorna um texto puro de um html
     *
     * @param s
     * @return
     */
    public static String htmlifyPlain(String s) {
        StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\n':
                    builder.append("<br>");
                    break;
                // We need Tab support here, because we print StackTraces as HTML
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    if (c < 128) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int) c).append(";");
                    }
            }
        }
        return builder.toString();
    }

    /**
     * <p>
     * Escapes the characters in a <code>String</code> to be suitable to pass to an
     * SQL query.
     * </p>
     *
     * <p>
     * For example,
     *
     * <pre>
     * statement.executeQuery("SELECT * FROM MOVIES WHERE TITLE='" + StringEscapeUtils.escapeSql("McHale's Navy") + "'");
     * </pre>
     * </p>
     *
     * <p>
     * At present, this method only turns single-quotes into doubled single-quotes
     * (<code>"McHale's Navy"</code> => <code>"McHale''s Navy"</code>). It does not
     * handle the cases of percent (%) or underscore (_) for use in LIKE clauses.
     * </p>
     * <p>
     * see http://www.jguru.com/faq/view.jsp?EID=8881
     *
     * @param str the string to escape, may be null
     * @return a new String, escaped for SQL, <code>null</code> if null string input
     */
    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.replace(str, "'", "''");
    }

    /**
     * This function converts radians to decimal degrees
     *
     * @param rad
     * @return
     */
    public static double rad2deg(double rad) {
        
        return (rad * 180 / Math.PI);
    }

    /**
     * This function converts radians to decimal degrees
     *
     * @param deg
     * @return
     */
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double[] calculateMinMaxValues(double latitude, double longitude, double radius) {
        double[] res = new double[4];
        res[0] /* minLatitude */ = latitude - (radius * 2) / 60.0;
        res[1] /* maxLatitude */ = latitude + (radius * 2) / 60.0;

        res[2] /* minLongitude */ = (longitude * 2) / 60.0 * (Math.cos(res[0]));
        res[3] /* maxLongitude */ = (longitude * 2) / 60.0 * (Math.cos(res[1]));
        return res;
    }

    /**
     * Pausa com sleep
     *
     * @param i, milliseconds
     * @return
     */
    public static boolean uSleep(int i) {
        
        try {
            Thread.sleep(i); // milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return true;
    }

    /**
     * Converte um timestamp para uma data
     *
     * @param time
     * @return
     */
    public static DateTime convertTimestampToDate(Long time) {
        java.sql.Timestamp timeStampDate = new java.sql.Timestamp(time);
        return new DateTime(timeStampDate.getTime());
    }

    /**
     * Converte uma data para timestamp
     *
     * @param date
     * @return
     */
    public static java.sql.Timestamp convertDateToTimestamp(Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    /**
     * Removendo Caracteres Especial
     *
     * @param str
     * @return
     */
    public static String removeAllSpecialChar(String str) {
        CharMatcher Alnum = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).or(CharMatcher.inRange('0', '9')).precomputed();
        return Alnum.retainFrom(str);
    }

    /**
     * Remove toda a acentuação da string substituindo por caracteres simples sem acento.
     */
    public static String unaccent(String src) {
        return Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    /**
     * Formatando data em string para string
     *
     * @param date
     * @param time
     * @return
     */
    public static String dateSystemToDb(String date, String time) {
        String[] dateParts = date.split("/");
        String d = dateParts[0];
        String m = dateParts[1];
        String y = dateParts[2];

        String[] timeParts = time.split(":");
        String hh = timeParts[0];
        String mm = timeParts[1];

        return String.format("%s-%s-%s %s:%s:00", y, m, d, hh, mm);
    }

    /**
     * Formatacao de datas com expressao/split
     *
     * @param date
     * @return
     */
    public static String datetimeSystemToDb(String date) {
        String[] parts = date.split(" ");
        String dt = parts[0];
        String tm = parts[1];

        String[] dateParts = dt.split("/");
        String d = dateParts[0];
        String m = dateParts[1];
        String y = dateParts[2];

        String[] timeParts = tm.split(":");
        String hh = timeParts[0];
        String mm = timeParts[1];

        return String.format("%s-%s-%s %s:%s:00", y, m, d, hh, mm);
    }

    /**
     * Formatando data para o sistema
     *
     * @param date
     * @return
     */
    public static String dateDbToSystem(String date) {
        date = date.replace("T", " ");
        String[] dateParts = date.split(" ");
        dateParts = dateParts[0].split("-");
        String y = dateParts[0];
        String m = dateParts[1];
        String d = dateParts[2];

        return String.format("%s/%s/%s", d, m, y);
    }

    /**
     * Formatando data para o sistema
     *
     * @param time
     * @return
     */
    public static String timeDbToSystem(String time) {
        time = time.replace("T", " ");
        String[] dateParts = time.split(" ");
        dateParts = dateParts[1].split(":");
        String hh = dateParts[0];
        String mm = dateParts[1];

        return String.format("%s:%s", hh, mm);
    }

    /**
     * Imagem de uma URL para base64
     *
     * @param imageURL
     * @return
     */
    public static String encodeUrlFileBase64(String imageURL) {
        try {
            java.net.URL url = new java.net.URL(imageURL);
            InputStream is = url.openStream();
            byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
            return Base64.encodeBase64String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Imagem de uma URL para base64
     *
     * @param imageURL
     * @return
     */
    public static String encodeUrlFileGZipBase64(String imageURL) {
//        try {
//            java.net.URL url = new java.net.URL(imageURL);
//            InputStream is = url.openStream();
//            byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
//            return Base64.encodeBase64String(bytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    public static Object[] appendValue(Object[] obj, Object newObj) {
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();
    }

    /**
     * Java – Check if a String contains a substring
     *
     * @param str
     * @param subString
     * @return
     * @see //www.mkyong.com/java/java-check-if-a-string-contains-another-string/
     */
    public static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }

    /**
     * Scala para calculo no map
     *
     * @param zoom
     * @return float
     */
    public static int getScaleMapsZoom(int zoom) {
        if (zoom == 20) {
            return 1;
        } else if (zoom == 19) {
            return 1;
        } else if (zoom == 18) {
            return 1;
        } else if (zoom == 17) {
            return 2;
        } else if (zoom == 16) {
            return 2;
        } else if (zoom == 15) {
            return 2;
        } else if (zoom == 14) {
            return 6;
        } else if (zoom == 13) {
            return 7;
        } else if (zoom == 12) {
            return 7;
        } else if (zoom == 11) {
            return 8;
        } else if (zoom == 10) {
            return 8;
        } else if (zoom == 9) {
            return 10;
        } else if (zoom == 8) {
            return 20;
        } else if (zoom == 7) {
            return 50;
        } else if (zoom == 6) {
            return 400;
        } else if (zoom == 5) {
            return 1600;
        } else if (zoom == 4) {
            return 3200;
        } else if (zoom == 3) {
            return 4600;
        } else if (zoom <= 2) {
            return 6371;
        } else if (zoom == 0) {
            return 10;
        }
        return 10;
    }
}