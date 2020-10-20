package dn.com;

import dn.com.model.LogLine;
import dn.com.model.Rendering;
import dn.com.model.Report;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(final String[] args) throws IOException {

        final LogReader logReader = new LogReader();

        final List<String> logs = readFile(logReader);
        final List<LogLine> logLines = parseLogs(logs);
        final ReportProcessor reportProcessor = processLogLines(logLines);
        final List<Rendering> listRenderings = new ArrayList<>(reportProcessor.getRenderings().values());
        final Report report = getReport(reportProcessor, listRenderings);
        createXmlReport(report);
        createJsonReport(report);
    }

    private static void createXmlReport(final Report report) {
        final ILogWriter logWriter = new LogWriterToXml();
        logWriter.write(report);
    }

    private static void createJsonReport(final Report report) {
        final ILogWriter logWriter = new LogWriterToJSON();
        logWriter.write(report);
    }

    private static Report getReport(final ReportProcessor reportProcessor, final List<Rendering> listRenderings) {
        final Report report = new Report();
        report.setSummary(reportProcessor.getSummary());
        report.setRenderings(listRenderings);
        return report;
    }

    private static ReportProcessor processLogLines(final List<LogLine> logLines) {
        final ReportProcessor reportProcessor = new ReportProcessor(logLines);
        reportProcessor.process();
        return reportProcessor;
    }

    private static List<LogLine> parseLogs(final List<String> logs) {
        final Parser parser = new Parser(logs);
        return parser.parseLines();
    }

    private static List<String> readFile(final LogReader logReader) throws IOException {
        final Path path = logReader.getLevelPath();
        return logReader.readFromInputStream(path);
    }
}
