package files;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaPathTest {

    @Test
    void checkCorrectPathFromParentDir(){
        ModelicaPath path = new ModelicaPath("TopPackage\\Package\\Model.mo");
        assertEquals("Model", path.className);
        assertEquals("TopPackage.Package.Model", path.path);
    }

    @Test
    void CheckCorrectPathWithLibraryName(){
        ModelicaPath path = new ModelicaPath("C:\\Documents\\TopPackage\\Package\\Model.mo", "TopPackage");
        assertEquals("Model", path.className);
        assertEquals("TopPackage.Package.Model", path.path);
    }

}