package persistence.sql.dialect.h2;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnAttributes {

    public int getStringLength(Field entityField, int defaultLength) {
        Column columnAnnotation = entityField.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null) {
            return defaultLength;
        }
        return entityField.getDeclaredAnnotation(Column.class).length();
    }

}
