package persistence.sql.ddl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.reflect.Field;

import domain.Person;

public class CreateQueryBuilder {
	Map<? extends Class<? extends Serializable>, String> javaClassTypeToDbTypes = Map.of(
		Long.class, "BIGINT",
		String.class, "VARCHAR",
		Integer.class, "BIGINT"
	);

	public String getCreateTableSql(Class<?> clazz) {
		Class<?> person = clazz;
		String tableName = person.getSimpleName();

		List<? extends Class<?>> fieldTypes = Arrays.stream(person.getDeclaredFields()).map(Field::getType).toList();

		List<String> fieldNames = Arrays.stream(person.getDeclaredFields()).map(Field::getName).toList();

		String createTableSql = String.format("create table %s(%s %s, %s %s, %s %s)",
			tableName, fieldNames.get(0), javaClassTypeToDbTypes.get(fieldTypes.get(0)),
			fieldNames.get(1), javaClassTypeToDbTypes.get(fieldTypes.get(1)),
			fieldNames.get(2), javaClassTypeToDbTypes.get(fieldTypes.get(2))
		);

		return createTableSql;
	}
}
