package dn.com;

import dn.com.constants.MessageConstants;
import dn.com.model.LogLine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private final List<String> logs;
    private final static Logger _logger = Logger.getLogger(LogReader.class.getName());


    public Parser(final List<String> logs) {
        this.logs = logs;
    }

    List<LogLine> parseLines() {
        _logger.log(Level.FINE, "Trying to parse log lines");
        final List<LogLine> logLines = new ArrayList<>();
        for (final String log : logs) {
            final String regex = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3}\\s\\[(.*?)]\\s(.*?)\\s{1,}\\[(.*?)]";
            final String[] splitInTwo = log.split(regex);

            final LogLine logLine = new LogLine();
            logLine.message = splitInTwo[1];
            logLine.timestamp = getParsedString(log, "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3}");
            logLine.thread = getParsedString(log, "\\[(.*?)\\]");
            logLine.logLevel = getParsedString(log, "[A-Z]{4,}");
            logLine.serviceName = getParsedString(log, "\\[[a-zA-Z]{1,}]:");
            _logger.log(Level.FINE, "new logLine object created: {0}", new Object[]{logLine});

            if (logLine.message.contains(MessageConstants.EXECUTING_START_RENDERING)) logLines.add(logLine);
            if (logLine.message.contains(MessageConstants.SERVICE_START_RENDERING)) logLines.add(logLine);
            if (logLine.message.contains(MessageConstants.EXECUTING_GET_RENDERING)) logLines.add(logLine);
        }
        return logLines;
    }

    protected static String getParsedString(final String log, final String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(log);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
