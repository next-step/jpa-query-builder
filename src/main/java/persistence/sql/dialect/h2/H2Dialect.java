package persistence.sql.dialect.h2;

import jakarta.persistence.Column;
import persistence.sql.Dialect;

import java.lang.reflect.Field;

/**
 * H2 SQL
 */
public class H2Dialect implements Dialect {

    private static final int VARCHAR_DEFAULT_LENGTH = 255;

    @Override
    public String getDbType(Class<?> columnType) {
        return H2ColumnType.getDbType(columnType);
    }

    @Override
    public String getStringLength(Field entityField) {
        return "(" + getStringLengthInt(entityField) + ")";
    }

    private int getStringLengthInt(Field entityField) {
        Column columnAnnotation = entityField.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null) {
            return VARCHAR_DEFAULT_LENGTH;
        }
        return entityField.getDeclaredAnnotation(Column.class).length();
    }

}
