package mfurmane.log.aggregator.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public String registerLogs(String body) {
		List<Log> logs = parser.parse(body);
		logs.forEach(log -> repository.save(log));
		return message.replace("?", Integer.valueOf(logs.size()).toString());
	}

	@Override
	public String getLogs(String application, String startDate, String endDate, Boolean xml) {
		// TODO Auto-generated method stub
		return null;
	}

}
