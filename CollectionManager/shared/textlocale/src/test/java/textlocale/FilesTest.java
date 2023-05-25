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

        assertTrue(matchingFiles.containsKey("text1"));
        assertTrue(matchingFiles.containsKey("subdir"));
        assertTrue(matchingFiles.containsKey("subdir3"));

        Map<String, Object> subdir = (Map<String, Object>) matchingFiles.get("subdir");
        assertTrue(subdir.containsKey("text2"));
        assertTrue(subdir.containsKey("subdir2"));

        Map<String, Object> subdir2 = (Map<String, Object>) subdir.get("subdir2");
        assertTrue(subdir2.containsKey("text4"));

        Map<String, Object> subdir3 = (Map<String, Object>) matchingFiles.get("subdir3");
        assertTrue(subdir3.containsKey("text4"));
    }
}
