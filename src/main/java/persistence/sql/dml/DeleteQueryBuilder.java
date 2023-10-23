package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import java.util.List;

import static java.lang.String.format;

public class DeleteQueryBuilder implements QueryBuilder {
	private final static String DELETE_COMMAND = "DELETE FROM %s;";

	private final EntityMetadata entityMetadata;

	private final String whereClause;

	public DeleteQueryBuilder(Class<?> clazz, List<String> whereColumns, List<String> whereValues) throws Exception {
		this.entityMetadata = new EntityMetadata(clazz);
		this.whereClause = new WhereClauseBuilder(clazz, whereColumns, whereValues).buildClause();
	}

	@Override
	public String buildQuery() {
		return format(DELETE_COMMAND, entityMetadata.getTableName() + whereClause);
	}
}
