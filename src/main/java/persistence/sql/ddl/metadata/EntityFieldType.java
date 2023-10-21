package persistence.sql.ddl.metadata;

import java.lang.reflect.Field;

public interface EntityFieldType {

	String getDataType(Field field);
}
