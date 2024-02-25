package database.sql.util.column;

import java.lang.reflect.Field;

public abstract class AbstractEntityColumn implements EntityColumn {
    protected final Field field;
    protected final String fieldName;
    protected final String columnName;
    protected final Class<?> type;
    protected final Integer columnLength;

    public AbstractEntityColumn(Field field,
                                String columnName,
                                Class<?> type,
                                Integer columnLength) {
        this.field = field;
        this.fieldName = field.getName();
        this.columnName = columnName;
        this.type = type;
        this.columnLength = columnLength;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Object getValue(Object entity) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(entity);
    }
}
