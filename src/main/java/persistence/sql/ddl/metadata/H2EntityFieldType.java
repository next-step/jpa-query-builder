package persistence.sql.ddl.metadata;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.util.Map;

public class H2EntityFieldType implements EntityFieldType {

	private static final Map<Class<?>, String> entityFieldType = Map.of(
		Long.class, "BIGINT",
		Integer.class, "INT",
		String.class, "VARCHAR"
	);

	@Override
	public String getDataType(Field field) {
		Class<?> aClass = field.getType();
		if (aClass == String.class) {
			return getString(field);
		}
		return entityFieldType.get(field.getType());
	}

	private String getString(Field field) {
		if (field.isAnnotationPresent(Column.class)) {
			Column column = field.getAnnotation(Column.class);
			return entityFieldType.get(String.class) + "(" + column.length() + ")";
		}
		return entityFieldType.get(String.class) + "(255)";
	}
}
