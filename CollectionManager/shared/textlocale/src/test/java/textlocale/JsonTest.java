package textlocale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import textlocale.utils.TLJsonUtils;

public class JsonTest {
    @Test
    public void testJsonFileToMapValidFile() throws IOException {
        Map<String, Object> expected = Map.of(
                "Hello, world!", Map.of(
                        "en", "Hello, world!",
                        "es", "¡Hola, mundo!"),
                "None", "None");

        // Arrange
        String filePath = "src/test/resources/text1.tl.json";
        File file = new File(filePath);

        // Act
        Map<String, Object> actual = TLJsonUtils.jsonFileToMap(file);

        // Assert
        assertEquals(expected, actual);
        assertTrue(TLJsonUtils.isValidJson(actual));
    }

    @Test
    public void testJsonFileToMapInvalidFile() throws IOException {
        // Arrange
        String filePath = "src/test/resources/invalid1.tl.json";
        File file = new File(filePath);
        Map<String, Object> actual = TLJsonUtils.jsonFileToMap(file);
        assertFalse(TLJsonUtils.isValidJson(actual));

        filePath = "src/test/resources/invalid2.tl.json";
        file = new File(filePath);
        actual = TLJsonUtils.jsonFileToMap(file);
        assertFalse(TLJsonUtils.isValidJson(actual));
    }
}
