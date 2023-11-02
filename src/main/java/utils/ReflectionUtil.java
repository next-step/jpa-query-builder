package utils;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static String generateGetterMethodName(Field field) {
        return "get"+capitalizeFirstLetter(field.getName());
    }

    public static String generateGetterMethodName(String fieldName) {
        return "get"+capitalizeFirstLetter(fieldName);
    }

    private static String capitalizeFirstLetter(String str) {
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
}
