package persistence.sql.dialect;

import java.lang.reflect.Field;

public interface Dialect {

    String getTableName(Class<?> clazz);

    String getFieldName(Field field);

    String getFieldType(Field field);

    String getFieldLength(Field field);

    String getGenerationType(Field field);

    String getColumnNullConstraint(Field field);
}
