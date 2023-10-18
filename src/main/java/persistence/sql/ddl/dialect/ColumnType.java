package persistence.sql.ddl.dialect;

import java.lang.reflect.Field;

public interface ColumnType {

    String generationIdentity();

    String getFieldType(Field field);
}
