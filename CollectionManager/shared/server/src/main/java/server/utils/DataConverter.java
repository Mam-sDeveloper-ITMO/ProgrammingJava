package server.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides methods for Response and Request data conversion.
 *
 * @see server.responses.Response
 * @see server.requests.Request
 */
public class DataConverter {
    /**
     * Converts map of objects to map of serializable objects.
     *
     * @param serializableMap Map of serializable objects.
     * @return Map of objects.
     */
    public static Map<String, Object> serializableToObjects(Map<String, Serializable> serializableMap) {
        Map<String, Object> objectMap = new HashMap<>();

        for (Map.Entry<String, Serializable> entry : serializableMap.entrySet()) {
            objectMap.put(entry.getKey(), entry.getValue());
        }

        return objectMap;
    }

    /**
     * Converts map of objects to map of serializable objects.
     *
     * @param objectMap Map of objects.
     * @return Map of serializable objects.
     */
    public static Map<String, Serializable> objectsToSerializable(Map<String, Object> objectMap) {
        Map<String, Serializable> serializableMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            serializableMap.put(entry.getKey(), (Serializable) entry.getValue());
        }

        return serializableMap;
    }
}
