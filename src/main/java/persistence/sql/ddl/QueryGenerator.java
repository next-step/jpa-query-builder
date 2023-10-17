package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class QueryGenerator {

	public String createQuery() {
		Class<Person> personClass = Person.class;
		StringBuilder query = new StringBuilder();
		Table table = personClass.getAnnotation(Table.class);
		if (table.name().isBlank()) {
			query.append("CREATE TABLE ");
			query.append(personClass.getName()).append(" ");
			query.append("(\n");
		}
		if (!table.name().isBlank()) {
			query.append("CREATE TABLE ");
			query.append(table.name()).append(" ");
			query.append("(\n");
		}

		Field[] declaredFields = personClass.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				query.append(addPrimaryKeyQuery(field, annotation));
				query.append(addColumnQuery(field, annotation));
			}
			query.append(",\n");
		}
		query.append(");");
		return query.toString();
	}

	private String addColumnQuery(Field field, Annotation annotation) {
		StringBuilder stringBuilder = new StringBuilder();
		if (annotation.annotationType().equals(Column.class)) {
			if (!field.getAnnotation(Column.class).name().isBlank()) {
				String name = field.getAnnotation(Column.class).name();
				stringBuilder.append(name).append(" ");
			}
			if (field.getAnnotation(Column.class).name().isBlank()) {
				stringBuilder.append(field.getName()).append(" ");
			}

			if (field.getType().equals(String.class)) {
				stringBuilder.append("VARCHAR(")
					.append(field.getAnnotation(Column.class).length())
					.append(")");
				return stringBuilder.toString();
			}
			if (annotation.annotationType().equals(Column.class) && (field.getType().equals(Integer.class))) {
				stringBuilder.append("INT");
			}
			if (annotation.annotationType().equals(Column.class) && (field.getType().equals(Long.class))) {
				stringBuilder.append("BIGINT");
			}
		}
		return stringBuilder.toString();
	}

	// 메서드명 수정해야함.
	private String addPrimaryKeyQuery(Field field, Annotation annotation) {
		StringBuilder stringBuilder = new StringBuilder();
		if (annotation.annotationType().equals(Id.class)) {
			if (!field.isAnnotationPresent(Column.class)) {
				stringBuilder.append("id BIGINT ");
			}
			if (field.isAnnotationPresent(Column.class)) {
				String name = field.getAnnotation(Column.class).name();
				stringBuilder.append(name);
			}
			if (field.isAnnotationPresent(GeneratedValue.class)) {
				stringBuilder.append("AUTO_INCREMENT ");
			}
			stringBuilder.append("PRIMARY KEY");
		}
		return stringBuilder.toString();
	}
}
