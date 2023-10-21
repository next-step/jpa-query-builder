package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.ddl.metadata.EntityTable;

public class CreateQueryGenerator<T> {

	private static final String CREATE_TABLE = "CREATE TABLE %s (%s);";
	private final EntityColumns entityColumns;
	private final String tableName;

	public CreateQueryGenerator(EntityColumns entityColumns, String tableName) {
		this.entityColumns = entityColumns;
		this.tableName = tableName;
	}

	public static <T> CreateQueryGenerator of(Class<T> aClass) {
		EntityColumns entityColumns = new EntityColumns(aClass);
		String tableName = EntityTable.getTableName(aClass);
		return new CreateQueryGenerator(entityColumns, tableName);
	}

	public String getCreateQuery() {
		return String.format(CREATE_TABLE, tableName, getColumn());
	}

	private String getColumn() {
		return entityColumns.getColumnQuery();
	}
}
