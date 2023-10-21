package persistence.sql.ddl.metadata;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

public class H2EntityTypeMetaData implements EntityMetaData {

	private static final String UNIQUE_CONSTRAINT_QUERY = " UNIQUE";
	private static final String NOT_NULL_CONSTRAINT_QUERY = " NOT NULL";

	@Override
	public String getMetaDateQuery(Field field) {
		StringBuilder query = new StringBuilder();
		Column column = field.getAnnotation(Column.class);
		if (column == null) {
			return "";
		}
		if (column.unique()) {
			query.append(UNIQUE_CONSTRAINT_QUERY);
		}
		if (!column.nullable()) {
			query.append(NOT_NULL_CONSTRAINT_QUERY);
		}
		return query.toString();
	}
}
