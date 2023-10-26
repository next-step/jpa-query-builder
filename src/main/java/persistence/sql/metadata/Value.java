package persistence.sql.metadata;

import java.lang.reflect.Field;

public class Value {
	private final Column column;

	private final String value;

	public Value(Field field, Object object) {
		this.column = new Column(field);
		try {
			field.setAccessible(true);
			this.value = convertValueToString(field, String.valueOf(field.get(object)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Value(Field field, String value) {
		this.column = new Column(field);
		this.value = convertValueToString(field, value);
	}

	public Column getColumn() {
		return column;
	}

	public String getValue() {
		return value;
	}

	public boolean checkPossibleToInsert() {
		return column.checkPossibleToInsert();
	}

	private String convertValueToString(Field field, String value) {
		if(field.getType().equals(String.class) && !"null".equals(value)) {
			value = "'" + value + "'";
		}

		return value;
	}
}
