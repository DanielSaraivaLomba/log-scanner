package dn.com;

import dn.com.model.Report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWriterToFile implements ILogWriter {

    private final static Logger _logger = Logger.getLogger(LogReader.class.getName());

    @Override
    public void write(final Report report) {
        try {
            final FileWriter myWriter = new FileWriter("report.txt");
            myWriter.write(report.toString());
            myWriter.close();
            _logger.log(Level.FINE, "Successfully wrote to the file.");
        } catch (final IOException ex) {
            _logger.log(Level.SEVERE, "Exception occur during writing file report", ex);
        }
    }
}
