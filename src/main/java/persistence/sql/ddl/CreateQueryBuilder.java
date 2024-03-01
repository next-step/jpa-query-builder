package persistence.sql.ddl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import domain.Person;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class CreateQueryBuilder {
	Map<? extends Class<? extends Serializable>, String> javaClassTypeToDbTypes = Map.of(
		Long.class, "BIGINT",
		String.class, "VARCHAR",
		Integer.class, "BIGINT"
	);

	public String getCreateTableSql() throws
		NoSuchMethodException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {

		Class<Person> personClass = Person.class;

		Person person = personClass.getConstructor().newInstance();

		Arrays.stream(personClass.getDeclaredFields())
			.filter(x -> x.isAnnotationPresent(GeneratedValue.class))
			.forEach(x -> {
				x.setAccessible(true);
				try {
					Long value = 0L;
					if (x.getAnnotation(GeneratedValue.class).strategy().equals(GenerationType.AUTO)) {
						value = (Long)x.get(person);
						x.set(person, value + 1L);
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			});

		Arrays.stream(personClass.getDeclaredFields())
			.filter(x -> x.isAnnotationPresent(Column.class))
			.forEach(x -> {
				x.setAccessible(true);
				try {
					x.set(person, x.getAnnotation(Column.class).name());
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			});

		Arrays.stream(personClass.getDeclaredFields())
			.filter(x -> x.isAnnotationPresent(Column.class))
			.forEach(x -> {
				x.setAccessible(true);
				try {
					if (!x.getAnnotation(Column.class).nullable()) {
						if (x.get(person) == null) {
							throw new RuntimeException();
						}
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			});

		String tableName = personClass.getSimpleName();

		List<? extends Class<?>> fieldTypes = Arrays.stream(personClass.getDeclaredFields()).map(Field::getType).toList();

		List<String> fieldNames = Arrays.stream(personClass.getDeclaredFields()).map(Field::getName).toList();

		String createTableSql = String.format("create table %s(%s %s, %s %s, %s %s)",
			tableName, fieldNames.get(0), javaClassTypeToDbTypes.get(fieldTypes.get(0)),
			fieldNames.get(1), javaClassTypeToDbTypes.get(fieldTypes.get(1)),
			fieldNames.get(2), javaClassTypeToDbTypes.get(fieldTypes.get(2))
		);

		return createTableSql;
	}
}
