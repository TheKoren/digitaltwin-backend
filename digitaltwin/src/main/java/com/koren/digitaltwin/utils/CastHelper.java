package com.koren.digitaltwin.utils;

import java.util.List;
import java.util.Map;

public class CastHelper {
    @SuppressWarnings("unchecked")
    public static Map<String, Object> castToMapStringObject(Object obj) {
        if (obj instanceof Map<?, ?>) {
            return (Map<String, Object>) obj;
        } else {
            throw new IllegalArgumentException("Object is not of type Map<String, Object>");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<String> castToListString(Object obj) {
        if (obj instanceof List<?>) {
            return (List<String>) obj;
        } else {
            throw new IllegalArgumentException("Object is not of type List<String>");
        }
    }
}