package mfurmane.log.aggregator.services;

public interface LogService {

	String registerLogs(String body);

	String getLogs(String application, String startDate, String endDate, Boolean xml);

}
