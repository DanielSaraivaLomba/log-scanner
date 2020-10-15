package dn.com;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogReaderTest {

    LogReader logReader;

    @BeforeEach
    public void setUp() {
        logReader = new LogReader();
    }

    @Test
    public void testWhenPathDoesNotExist_ShouldReturnException() {
        final Path wrongPath = Path.of("wrongPath");
        assertThrows(NoSuchFileException.class, () -> logReader.readFromInputStream(wrongPath));
    }

    @Test
    public void testWhenReadingLinesWithoutTimestamp_ShouldAppendToLastLine() throws IOException {
        final Path resourceDirectory = Paths.get("src", "test", "java", "resources", "log.txt");
        final List<String> logs = logReader.readFromInputStream(resourceDirectory);
        Assertions.assertEquals(logs.get(13), "2010-10-06 09:12:38,195 [inga1] ERROR [PacketConnectionHandler]: Connection reset" +
                "java.net.SocketException: Connection reset" +
                "\tat java.net.SocketInputStream.read(SocketInputStream.java:179)" +
                "\tat java.io.BufferedInputStream.fill(BufferedInputStream.java:229)" +
                "\tat java.io.BufferedInputStream.read(BufferedInputStream.java:248)" +
                "\tat com.dn.athena.net.Packet.scanForPacketHeader(Packet.java:141)" +
                "\tat com.dn.athena.net.Packet.readFromStream(Packet.java:116)" +
                "\tat com.dn.athena.net.stream.StreamConnectionHandler.processPacket(StreamConnectionHandler.java:122)" +
                "\tat com.dn.athena.net.stream.StreamConnectionHandler.readIncoming(StreamConnectionHandler.java:102)" +
                "\tat com.dn.athena.net.stream.StreamConnectionHandler.run(StreamConnectionHandler.java:69)" +
                "\tat java.lang.Thread.run(Thread.java:735)");
    }

}
