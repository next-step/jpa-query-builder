package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class EntityColumn {

	private final Field field;
	private final boolean isPrimaryKey;

	public EntityColumn(Field field) {
		this.field = field;
		this.isPrimaryKey = isPrimaryKey(field);
	}

	public String getColumnQuery() {
		StringBuilder query = new StringBuilder();
		if (isPrimaryKey) {
			query.append(getPrimaryKeyQuery());
			query.append(getColumnMetaData());
			return query.toString();
		}
		query.append(getColumnName());
		query.append(getColumnMetaData());
		return query.toString();
	}

	private String getColumnName() {
		Column annotation = field.getAnnotation(Column.class);
		if (annotation == null || annotation.name().isEmpty()) {
			return field.getName() + " ";
		}
		return annotation.name() + " ";
	}

	private boolean isPrimaryKey(Field field) {
		return field.isAnnotationPresent(Id.class);
	}

	private String getColumnMetaData() {
		StringBuilder query = new StringBuilder();
		Column column = field.getAnnotation(Column.class);
		if (column != null) {
			query.append(getDataType(field));
			query.append(getNullableMetaData(column));
			query.append(getUniqueMetaData(column));
		}
		return query.toString();
	}

	private String getPrimaryKeyQuery() {
		StringBuilder query = new StringBuilder();
		query.append(getColumnName());
		query.append(getDataType(field));
		if (field.isAnnotationPresent(GeneratedValue.class)) {
			query.append(" AUTO_INCREMENT");
		}
		query.append(" PRIMARY KEY");
		return query.toString();
	}

	private String getDataType(Field field) {
		if (field.getType().equals(Long.class)) {
			return "BIGINT";
		}
		if (field.getType().equals(Integer.class)) {
			return "INT";
		}
		if (field.getType().equals(String.class)) {
			Column column = field.getAnnotation(Column.class);
			return String.format("VARCHAR(%d)", column.length());
		}
		throw new IllegalArgumentException("지원하지 않는 타입입니다.");
	}

	private static String getUniqueMetaData(Column column) {
		if (column.unique()) {
			return " UNIQUE";
		}
		return "";
	}

	private String getNullableMetaData(Column column) {
		if (!column.nullable()) {
			return " NOT NULL";
		}
		return "";
	}
}
