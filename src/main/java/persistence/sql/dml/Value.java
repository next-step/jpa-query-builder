package persistence.sql.dml;

import persistence.sql.ddl.Column;

import java.lang.reflect.Field;

public class Value {
	private final Column column;

	private final String value;

	public Value(Field field, Object object) {
		this.column = new Column(field);
		this.value = findValue(this.column, field, object);
	}

	public Column getColumn() {
		return column;
	}

	public String getValue() {
		return value;
	}

	public boolean checkPossibleToAdd() {
		return column.checkPossibleToAddValue();
	}

	private String findValue(Column column, Field field, Object object) {
		try {
			String value = String.valueOf(field.get(object));

			if(field.getType().equals(String.class)) {
				value = "'" + value + "'";
			}

			if(column.getConstraint().isNullable() && value.isEmpty()) {
				value = "NULL";
			}

			return value;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
}
