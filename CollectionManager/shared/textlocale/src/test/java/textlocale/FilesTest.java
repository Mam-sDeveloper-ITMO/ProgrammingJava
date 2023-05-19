package textlocale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import textlocale.utils.Files;

public class FilesTest {
    @Test
    public void testFindFilesWithExtension() throws IOException {
        String packageName = "textlocale";
        String extension = ".tl.json";

        List<File> matchingFiles = Files.findFilesWithExtension(packageName, extension);

        assertNotNull(matchingFiles);
        assertEquals(2, matchingFiles.size());

        for (File file : matchingFiles) {
            assertTrue(file.isFile());
            assertTrue(file.getName().endsWith(extension));
        }
    }
}
