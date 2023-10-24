package persistence.sql.dialect.h2;

import persistence.sql.Dialect;

import java.lang.reflect.Field;

/**
 * H2 SQL
 */
public class H2Dialect implements Dialect {

    private static final int VARCHAR_DEFAULT_LENGTH = 255;

    private final ColumnAttributes columnAttributes = new ColumnAttributes();

    @Override
    public String getDbType(Class<?> columnType) {
        return H2ColumnType.getDbType(columnType);
    }

    @Override
    public String getStringLength(Field entityField) {
        return "(" + columnAttributes.getStringLength(entityField, VARCHAR_DEFAULT_LENGTH) + ")";
    }

}
