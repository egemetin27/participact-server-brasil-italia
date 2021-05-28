package it.unibo.paserver.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;

public class PADateTimeFormatter implements Formatter<DateTime> {

	private static final DateTimeFormatter dtf = DateTimeFormat
			.forPattern("yyyy-MM-dd HH:mm");

	@Override
	public String print(DateTime arg0, Locale arg1) {
		return dtf.print(arg0);
	}

	@Override
	public DateTime parse(String arg0, Locale arg1) throws ParseException {
		return dtf.parseDateTime(arg0);
	}
}
