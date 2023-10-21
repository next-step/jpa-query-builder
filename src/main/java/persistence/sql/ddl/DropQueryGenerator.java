package persistence.sql.ddl;

import jakarta.persistence.Table;
import persistence.sql.ddl.metadata.EntityTable;

public class DropQueryGenerator<T> {

	private final String tableName;

	public DropQueryGenerator(Class<T> aClass) {
		this.tableName = EntityTable.getTableName(aClass);
	}

	public String getDropQuery() {
		return String.format("DROP TABLE %s;", this.tableName);
	}
}
