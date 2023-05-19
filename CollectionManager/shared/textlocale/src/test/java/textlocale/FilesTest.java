package textlocale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import textlocale.utils.FilesUtils;

public class FilesTest {
    @Test
    public void testFindFilesWithExtension() throws IOException {
        String packageName = "textlocale";
        String extension = ".tl.json";

        Map<String, File> matchingFiles = FilesUtils.findFilesWithExtension(packageName, extension);

        assertNotNull(matchingFiles);
        assertEquals(2, matchingFiles.size());

        for (Map.Entry<String, File> entry : matchingFiles.entrySet()) {
            assertTrue(entry.getValue().isFile());
            assertTrue(entry.getValue().getName().endsWith(extension));
        }
    }
}
