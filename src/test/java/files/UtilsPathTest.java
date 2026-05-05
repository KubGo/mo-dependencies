package files;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class UtilsPathTest {

    @Test
    void checkCorrectPathFromParentDir(){
        ModelicaPathInfo modelicaPath = new ModelicaPathInfo(Path.of("TopPackage", "Package", "Model.mo").toString());
        assertEquals("Model", modelicaPath.className);
        assertEquals("TopPackage.Package.Model", modelicaPath.path);
    }

    @Test
    void CheckCorrectPathWithLibraryName(){
        ModelicaPathInfo path = new ModelicaPathInfo(
                Path.of("C:", "Documents", "TopPackage", "Package", "Model.mo").toString(),
                                                     "TopPackage");
        assertEquals("Model", path.className);
        assertEquals("TopPackage.Package.Model", path.path);
    }

}