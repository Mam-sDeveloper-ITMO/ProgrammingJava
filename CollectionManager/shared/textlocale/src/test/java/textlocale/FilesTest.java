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

        Map<String, Object> matchingFiles = FilesUtils.findFilesWithExtension(packageName, extension);

        assertNotNull(matchingFiles);

        for (Map.Entry<String, Object> entry : matchingFiles.entrySet()) {
            String fileName = entry.getKey();
            File file = (File) entry.getValue();

            assertTrue(fileName.endsWith(extension));
        }
    }
}
