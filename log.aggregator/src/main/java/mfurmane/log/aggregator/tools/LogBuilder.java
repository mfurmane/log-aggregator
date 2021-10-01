package mfurmane.log.aggregator.tools;

import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.event.Level;

import mfurmane.log.aggregator.dto.Log;

public class LogBuilder {

	private String application;
	private LocalDate date;
	private LocalTime time;
	private Level loggingLevel;
	private String sourceClass;
	private String content;

	public LogBuilder application(String application) {
		this.application = application;
		return this;
	}

	public LogBuilder date(LocalDate date) {
		this.date = date;
		return this;
	}

	public LogBuilder time(LocalTime time) {
		this.time = time;
		return this;
	}

	public LogBuilder loggingLevel(Level loggingLevel) {
		this.loggingLevel = loggingLevel;
		return this;
	}

	public LogBuilder sourceClass(String sourceClass) {
		this.sourceClass = sourceClass;
		return this;
	}

	public LogBuilder content(String content) {
		this.content = content;
		return this;
	}

	public Log build() {
		return new Log(null, application, date, time, loggingLevel, sourceClass, content);
	}

}
