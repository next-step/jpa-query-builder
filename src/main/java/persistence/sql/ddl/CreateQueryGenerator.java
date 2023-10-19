package persistence.sql.ddl;

import jakarta.persistence.Table;

public class CreateQueryGenerator<T> {

	private static final String CREATE_TABLE = "CREATE TABLE %s (%s);";
	private static final String DROP_TABLE = "DROP TABLE %s;";
	private final EntityColumns entityColumns;
	private final String tableName;

	public CreateQueryGenerator(Class<T> aClass) {
		this.entityColumns = new EntityColumns(aClass);
		this.tableName = getTableName(aClass);
	}

	public String getCreateQuery() {
		return String.format(CREATE_TABLE, tableName, getColumn());
	}

	private String getTableName(Class<T> aClass) {
		String tableName = aClass.getAnnotation(Table.class).name();
		if (tableName.isEmpty()) {
			return aClass.getSimpleName();
		}
		return tableName;
	}

	private String getColumn() {
		return entityColumns.getColumnQuery();
	}

	public String getDropQuery() {
		return String.format(DROP_TABLE, tableName);
	}
}
