package persistence.sql.dml.parser;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import persistence.sql.dml.keygenerator.H2KeyGenerator;
import persistence.sql.dml.keygenerator.KeyGenerator;

import java.lang.reflect.Field;

public class ValueParser {

    public static String insertValuesClauseParse(Field field, Object object, KeyGenerator keyGenerator) {
        if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class)) {
            return keyGenerator.generator(field.getAnnotation(GeneratedValue.class).strategy());
        }

        field.setAccessible(true);
        Object value;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (field.getType().equals(String.class)) {
            return String.format("'%s'", value);
        }

        return String.valueOf(value);
    }

    public static String valueParse(Field field, Object object) {
        field.setAccessible(true);
        Object value;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (field.getType().equals(String.class)) {
            return String.format("'%s'", value);
        }

        if (field.getType().equals(Long.class)) {
            return String.format("%dL", value);
        }

        return String.valueOf(value);
    }
}
