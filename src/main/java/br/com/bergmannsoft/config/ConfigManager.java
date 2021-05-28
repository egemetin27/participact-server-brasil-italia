package br.com.bergmannsoft.config;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class ConfigManager {
	private static HashMap<Class<?>, Object> map = new HashMap<Class<?>, Object>();

	public static <T> T getInstance(Class<T> cls) {
		return getInstance(cls, cls.getSimpleName() + ".xml");
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> cls, String filename) {
		Object instance = map.get(cls);
		try {
			if (instance == null) {
				XStream x = new XStream();
				File cfgfile = new File(filename);
				if (cfgfile.exists()) {
					instance = x.fromXML(new FileReader(cfgfile));
				} else {
					instance = cls.newInstance();
					x.toXML(instance, new FileWriter(cfgfile));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) instance;
	}
}
