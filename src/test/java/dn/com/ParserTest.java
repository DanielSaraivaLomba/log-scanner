package dn.com;

import dn.com.model.LogLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserTest {

    Parser parser;

    @Test
    public void testWhenParsingLine_ShouldReturnCreatedLogLine() throws IOException {
        final String log1 = "2010-10-06 09:02:11,550 [WorkerThread-4] INFO  [ServiceProvider]: Executing request getRendering with arguments [1286373729338-5317] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }";
        final List<String> logs = new ArrayList<>();
        logs.add(log1);

        final Parser parser = new Parser(logs);
        final List<LogLine> logLines = parser.parseLines();
        Assertions.assertEquals("INFO", logLines.get(0).logLevel);
        Assertions.assertEquals(": Executing request getRendering with arguments [1286373729338-5317] on service object { ReflectionServiceObject -> com.dn.gaverzicht.dms.services.DocumentService@4a3a4a3a }", logLines.get(0).message);
        Assertions.assertEquals("2010-10-06 09:02:11,550", logLines.get(0).timestamp);
        Assertions.assertEquals("[ServiceProvider]:", logLines.get(0).serviceName);
        Assertions.assertEquals("[WorkerThread-4]", logLines.get(0).thread);
    }

    @Test
    public void testWhenParsingNonMatchString_ShouldReturnEmptyList() {
        final String log1 = "2010-10-06 09:11:51,368 [WorkerThread-0] DEBUG [DmsObjectDeterminator]: Object to encode for ObjectId { com.dn.gaverzicht.dms.models.DocumentProperty - 667488 } (encoding depth = 1): null";
        final List<String> logs = new ArrayList<>();
        logs.add(log1);
        final Parser parser = new Parser(logs);
        final List<LogLine> logLines = parser.parseLines();
        Assertions.assertEquals(0, logLines.size());

    }
}
