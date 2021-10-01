package mfurmane.log.aggregator.tools;

import java.lang.System.Logger.Level;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import mfurmane.log.aggregator.dto.Log;

@Component
public class LogParser {

	public List<Log> parse(String body, String application) {
		List<Log> logs = new ArrayList<>();
		body.lines().forEach(line -> logs.add(parseLine(line, application)));
		return logs;
	}

	private Log parseLine(String line, String application) {
		Level logginglevel = findLevel(line);
		String sourceClass = findSourceClass(line);
		LocalDateTime time = findTime(line);
		String content = findContent(line);
		return new Log(null, application, time, logginglevel, sourceClass, content);
	}

	private Level findLevel(String line) {
		for (Level level : Level.values()) {
			if (line.contains(level.getName())) {
				return level;
			}
		}
		if (line.contains("WARN")) {
			return Level.WARNING;
		}
		return Level.ALL;
	}

	private LocalDateTime findTime(String line) {
		String regex = "\\d{4}-\\d{2}-\\d{2}[\\sT]*\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			String dateString = matcher.group().replace(" ", "T");
			return LocalDateTime.parse(dateString);
		}
		return null;
	}

	private String findSourceClass(String line) {
		String regex = "(([a-z][a-zA-Z\\d_$]*)?\\.)+[A-Z][a-zA-Z\\d_$]*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	private String findContent(String line) {
		return line;
	}

}
