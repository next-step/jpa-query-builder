package persistence.sql.ddl.metadata;

import jakarta.persistence.Id;
import java.lang.reflect.Field;

public class H2EntityIdMetaData implements EntityMetaData {

	private static final String PRIMARY_KEY_META_DATA_VALUE = " PRIMARY KEY";
	@Override
	public String getMetaDateQuery(Field field) {
		if (field.isAnnotationPresent(Id.class)) {
			return PRIMARY_KEY_META_DATA_VALUE;
		}
		return "";
	}
}
