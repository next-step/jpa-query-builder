package persistence.sql.dml;

import persistence.sql.ddl.Columns;

import java.util.List;
import java.util.stream.Collectors;

public class Values {
	private final List<Value> values;

	public Values(List<Value> values) {
		this.values = values;
	}

	public void addValue(Value value) {
		if (value.checkPossibleToAdd()) {
			values.add(value);
		}
	}

	public String valueClause() {
		return values.stream()
				.map(Value::getValue)
				.collect(Collectors.joining(","));
	}

	public String columnsClause() {
		return new Columns(
				values.stream()
				.map(Value::getColumn)
				.collect(Collectors.toList())
			).buildColumnsToInsert();
	}

}
