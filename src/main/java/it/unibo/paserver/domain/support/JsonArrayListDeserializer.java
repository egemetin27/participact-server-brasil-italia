package it.unibo.paserver.domain.support;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.gson.Gson;

public class JsonArrayListDeserializer extends JsonDeserializer<String> {
	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Gson gson = new Gson();  
		return gson.toJson(jp);  
	}
}
