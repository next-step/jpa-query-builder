package persistence.sql.dialect;

import java.lang.reflect.Field;

public interface ColumnDialect {
    String getSqlColumn(Field field);
}
