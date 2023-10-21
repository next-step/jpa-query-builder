package persistence.sql.ddl.metadata;

import jakarta.persistence.Table;

public class EntityTable {

	public static <T> String getTableName(Class<T> aClass) {
		String tableName = aClass.getAnnotation(Table.class).name();
		if (tableName.isEmpty()) {
			return aClass.getSimpleName();
		}
		return tableName;
	}
}
