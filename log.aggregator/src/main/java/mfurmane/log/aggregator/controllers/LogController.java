package mfurmane.log.aggregator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mfurmane.log.aggregator.services.LogService;

@RestController
@RequestMapping("/log")
public class LogController {

	@Autowired
	LogService logService;

	@PostMapping("/register")
	String registerLogs(@RequestBody String body, @RequestParam(name = "application") String application) {
		return logService.registerLogs(body, application);
	}

	@GetMapping("/get")
	String getLogs(@RequestParam(name = "application") String application,
			@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate,
			@RequestParam(name = "xml", defaultValue = "false") Boolean xml,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "pageSize", defaultValue = "20") int pageSize) {
		return logService.getLogs(application, startDate, endDate, xml, page, pageSize);
	}

}
