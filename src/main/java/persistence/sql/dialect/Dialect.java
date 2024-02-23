package persistence.sql.dialect;

import java.lang.reflect.Field;

public interface Dialect {

    String generateColumnSql(Field field);
}
