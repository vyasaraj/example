import java.text.DateFormat;

import java.text.MessageFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.logging.Formatter;

import java.util.logging.LogManager;

import java.util.logging.LogRecord;

public class PatternFormatter extends Formatter {
	private String logPattern;
	private String timeFormat;
	private MessageFormat logMessageFormat;
	private DateFormat dateFormat;

	public PatternFormatter() {
		LogManager manager = LogManager.getLogManager();
		String cname = getClass().getName();

		timeFormat = manager.getProperty(cname + ".timeFormat");
		if (timeFormat == null) {
			timeFormat = "dd-MMM-yyy; HH:mm:ss";
		}
		setTimeFormat(timeFormat);

		logPattern = manager.getProperty(cname + ".logPattern");
		if (logPattern == null) {
			logPattern = "[{0} - {1}] {2}: {3} \n";
		}
		setLogPattern(logPattern);
		logMessageFormat = new MessageFormat(logPattern);
		dateFormat = new SimpleDateFormat(timeFormat);
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
		dateFormat = new SimpleDateFormat(timeFormat);
	}

	public void setLogPattern(String logFormat) {
		logFormat = logFormat.replace("%c", "{0}"); // logger name
		logFormat = logFormat.replace("%p", "{1}"); // priority(level)
		logFormat = logFormat.replace("%d", "{2}"); // date and time
		logFormat = logFormat.replace("%m", "{3}"); // message
		logFormat = logFormat.replace("%C", "{4}"); // source class name
		logFormat = logFormat.replace("%M", "{5}"); // source method name
		this.logPattern = logFormat;
		logMessageFormat = new MessageFormat(logPattern);
	}

	public String format(LogRecord record) {
		Date time = new Date(record.getMillis());
		String formattedTime = dateFormat.format(time);
		String logMessage = "";
		Object[] log = { record.getLoggerName(), record.getLevel(),
				formattedTime, record.getMessage(),
				record.getSourceClassName(), record.getSourceMethodName() };
		logMessage = logMessageFormat.format(log);
		return logMessage;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public String getLogPattern() {
		return logPattern;
	}
}