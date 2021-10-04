package mfurmane.log.aggregator.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import mfurmane.log.aggregator.dto.Log;
import mfurmane.log.aggregator.repositories.LogRepository;
import mfurmane.log.aggregator.services.LogService;
import mfurmane.log.aggregator.tools.LogParser;

@Service
public class LogServiceImpl implements LogService {

	private static final String message = "Logs added: ?";

	@Autowired
	private LogRepository repository;
	@Autowired
	private LogParser parser;

	@Override
	public String registerLogs(String body, String application) {
		List<Log> logs = parser.parse(body, application);
		logs.forEach(log -> System.out.println(log.toString()));
		logs.forEach(log -> repository.save(log));
		return message.replace("?", Integer.valueOf(logs.size()).toString());
	}

	@Override
	public String getLogs(String application, String startDate, String endDate, Boolean xml, int page, int pageSize) {
		DateAndTime startDateAndTime = new DateAndTime();
		DateAndTime endDateAndTime = new DateAndTime();
		parseDateAndTime(startDate, startDateAndTime);
		parseDateAndTime(endDate, endDateAndTime);
		Pageable pageable = PageRequest.of(page, pageSize);
		List<Log> findAll = callRepository(application, startDateAndTime, endDateAndTime, pageable);
		try {
			if (xml) {
				XmlMapper xmlMapper = new XmlMapper();
				return xmlMapper.writeValueAsString(findAll);
			} else {
				ObjectMapper mapper = new ObjectMapper();
				return mapper.writeValueAsString(findAll);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void parseDateAndTime(String startDate, DateAndTime dateAndTime) {
		try {
			LocalDateTime start = LocalDateTime.parse(startDate);
			dateAndTime.time = start.toLocalTime();
			dateAndTime.date = start.toLocalDate();
		} catch (DateTimeParseException e) {
			try {
				dateAndTime.time = LocalTime.parse(startDate);
			} catch (DateTimeParseException e2) {
				dateAndTime.date = LocalDate.parse(startDate);
			}
		}
	}

	private List<Log> callRepository(String application, DateAndTime startDateAndTime, DateAndTime endDateAndTime,
			Pageable pageable) {
		List<Log> findAll;
		if (startDateAndTime.date == null) {
			findAll = repository.findAllByApplicationAndTimeBetween(application, startDateAndTime.time,
					endDateAndTime.time, pageable);
		} else if (startDateAndTime.time == null) {
			findAll = repository.findAllByApplicationAndDateBetween(application, startDateAndTime.date,
					endDateAndTime.date, pageable);
		} else {
			findAll = repository.findAllByApplicationAndDatetimeBetween(application, startDateAndTime.time,
					endDateAndTime.time, startDateAndTime.date, endDateAndTime.date, pageable);
		}
		return findAll;
	}

	private class DateAndTime {
		LocalDate date;
		LocalTime time;
	}

}
