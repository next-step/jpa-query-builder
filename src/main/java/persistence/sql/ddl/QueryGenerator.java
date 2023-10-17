package persistence.sql.ddl;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QueryGenerator {

	private static final String CREATE_TABLE = "CREATE TABLE %s (%s\n);";
	private static final String DROP_TABLE = "DROP TABLE %s;";

	public String getCreateQuery() {
		Class<Person> personClass = Person.class;
		String tableName = getTableName(personClass);
		getColumn(personClass);
		return String.format(CREATE_TABLE, tableName, getColumn(personClass));
	}

	private String getTableName(Class<Person> aClass) {
		String tableName = aClass.getAnnotation(Table.class).name();
		if (tableName.isEmpty()) {
			return aClass.getSimpleName();
		}
		return tableName;
	}

	public String getColumn(Class<Person> aClass) {
		return Arrays.stream(aClass.getDeclaredFields())
			.filter(field -> !field.isAnnotationPresent(Transient.class))
			.map(field -> "\n" + new EntityColumn(field).getColumnQuery())
			.collect(Collectors.joining(","));
	}

	public String getDropQuery() {
		Class<Person> personClass = Person.class;
		String tableName = getTableName(personClass);
		return String.format(DROP_TABLE, tableName);
	}
}
