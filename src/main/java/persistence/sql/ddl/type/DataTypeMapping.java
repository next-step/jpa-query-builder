package persistence.sql.ddl.type;

import java.lang.reflect.Field;

public interface DataTypeMapping {
    String getDataTypeDefinitionFrom(Field field);
}
