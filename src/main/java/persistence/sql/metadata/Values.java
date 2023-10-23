package persistence.sql.metadata;

import java.util.List;
import java.util.stream.Collectors;

public class Values {
	private final List<Value> values;

	public Values(List<Value> values) {
		this.values = values;
	}

	public String buildValueClause() {
		return values.stream()
				.map(Value::getValue)
				.collect(Collectors.joining(","));
	}

	public String buildColumnsClause() {
		return new Columns(
				values.stream()
				.map(Value::getColumn)
				.collect(Collectors.toList())
			).buildColumnsToInsert();
	}

	public String buildWhereClause() {
		return values.stream()
				.map(x -> x.getColumn().getName() + " = " + x.getValue())
				.collect(Collectors.joining(" AND "));
	}
}
