package mfurmane.log.aggregator.services.impl;

import java.time.LocalDateTime;
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
		LocalDateTime start = LocalDateTime.parse(startDate);
		LocalDateTime end = LocalDateTime.parse(endDate);
		Pageable pageable = PageRequest.of(page, pageSize);
		List<Log> findAll = repository.findAllByApplicationAndTimeBetween(application, start.toLocalTime(),
				end.toLocalTime(), start.toLocalDate(), end.toLocalDate(), pageable);
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

}
