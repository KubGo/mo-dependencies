package files;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UtilsFileReaderTest {

    @Test
    void testReadingFile() throws IOException {
        ModelicaFileReader modelicaFileReader = new ModelicaFileReader();

        String text = modelicaFileReader.readFile(
                Objects.requireNonNull(getClass().getClassLoader().getResource(
		                "BuildingsLite/Tests/BouncingBall.mo")).getPath());
        assertEquals("model BouncingBall \"The 'classic' bouncing ball model\"",
                     text.split("\n")[1]);
    }
}