package mfurmane.log.aggregator.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import mfurmane.log.aggregator.dto.Log;

@Component
public class LogParser {

	private String fullDateTimeRegex = "\\d{4}-\\d{2}-\\d{2}[\\sT]*\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?";
	private String justDateRegex = "\\d{4}-\\d{2}-\\d{2}";
	private String justTimeRegex = "\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?";

	public List<Log> parse(String body, String application) {
		List<Log> logs = new ArrayList<>();
		body.lines().forEach(line -> logs.add(parseLine(line, application)));
		return logs;
	}

	private Log parseLine(String line, String application) {
		LineHolder lineHolder = new LineHolder(line);
		LogBuilder builder = new LogBuilder();
		builder.application(application);
		builder.loggingLevel(findLevel(lineHolder));
		builder.sourceClass(findSourceClass(lineHolder));
		findTime(lineHolder, builder);
		lineHolder.line = lineHolder.line.replaceAll("^[ \\t:-]+", "").replaceAll("[ \\t]+$", "").replaceAll("\\s\\s+",
				" ");
		builder.content(lineHolder.line);
		return builder.build();
	}

	private Level findLevel(LineHolder line) {
		for (Level level : Level.values()) {
			if (line.line.contains(level.name())) {
				line.remove(level.name());
				return level;
			}
		}
		return Level.INFO;
	}

	private void findTime(LineHolder line, LogBuilder builder) {
		String datetime = checkDateTimeFormat(line, fullDateTimeRegex);
		if (datetime != null) {
			LocalDateTime dt = LocalDateTime.parse(datetime.replace(" ", "T"));
			builder.date(dt.toLocalDate());
			builder.time(dt.toLocalTime());
		}
		datetime = checkDateTimeFormat(line, justDateRegex);
		if (datetime != null) {
			builder.date(LocalDate.parse(datetime));
		}
		datetime = checkDateTimeFormat(line, justTimeRegex);
		if (datetime != null) {
			builder.time(LocalTime.parse(datetime));
		}
	}

	private String checkDateTimeFormat(LineHolder line, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line.line);
		if (matcher.find()) {
			return line.remove(matcher.group());
		}
		return null;
	}

	private String findSourceClass(LineHolder line) {
		String regex = "(([a-z][a-zA-Z\\d_$]*)?\\.)+[A-Z][a-zA-Z\\d_$]*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line.line);
		if (matcher.find()) {

			return line.remove(matcher.group());
		}
		return null;
	}

	private class LineHolder {

		String line;

		public LineHolder(String line) {
			this.line = line;
		}

		String remove(String input) {
			line = line.replace(input, "");
			return input;
		}

	}

}
