package mfurmane.log.aggregator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@PostMapping("/log/register")
	String registerLogs(@RequestBody String body) {
		return "";
	}

	@GetMapping("/log/get")
	String getLogs(@RequestParam(name = "application") String application,
			@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate,
			@RequestParam(name = "xml", defaultValue = "false") Boolean xml) {
		return "";
	}

}
