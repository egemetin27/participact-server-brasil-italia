package it.unibo.paserver.domain;

import br.com.bergmannsoft.utils.Validator;
import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Recebe um json
 *
 * @author Claudio
 */
public class ReceiveJson {
    private static final Gson gson = new Gson();
    private String json;
    private JsonElement elm;
    private JsonObject jObj;

    /**
     * Construtor
     */
    public ReceiveJson(String json) {
        if (!Validator.isEmptyString(json) && this.isJSONValid(json)) {
            JsonParser par = new JsonParser();
            JsonElement elm = par.parse(json);
            setjObj(elm.getAsJsonObject());
        } else {
            setjObj(new JsonObject());
        }
    }

    /**
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * @param json the json to set
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * @return the elm
     */
    public JsonElement getElm() {
        return elm;
    }

    /**
     * @param elm the elm to set
     */
    public void setElm(JsonElement elm) {
        this.elm = elm;
    }

    /**
     * @return the jObj
     */
    public JsonObject getjObj() {
        return jObj;
    }

    /**
     * @param jObj the jObj to set
     */
    public void setjObj(JsonObject jObj) {
        this.jObj = jObj;
    }

    /**
     * Se uma chave existe
     *
     * @param key
     * @return
     */
    public boolean has(String key) {
        return jObj.has(key);
    }

    /**
     * Obtem um elemento se existir
     *
     * @param key
     * @return
     */
    public JsonElement get(String key) {
        JsonElement res = null;
        if (this.has(key)) {
            return this.jObj.get(key);
        }
        return res;
    }

    /**
     * Retorna uma String de um atributo do json
     *
     * @param key
     * @return
     */
    public String getAsString(String key) {
        JsonElement res = this.get(key);
        String str = null;
        try {
            str = (res != null) ? res.getAsString() : null;
        } catch (Exception e) {
            // TODO: handle exception
            str = null;
        }
        return str;
    }

    /**
     * Retorna um boolean de um atributo do json
     *
     * @param key
     * @return
     */
    public Boolean getAsBoolean(String key) {
        try {
            JsonElement res = this.get(key);
            return (res != null) ? res.getAsBoolean() : false;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    /**
     * Retorna uma String do tipo long
     *
     * @param key
     * @return
     */
    public long getAsLong(String key) {
        try {
            JsonElement res = this.get(key);
            return (res != null) ? res.getAsLong() : 0L;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0L;
    }

    /**
     * Retorna um double

     * @param key
     * @return
     */
    public double getAsDouble(String key) {
        try {
            JsonElement res = this.get(key);
            return (res != null) ? res.getAsDouble() : 0D;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0D;
    }

    /**
     * Retorna um integer do json
     *
     * @param key
     * @return
     */
    public int getAsInt(String key) {
        try {
            JsonElement res = this.get(key);
            return (res != null) ? res.getAsInt() : 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 0;
    }

    /**
     * Retorna um JsonArray
     *
     * @param key
     * @return
     */
    public JsonArray getAsJsonArray(String key) {
        JsonElement res = this.get(key);
        return (res != null) ? res.getAsJsonArray() : null;
    }

    /**
     * Retorna um Json Object
     *
     * @param key
     * @return
     */
    public JsonObject getAsJsonObject(String key) {
        JsonElement res = this.get(key);
        return (res != null) ? res.getAsJsonObject() : null;
    }


    /**
     * Verifica se um json valido
     *
     * @param jsonInString
     * @return
     */
    public boolean isJSONValid(String jsonInString) {
        try {
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    /**
     * Retorna um array com AdvancedSearch
     *
     * @param key
     * @return
     */
    public ReceiveAdvancedSearch[] getAsAdvancedSearch(String key) {
        JsonArray str = getAsJsonArray(key);
        ReceiveAdvancedSearch[] res = null;
        if (str != null) {
            // Obtendo
            res = gson.fromJson(str, ReceiveAdvancedSearch[].class);
        }
        return res;
    }

    /**
     * Retorna um ListMultimap de um json
     *
     * @param key
     * @return
     */
    public Map<Integer, JsonObject> getAsJsonMap(String key) {
        Map<Integer, JsonObject> res = new HashMap<Integer, JsonObject>();
        JsonArray str = getAsJsonArray(key);
        Integer count = 0;
        if (str != null) {
            for (JsonElement item : str) {
                if (isJSONValid(item.toString())) {
                    res.put(count, item.getAsJsonObject());
                    count++;
                }
            }
        }
        return res;
    }
}
