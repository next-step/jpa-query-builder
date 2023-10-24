package persistence.sql.dml;

import persistence.sql.metadata.Value;
import persistence.sql.metadata.Values;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class WhereClauseBuilder {
	private final static String WHERE_CLAUSE = " WHERE %s";

	private final Values values;

	public WhereClauseBuilder(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		List<Value> valueList = Arrays.stream(fields)
				.map(x -> new Value(x, object))
				.filter(x -> !x.getColumn().isTransient())
				.collect(Collectors.toList());

		this.values = new Values(valueList);
	}

	public WhereClauseBuilder(Values values) {
		this.values = values;
	}

	public String buildClause() {
		if(values.buildWhereClause().isEmpty()) {
			return "";
		}

		return format(WHERE_CLAUSE, values.buildWhereClause());
	}

	public String buildPKClause() {
		return format(WHERE_CLAUSE, values.buildWherePKClause());
	}
}
