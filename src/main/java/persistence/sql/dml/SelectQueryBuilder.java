package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import java.util.List;

import static java.lang.String.format;

public class SelectQueryBuilder implements QueryBuilder {
	private final static String SELECT_COMMAND = "SELECT %s FROM %s;";

	private final EntityMetadata entityMetadata;
	private final String whereClause;

	public SelectQueryBuilder(Class<?> clazz, List<String> whereColumns, List<String> whereValues) {
		this.entityMetadata = new EntityMetadata(clazz);
		this.whereClause = new WhereClauseBuilder(clazz, whereColumns, whereValues).buildClause();
	}

	@Override
	public String buildQuery() {
		return format(SELECT_COMMAND, "*", entityMetadata.getTableName() + whereClause);
	}
}
