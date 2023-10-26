package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class SelectQueryBuilder implements QueryBuilder {
	private static final String SELECT_COMMAND = "SELECT %s FROM %s;";

	public SelectQueryBuilder() {
	}
	@Override
	public String buildQuery(EntityMetadata entityMetadata) {
		return format(SELECT_COMMAND, "*", entityMetadata.getTableName());
	}

	public String buildQuery(EntityMetadata entityMetadata, WhereClauseBuilder whereClauseBuilder) {
		return format(SELECT_COMMAND, "*", entityMetadata.getTableName() + whereClauseBuilder.buildClause());
	}

	public String buildFindByIdQuery(EntityMetadata entityMetadata, WhereClauseBuilder whereClauseBuilder) {
		return format(SELECT_COMMAND, "*", entityMetadata.getTableName() + whereClauseBuilder.buildPKClause());
	}
}
