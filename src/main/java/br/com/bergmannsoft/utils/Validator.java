package br.com.bergmannsoft.utils;

import com.google.common.base.Strings;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.LongValidator;
import org.hibernate.validator.internal.constraintvalidators.EmailValidator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validacoes comuns customizadas
 *
 * @author Claudio
 */
public class Validator {

    /**
     * Se string e vazia, TRUE se sim (ESTA VAZIA) e FALSE se nao
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        if (str == null || str == "") {
            return true;
        }
        String verify = str.replaceAll("\\s+", "");
        if (Strings.isNullOrEmpty(verify)) {
            return true;
        }
        return false;
    }

    /**
     * Se contem um mimetype padrao de csv
     *
     * @param mimeType
     * @return
     */
    public static boolean isValidMimeTypeCSV(String mimeType) {
        return isValidMimeTypeCSVComma(mimeType) || isValidMimeTypeSemicolon(mimeType);
    }

    /**
     * Se a string mimetype eh um csv padrao
     *
     * @param mimeType
     * @return
     */
    public static boolean isValidMimeTypeCSVComma(String mimeType) {
        String[] haystack = {"text/comma-separated-values", "text/csv", "application/csv", "text/anytext"};
        return isValueinArray(haystack, mimeType);
    }

    /**
     * Se a string mimetype eh um csv exporta do excel
     *
     * @param mimeType
     * @return
     */
    public static boolean isValidMimeTypeSemicolon(String mimeType) {
        String[] haystack = {"application/excel", "application/vnd.ms-excel", "application/vnd.msexcel"};
        return isValueinArray(haystack, mimeType);
    }

    /**
     * Se a string mimetype eh imagem
     *
     * @param mimeType
     * @return
     */
    public static boolean isValidMimeTypeImage(String mimeType) {
        String[] haystack = {"image/gif", "image/jpeg", "image/jpg", "image/png", "application/octet-stream"};
        return isValueinArray(haystack, mimeType);
    }

    /**
     * Se a string esta no formato de uma data
     *
     * @param dateString
     * @return
     */
    public static boolean isValidDate(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            df.parse(dateString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validacao de data e formato
     *
     * @param dateToValidate
     * @param dateFromat
     * @return
     */
    public static boolean isValidDateFormat(String dateToValidate, String dateFromat) {
        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);
        try {
            // if not valid, it will throw ParseException
            @SuppressWarnings("unused")
            Date date = sdf.parse(dateToValidate);
        } catch (Exception e) {
            // e.printStackTrace(System.out);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Se eh um digito valido
     *
     * @param str
     * @return
     */
    public static boolean isValidNumeric(String str) {
        if (str == null || str == "") {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Se eh um valido long
     *
     * @param value
     * @return
     */
    public static boolean isValidLong(String value) {
        LongValidator validator = new LongValidator();
        return validator.isValid(value);
    }

    /**
     * Se String eh null/empty
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(Integer str) {
        
        Integer startIn = null;

        try {
            startIn = Integer.valueOf(str.toString());
        } catch (NumberFormatException e) {
        } catch (NullPointerException e) {
        }

        return startIn == null;
    }

    /**
     * Se uma String esta dentro do tamanho permitido
     *
     * @param str
     * @param min
     * @param max
     * @return
     */
    public static boolean isValidStringLength(String str, int min, int max) {
        if (!isEmptyString(str)) {
            String sr = str.trim();
            int sz = sr.length();
            return (sz >= min && sz <= max);
        }
        return false;
    }

    /**
     * Se duas string sao iguals
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isStringEquals(String a, String b) {
        return new String(a).equals(b);
    }

    /**
     * Se uma string contem uma palavra
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static boolean isStringContains(String haystack, String needle) {
        haystack = haystack == null ? "" : haystack;
        needle = needle == null ? "" : needle;
        if (!isStringEquals(haystack, needle)) {
            return haystack.toLowerCase().contains(needle.toLowerCase());
        }
        return true;
    }

    /**
     * Se uma string existe em um array de string
     *
     * @param haystack
     * @param device
     * @return
     */
    public static boolean isStringContains(String[] haystack, String device) {
        for (String str : haystack) {
            if (isStringContains(str, device)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStringinSentence(String search, String sentence) {
        if (sentence.toLowerCase().indexOf(search.toLowerCase()) != -1) {
            return true;
        }
        return false;
    }

    public static boolean isStringinSentence(String[] haystack, String sentence) {
        for (String str : haystack) {
            if (isStringinSentence(str, sentence)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Se o nome do aparelho esta na lista de dispositivos da Apple
     *
     * @param device
     * @return
     */
    public static boolean isAppleiOS(String device) {
        String[] haystack = {"iPhone", "iPad", "iPod"};
        if (!Validator.isEmptyString(device) && (Validator.isStringContains(haystack, device) || Validator.isStringinSentence(haystack, device))) {
            return true;
        }
        return false;
    }

    /**
     * OS do Aparelho
     *
     * @param device
     * @return
     */
    public static String getDeviceOS(String device) {
        String deviceOS = "Unknown";
        if (!isEmptyString(device)) {
            deviceOS = isAppleiOS(device) ? "Apple" : "Google";
        }
        return deviceOS;
    }

    /**
     * Valida se um username esta dentro do padrao esperado
     *
     * @param str
     * @return
     */
    public static boolean isValidUsername(String str) {
        if (!isEmptyString(str)) {
            Pattern pattern = Pattern.compile("^[A-Za-z]+[._-]{0,2}[A-Za-z0-9]+[._-]{0,2}[A-Za-z0-9]+$");
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Se a string esta dentro do formato de uma senha valida/segura
     *
     * @param str
     * @return
     */
    public static boolean isValidPassword(String str) {
        if (!isEmptyString(str)) {
            Pattern pattern = Pattern.compile("^[A-Za-z0-9_@#$%&*?!.]{8,40}$");
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Validacacao de latitude
     *
     * @param lat
     * @return
     */
    public static boolean isValidLatitude(String lat) {
        if (!isEmptyString(lat)) {
            Pattern pattern = Pattern.compile("^-?[0-9]{1,3}(?:\\.[0-9]{1,20})?$");
            Matcher matcher = pattern.matcher(lat);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Validacao de longitude
     *
     * @param lng
     * @return
     */
    public static boolean isValidLongitude(String lng) {
        if (!isEmptyString(lng)) {
            Pattern pattern = Pattern.compile("^-?[0-9]{1,3}(?:\\.[0-9]{1,20})?$");
            Matcher matcher = pattern.matcher(lng);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Se uma string e um email valido
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        // Vazia?
        if (isEmptyString(email)) {
            return false;
        }
        // Valido?
        EmailValidator ev = new EmailValidator();
        return ev.isValid(email, null);
    }

    /**
     * Verifica se um determinado valor existe em um enum
     *
     * @param clazz
     * @param val
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isValueinEnum(Class<? extends Enum> clazz, String val) {
        Object[] arr = clazz.getEnumConstants();
        for (Object e : arr) {
            if (((Enum) e).name().equals(val)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Se um determinado valor existe no array
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static boolean isValueinArray(String[] haystack, String needle) {
        return Arrays.asList(haystack).contains(needle);
    }

    /**
     * Se um determinado valor existe no array
     *
     * @param haystack
     * @param needle
     * @return
     */
    @SuppressWarnings("unlikely-arg-type")
    public static boolean isValueinArray(Integer[] haystack, long needle) {
        return Arrays.asList(haystack).contains(needle);
    }

    /**
     * Se valor existe no array
     *
     * @param haystack
     * @param needle
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isValueinArray(ArrayList haystack, Long needle) {
        return haystack.contains(needle);
    }

    public static boolean isValueinArray(ArrayList<String> haystack, String needle) {
        
        return haystack.contains(needle);
    }

    /**
     * Se um index existe em um array
     *
     * @param index
     * @param nextLine
     * @return
     */
    public static boolean isIndexinArray(int index, String[] nextLine) {
        return (index < nextLine.length) ? true : false;
    }

    /**
     * Se um array contem todos os values em outro array
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static boolean isCollectionInArray(String[] haystack, Collection<String> needle) {
        int found = 0;
        for (String h : haystack) {
            for (String n : needle) {
                if (h.equals(n))
                    found++;
            }
        }

        return found == haystack.length;
    }

    /**
     * Se String eh valido
     *
     * @param color
     * @return
     */
    public static boolean isValidRgbString(String color) {
        Pattern pattern = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
        Matcher matcher = pattern.matcher(color);
        return matcher.matches();
    }

    /**
     * Chega se contem dominio do projeto
     *
     * @param userEmail
     * @return
     */
    public static boolean isPreventEmailProject(String userEmail) {
        
        return userEmail.contains("participact.com") || userEmail.contains("participact.com.br") || userEmail.contains("facebook.com");
    }

    /**
     * Se eh um string em base64
     *
     * @param value
     * @return
     */
    public static boolean isValidBase64(String value) {
        if (StringUtils.isNotEmpty(value)) {
            if (Base64.isBase64(value)) {
                try {
                    String base64String = new String(Base64.decodeBase64(value), "UTF-8");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }
        return false;
    }

    /**
     * Se uma string eh um json valido
     * @param str
     * @return
     */
    public static boolean isValidJson(String str) {
        try {
            new org.json.JSONObject(str);
        } catch (org.json.JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new org.json.JSONArray(str);
            } catch (org.json.JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
