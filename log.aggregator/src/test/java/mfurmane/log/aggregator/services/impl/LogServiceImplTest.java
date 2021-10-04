package mfurmane.log.aggregator.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import mfurmane.log.aggregator.dto.Log;
import mfurmane.log.aggregator.repositories.LogRepository;
import mfurmane.log.aggregator.tools.LogParser;

@ExtendWith(MockitoExtension.class)
class LogServiceImplTest {

	String logsAdded = "Logs added: 1";
	String app = "app";
	Pageable pageable = PageRequest.of(0, 5);
	LocalTime startTime = LocalTime.now();
	LocalTime endTime = LocalTime.now();
	LocalDate startDate = LocalDate.now();
	LocalDate endDate = LocalDate.now();
	List<Log> logs = new ArrayList<>();
	Log log = new Log();

	LogServiceImpl service = new LogServiceImpl();

	@Mock
	LogParser parser;
	@Mock
	LogRepository repository;

	@BeforeEach
	void prepareService() {
		service.setParser(parser);
		service.setRepository(repository);
		logs.clear();
		logs.add(log);
	}

	@Test
	void testRegisterLogs() {
		when(parser.parse("", "")).thenReturn(logs);
		when(repository.save(log)).thenReturn(log);
		String result = service.registerLogs("", "");
		assertEquals(logsAdded, result);
	}

	@Test
	void whenDateAndTimeThenBoth() {
		when(repository.findAllByApplicationAndDatetimeBetween(app, startTime, endTime, startDate, endDate, pageable))
				.thenReturn(logs);
		String result = service.getLogs(app, LocalDateTime.of(startDate, startTime).toString(),
				LocalDateTime.of(endDate, endTime).toString(), false, 0, 5);
		assertEquals("[{\"date\":null,\"time\":null,\"loggingLevel\":null,\"sourceClass\":null,\"content\":null}]",
				result);
	}

	@Test
	void whenNoDateThenTime() {
		when(repository.findAllByApplicationAndTimeBetween(app, startTime, endTime, pageable)).thenReturn(logs);
		String result = service.getLogs(app, startTime.toString(), endTime.toString(), false, 0, 5);
		assertEquals("[{\"date\":null,\"time\":null,\"loggingLevel\":null,\"sourceClass\":null,\"content\":null}]",
				result);

	}

	@Test
	void whenNoTimeThenDate() {
		when(repository.findAllByApplicationAndDateBetween(app, startDate, endDate, pageable)).thenReturn(logs);
		String result = service.getLogs(app, startDate.toString(), endDate.toString(), false, 0, 5);
		assertEquals("[{\"date\":null,\"time\":null,\"loggingLevel\":null,\"sourceClass\":null,\"content\":null}]",
				result);

	}

	@Test
	void testXml() {
		when(repository.findAllByApplicationAndDatetimeBetween(app, startTime, endTime, startDate, endDate, pageable))
				.thenReturn(logs);
		String result = service.getLogs(app, LocalDateTime.of(startDate, startTime).toString(),
				LocalDateTime.of(endDate, endTime).toString(), true, 0, 5);
		assertEquals("<ArrayList><item><date/><time/><loggingLevel/><sourceClass/><content/></item></ArrayList>",
				result);
	}
}
