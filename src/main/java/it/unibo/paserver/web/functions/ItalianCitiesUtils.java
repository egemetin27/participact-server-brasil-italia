package it.unibo.paserver.web.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ItalianCitiesUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(ItalianCitiesUtils.class);
	private Map<String, String> codToCity;
	private Map<String, String> codToProv;

	private void init() {
		codToCity = new TreeMap<String, String>();
		codToProv = new TreeMap<String, String>();
		BufferedReader reader = null;
		try {
			DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
			InputStream source = defaultResourceLoader.getResource(
					"META-INF/ita_cities_codes.txt").getInputStream();
			reader = new BufferedReader(new InputStreamReader(source));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(";");
				codToCity.put(data[0], data[1]);
				codToProv.put(data[0], data[2]);
			}
		} catch (IOException e) {
			logger.error("Error while reading cities codes", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("Error while closing cities codes file", e);
				}
			}
		}
	}

	private void checkInit() {
		if (codToCity == null) {
			init();
		}
	}

	public String getBornCity(String cf) {
		if (cf == null || cf.length() != 16) {
			return "_________________________";
		}
		checkInit();
		String code = cf.substring(11, 15).toUpperCase();
		String cityName = codToCity.get(code);
		return cityName != null ? cityName : "_________________________";
	}

	public String getBornCityProv(String cf) {
		if (cf == null || cf.length() != 16) {
			return "__________";
		}
		checkInit();
		String code = cf.substring(11, 15).toUpperCase();
		String cityName = codToProv.get(code);
		return cityName != null ? cityName : "__________";
	}
}
