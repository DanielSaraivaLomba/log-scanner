package dn.com;

import dn.com.constants.MessageConstants;
import dn.com.model.LogLine;
import dn.com.model.Rendering;
import dn.com.model.Summary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportProcessor {

    final private List<LogLine> logLines;
    final private LinkedHashMap<String, Rendering> threadCache;
    final private LinkedHashMap<String, Rendering> renderings;
    private final static Logger logger = Logger.getLogger(LogReader.class.getName());


    public ReportProcessor(final List<LogLine> logLines) {
        this.logLines = logLines;
        this.threadCache = new LinkedHashMap<>();
        this.renderings = new LinkedHashMap<>();
    }

    public LinkedHashMap<String, Rendering> getRenderings() {
        return renderings;
    }

    public void process() {
        Rendering rendering;
        for (final LogLine logLine : logLines) {
            final String uID = getuID(logLine.message);
            if (logLine.message.contains(MessageConstants.EXECUTING_START_RENDERING)) {
                rendering = getNewRendering(logLine.message);
                threadCache.put(logLine.thread, rendering);
                logger.log(Level.FINE, "Found new rendering {0}", new Object[]{logLine.message});
            }
            if (logLine.message.contains(MessageConstants.SERVICE_START_RENDERING)) {
                validateStartRendering(logLine, uID);
                logger.log(Level.FINE, "Found rendering with UID: {0}, message: {1}", new Object[]{uID, logLine.message});
            }
            if (logLine.message.contains(MessageConstants.EXECUTING_GET_RENDERING)) {
                validateGetRendering(logLine, uID);
                logger.log(Level.FINE, "Found get rendering with UID: {0}, message: {1}", new Object[]{uID, logLine.message});
            }
        }
    }

    private void validateGetRendering(final LogLine logLine, final String uID) {
        final Rendering rendering;
        if (renderings.containsKey(uID)) {
            rendering = renderings.get(uID);
            rendering.addGetRendering(logLine.timestamp);
        }
    }

    private void validateStartRendering(final LogLine logLine, final String uID) {
        final Rendering rendering;
        if (renderings.containsKey(uID)) {
            rendering = renderings.get(uID);
            rendering.addStart(logLine.timestamp);
        } else {
            rendering = threadCache.get(logLine.thread);
            rendering.setuID(uID);
            rendering.addStart(logLine.timestamp);
            renderings.put(uID, rendering);
            threadCache.remove(logLine.thread, rendering);
        }
    }

    public Summary getSummary() {
        final Summary summary = new Summary();
        summary.setCount(renderings.size());
        Integer countDuplicates = 0;
        Integer countUnnecessary = 0;
        for (final Map.Entry<String, Rendering> entry : renderings.entrySet()) {
            if (entry.getValue().getStartRendering().size() > 1) {
                countDuplicates++;
            }
            if (entry.getValue().getGetRendering() == null || entry.getValue().getGetRendering().size() == 0) {
                countUnnecessary++;
            }
        }
        summary.setDuplicates(countDuplicates);
        summary.setUnnecessary(countUnnecessary);
        return summary;
    }

    private Rendering getNewRendering(final String logMessage) {
        final Rendering rendering = new Rendering();
        final String docIdAndPage = Objects.requireNonNull(Parser.getParsedString(logMessage, "\\[(.*?)\\]"))
                .replaceAll("\\[", "")
                .replaceAll("]", "")
                .replaceAll(" ", "");
        final String[] split = docIdAndPage.split(",");
        rendering.setDocument(split[0]);
        rendering.setPage(Integer.parseInt(split[1]));
        return rendering;
    }

    private String getuID(final String logMessage) {
        return Parser.getParsedString(logMessage, "\\d{4,}-\\d{2,}");
    }
}
