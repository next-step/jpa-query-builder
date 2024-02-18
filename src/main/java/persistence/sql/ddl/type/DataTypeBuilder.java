package persistence.sql.ddl.type;

import java.lang.reflect.Field;

public interface DataTypeBuilder {
    String getDataTypeDefinitionFrom(Field field);
}
