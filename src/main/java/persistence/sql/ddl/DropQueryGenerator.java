package persistence.sql.ddl;

import jakarta.persistence.Table;

public class DropQueryGenerator<T> {

	private final String tableName;

	public DropQueryGenerator(Class<T> aClass) {
		this.tableName = getTableName(aClass);
	}

	public String getDropQuery() {
		return String.format("DROP TABLE %s;", this.tableName);
	}

	private String getTableName(Class<T> aClass) {
		String tableName = aClass.getAnnotation(Table.class).name();
		if (tableName.isEmpty()) {
			return aClass.getSimpleName();
		}
		return tableName;
	}
}
