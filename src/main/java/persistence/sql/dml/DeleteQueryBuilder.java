package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class DeleteQueryBuilder implements QueryBuilder {
	private final static String DELETE_COMMAND = "DELETE FROM %s;";

	private final EntityMetadata entityMetadata;

	private final WhereClauseBuilder whereClauseBuilder;

	public DeleteQueryBuilder(Class<?> clazz, WhereClauseBuilder whereClauseBuilder) {
		this.entityMetadata = new EntityMetadata(clazz);
		this.whereClauseBuilder = whereClauseBuilder;
	}

	@Override
	public String buildQuery() {
		return format(DELETE_COMMAND, entityMetadata.getTableName() + whereClauseBuilder.buildClause());
	}
}
