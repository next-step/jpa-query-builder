package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class SelectQueryBuilder implements QueryBuilder {
	private final static String SELECT_COMMAND = "SELECT %s FROM %s;";

	private final EntityMetadata entityMetadata;

	private final WhereClauseBuilder whereClauseBuilder;

	public SelectQueryBuilder(Class<?> clazz, WhereClauseBuilder whereClauseBuilder) {
		this.entityMetadata = new EntityMetadata(clazz);
		this.whereClauseBuilder = whereClauseBuilder;
	}
	@Override
	public String buildQuery() {
		return format(SELECT_COMMAND, "*", entityMetadata.getTableName() + whereClauseBuilder.buildClause());
	}

	public String buildFindByIdQuery() {
		return format(SELECT_COMMAND, "*", entityMetadata.getTableName() + whereClauseBuilder.buildPKClause());
	}

	public String buidFindAllQuery() {
		return format(SELECT_COMMAND, "*", entityMetadata.getTableName());
	}

}
