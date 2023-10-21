package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.QueryValidator;
import persistence.sql.ddl.Table;

import static java.lang.String.format;

public class SelectQueryBuilder implements QueryBuilder {
	private final static String SELECT_COMMAND = "SELECT %s FROM %s;";

	private QueryValidator queryValidator;

	private final Table table;

	public SelectQueryBuilder(QueryValidator queryValidator, Class<?> clazz) {
		this.queryValidator = queryValidator;
		queryValidator.checkIsEntity(clazz);
		this.table = new Table(clazz);
	}

	@Override
	public String buildQuery() {
		return null;
	}

	public String findAll() {
		return format(SELECT_COMMAND, "*", table.getName());
	}
}
