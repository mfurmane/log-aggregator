package mfurmane.log.aggregator.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;

import mfurmane.log.aggregator.dto.Log;

class LogParserTest {

	private String applicationName = "application";
	private String myLogFormat = "2021-10-01 10:58:56.012  INFO 2060 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.";
	private String noThreadFormat = "2021-10-01 10:58:56  INFO com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.";
	private String noDateFormat = "10:58:56  INFO com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.";
	private String noTimeAndClassFormat = "2021-10-01  INFO : HikariPool-1 - Start completed.";
	private String strangeFormat = "INFO : com.zaxxer.hikari.HikariDataSource 2021-10-01 - HikariPool-1 - Start completed.";

	LogParser parser = new LogParser();

	@Test
	void testMyLogFormat() {
		List<Log> logList = parser.parse(myLogFormat, applicationName);
		assertEquals(1, logList.size());
		Log log = logList.get(0);
		assertEquals(applicationName, log.getApplication());
		assertEquals(Level.INFO, log.getLoggingLevel());
		assertEquals("com.zaxxer.hikari.HikariDataSource", log.getSourceClass());
		assertEquals(LocalTime.parse("10:58:56.012"), log.getTime());
		assertEquals(LocalDate.parse("2021-10-01"), log.getDate());
		assertEquals("2060 --- [ main] : HikariPool-1 - Start completed.", log.getContent());
	}

	@Test
	void testNoThreadFormat() {
		List<Log> logList = parser.parse(noThreadFormat, applicationName);
		Log log = logList.get(0);
		assertEquals(LocalTime.parse("10:58:56"), log.getTime());
		assertEquals(LocalDate.parse("2021-10-01"), log.getDate());
		assertEquals("HikariPool-1 - Start completed.", log.getContent());
	}

	@Test
	void testNoDateFormat() {
		List<Log> logList = parser.parse(noDateFormat, applicationName);
		Log log = logList.get(0);
		assertEquals(LocalTime.parse("10:58:56"), log.getTime());
		assertEquals(null, log.getDate());
		assertEquals("HikariPool-1 - Start completed.", log.getContent());
	}

	@Test
	void testNoTimeAndClassFormat() {
		List<Log> logList = parser.parse(noTimeAndClassFormat, applicationName);
		Log log = logList.get(0);
		assertEquals(null, log.getSourceClass());
		assertEquals(null, log.getTime());
		assertEquals(LocalDate.parse("2021-10-01"), log.getDate());
		assertEquals("HikariPool-1 - Start completed.", log.getContent());
	}

	@Test
	void testStrangeFormat() {
		List<Log> logList = parser.parse(strangeFormat, applicationName);
		Log log = logList.get(0);
		assertEquals(Level.INFO, log.getLoggingLevel());
		assertEquals("com.zaxxer.hikari.HikariDataSource", log.getSourceClass());
		assertEquals(null, log.getTime());
		assertEquals(LocalDate.parse("2021-10-01"), log.getDate());
		assertEquals("HikariPool-1 - Start completed.", log.getContent());
	}

}
