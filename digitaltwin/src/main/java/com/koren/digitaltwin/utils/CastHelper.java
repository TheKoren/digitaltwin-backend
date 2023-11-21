package com.koren.digitaltwin.utils;

import java.util.List;
import java.util.Map;

/**
 * Utility class for type casting operations.
 */
public class CastHelper {

    /**
     * Casts an object to a Map of String and Object.
     *
     * @param obj Object to be casted.
     * @return Map<String, Object> if the object is a Map.
     * @throws IllegalArgumentException if the object is not of type Map<String, Object>.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> castToMapStringObject(Object obj) {
        if (obj instanceof Map<?, ?>) {
            return (Map<String, Object>) obj;
        } else {
            throw new IllegalArgumentException("Object is not of type Map<String, Object>");
        }
    }

    /**
     * Casts an object to a List of String.
     *
     * @param obj Object to be casted.
     * @return List<String> if the object is a List.
     * @throws IllegalArgumentException if the object is not of type List<String>.
     */
    @SuppressWarnings("unchecked")
    public static List<String> castToListString(Object obj) {
        if (obj instanceof List<?>) {
            return (List<String>) obj;
        } else {
            throw new IllegalArgumentException("Object is not of type List<String>");
        }
    }

    /**
     * Casts an object to a List of Map<String, Object>.
     *
     * @param obj Object to be casted.
     * @return List<Map<String, Object>> if the object is a List of Maps.
     * @throws IllegalArgumentException if the object is not a List of type List<Map<String, Object>>.
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> castToListMapStringObject(Object obj) {
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty() && list.get(0) instanceof Map<?, ?>) {
                return (List<Map<String, Object>>) obj;
            } else {
                throw new IllegalArgumentException("Object is not a List of type List<Map<String, Object>>");
            }
        } else {
            throw new IllegalArgumentException("Object is not of type List<?>");
        }
    }
}
