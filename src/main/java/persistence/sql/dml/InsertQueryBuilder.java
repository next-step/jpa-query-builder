package persistence.sql.dml;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.persistence.Transient;
import persistence.sql.column.Columns;
import persistence.sql.column.IdColumn;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Dialect;

public class InsertQueryBuilder {

	private static final String INSERT_QUERY_FORMAT = "insert into %s (%s) values (%s)";
	private static final String COMMA = ", ";
	private static final String QUOTES = "'";

	private final Dialect dialect;

	public InsertQueryBuilder(Dialect dialect) {
		this.dialect = dialect;
	}

	public String build(Object entity) {
		Class<?> clazz = entity.getClass();
		TableColumn tableColumn = new TableColumn(clazz);
		Columns columns = new Columns(entity.getClass().getDeclaredFields(), dialect);
		return String.format(INSERT_QUERY_FORMAT,
			tableColumn.getName(),
			columns.getColumnNames(),
			valueClause(clazz.getDeclaredFields(), entity, dialect)
		);
	}

	private String valueClause(Field[] fields, Object entity, Dialect dialect) {
		return Arrays.stream(fields)
			.filter(field -> !field.isAnnotationPresent(Transient.class))
			.filter(dialect::isNotAutoIncrement)
			.map(field -> getFieldValue(entity, field))
			.collect(Collectors.joining(COMMA));
	}

	private String getFieldValue(Object entity, Field field) {
		Object value = null;
		field.setAccessible(true);
		try {
			value = field.get(entity);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		if (value instanceof String) {
			return QUOTES + value + QUOTES;
		}
		return String.valueOf(value);
	}
}
