package dn.com;

import dn.com.model.Report;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWriterToXml implements ILogWriter {

    private final static Logger _logger = Logger.getLogger(LogReader.class.getName());

    @Override
    public void write(final Report report) {
        try {
            final File file = new File("report.xml");
            final JAXBContext context = JAXBContext.newInstance(Report.class);
            final Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(report, file);
            marshaller.marshal(report, System.out);
        } catch (final JAXBException ex) {
            _logger.log(Level.SEVERE, "Exception occur during writing xml report", ex);
        }
    }
}
