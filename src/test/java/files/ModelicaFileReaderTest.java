package files;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaFileReaderTest {

    @Test
    void testReadingFile() throws IOException {
        ModelicaFileReader modelicaFileReader = new ModelicaFileReader();

        var lines = modelicaFileReader.readFile(
                Objects.requireNonNull(getClass().getClassLoader().getResource("BouncingBall.mo")).getPath());
        assertEquals("model BouncingBall \"The 'classic' bouncing ball model\"", lines.getFirst());
    }
}