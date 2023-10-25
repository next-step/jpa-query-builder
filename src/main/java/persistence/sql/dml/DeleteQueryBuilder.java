package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class DeleteQueryBuilder implements QueryBuilder {
	private static final String DELETE_COMMAND = "DELETE FROM %s;";

	public DeleteQueryBuilder() {
	}

	@Override
	public String buildQuery(EntityMetadata entityMetadata) {
		return format(DELETE_COMMAND, entityMetadata.getTableName());
	}

	public String buildQuery(EntityMetadata entityMetadata, WhereClauseBuilder whereClauseBuilder) {
		return format(DELETE_COMMAND, entityMetadata.getTableName() + whereClauseBuilder.buildClause());
	}
}
