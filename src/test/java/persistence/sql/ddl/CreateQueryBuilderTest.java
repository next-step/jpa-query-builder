package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

class CreateQueryBuilderTest {
	@Test
	void createDDL() throws
		InvocationTargetException,
		NoSuchMethodException,
		InstantiationException,
		IllegalAccessException {
		CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder();
		String createTableSql = createQueryBuilder.getCreateTableSql();

		assertEquals(createTableSql, "CREATE TABLE person (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50) NOT NULL, age INT)");
	}
}
