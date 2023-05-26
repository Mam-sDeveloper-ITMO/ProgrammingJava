package textlocale.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import lombok.Cleanup;

/**
 * Provides utility methods for working with tl.json files.
 *
 * @see TextLocale
 */
public class TLJsonUtils {
    /**
     * Gson instance.
     */
    private static final Gson gson = new Gson();

    /**
     * Convert tl.json file to map.
     *
     * @param jsonFile tl.json file
     * @return map with json data
     * @throws IOException
     * @throws JsonSyntaxException
     */
    public static Map<String, Object> jsonFileToMap(File jsonFile) throws IOException, JsonSyntaxException {
        @Cleanup
        FileReader fileReader = new FileReader(jsonFile);

        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        Map<String, Object> jsonMap = gson.fromJson(fileReader, type);
        if (jsonMap == null) {
            throw new JsonSyntaxException("Json file is empty");
        }
        return jsonMap;
    }

    /**
     * Verify that mapped json file is valid.
     *
     * Each node in json file must be a map or a string.
     *
     * @param jsonMap mapped json file
     * @return true if json file is valid, false otherwise
     */
    public static boolean isValidJson(Map<String, Object> jsonMap) {
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            if (entry.getValue() instanceof Map) {
                if (!isValidJson((Map<String, Object>) entry.getValue())) {
                    return false;
                }
            } else if (!(entry.getValue() instanceof String)) {
                return false;
            }
        }
        return true;
    }
}
