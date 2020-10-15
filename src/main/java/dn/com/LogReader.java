package dn.com;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogReader {

    private final static Logger _logger = Logger.getLogger(LogReader.class.getName());
    private final List<String> lines = new LinkedList<>();

    protected List<String> readFromInputStream(final Path path) throws IOException {
        final Pattern regexp = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}");
        final Matcher matcher = regexp.matcher("");
        try {
            final BufferedReader reader = Files.newBufferedReader(path);
            final LineNumberReader lineReader = new LineNumberReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                matcher.reset(line);
                if (matcher.find()) {
                    lines.add(line);
                } else {
                    final String stringToAppend = lines.get(lines.size() - 1);
                    lines.set(lines.size() - 1, stringToAppend + line);
                }
            }
        } catch (final IOException ex) {
            _logger.log(Level.SEVERE, "Exception occur during reading log file", ex);
            throw ex;
        }
        return lines;
    }

    protected Path getLevelPath() {
        final FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setDirectory("C://");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        final String stringPath = dialog.getDirectory() + dialog.getFile();
        _logger.fine(stringPath + " chosen.");
        return Paths.get(stringPath);
    }

}
