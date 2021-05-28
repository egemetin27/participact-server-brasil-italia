package it.unibo.paserver.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;

public class PALocalDateFormatter implements Formatter<LocalDate> {

	private static final DateTimeFormatter dtf = DateTimeFormat
			.forPattern("yyyy-MM-dd");

	@Override
	public String print(LocalDate arg0, Locale arg1) {
		return dtf.print(arg0);
	}

	@Override
	public LocalDate parse(String arg0, Locale arg1) throws ParseException {
		return dtf.parseLocalDate(arg0);
	}
}
