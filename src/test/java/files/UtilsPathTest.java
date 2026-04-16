package files;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsPathTest {

    @Test
    void checkCorrectPathFromParentDir(){
        ModelicaPathInfo path = new ModelicaPathInfo("TopPackage\\Package\\Model.mo");
        assertEquals("Model", path.className);
        assertEquals("TopPackage.Package.Model", path.path);
    }

    @Test
    void CheckCorrectPathWithLibraryName(){
        ModelicaPathInfo path = new ModelicaPathInfo("C:\\Documents\\TopPackage\\Package\\Model.mo", "TopPackage");
        assertEquals("Model", path.className);
        assertEquals("TopPackage.Package.Model", path.path);
    }

}