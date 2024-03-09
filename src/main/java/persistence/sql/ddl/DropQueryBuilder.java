package persistence.sql.ddl;

import domain.Person;

public class DropQueryBuilder {
	public String getDropTableSql() {
		Class<Person> personClass = Person.class;

		String tableName = personClass.getSimpleName();
		// DROP TABLE Person
		String dropTableSql = String.format("drop table %s", tableName);

		return dropTableSql;
	}


}
