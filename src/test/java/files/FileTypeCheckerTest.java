package files;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileTypeCheckerTest {
    FileTypeChecker fileTypeChecker = new FileTypeChecker();
    @Test
    void getFileType() {
        assertEquals(FileType.MODEL, fileTypeChecker.getFileType(Path.of("test.mo")));
        assertEquals(FileType.PACKAGE, fileTypeChecker.getFileType(Path.of("package.mo")));
        assertEquals(FileType.ORDER, fileTypeChecker.getFileType(Path.of("package.order")));
        assertEquals(FileType.OTHER, fileTypeChecker.getFileType(Path.of("test.mos")));
    }

}