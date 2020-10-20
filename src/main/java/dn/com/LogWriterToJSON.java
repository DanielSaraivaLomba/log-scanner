package dn.com;

import com.google.gson.Gson;
import dn.com.model.Report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWriterToJSON implements ILogWriter {

    private final static Logger _logger = Logger.getLogger(LogReader.class.getName());
    protected final Gson _gson = new Gson();

    @Override
    public void write(final Report report) {
        final String jsonString = _gson.toJson(report);
        try {
            final FileWriter myWriter = new FileWriter("report.json");
            myWriter.write(jsonString);
            myWriter.close();
            _logger.log(Level.FINE, "Successfully wrote to the JSON.");
        } catch (final IOException ex) {
            _logger.log(Level.SEVERE, "Exception occur during writing JSON report", ex);
        }
    }
}
