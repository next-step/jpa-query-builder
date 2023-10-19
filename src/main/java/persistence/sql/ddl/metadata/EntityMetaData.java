package persistence.sql.ddl.metadata;

import java.lang.reflect.Field;

public interface EntityMetaData {

	String getMetaDateQuery(Field field);
}
