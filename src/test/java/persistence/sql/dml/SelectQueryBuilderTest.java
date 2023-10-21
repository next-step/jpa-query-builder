package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.QueryValidator;

import static org.junit.jupiter.api.Assertions.*;

class SelectQueryBuilderTest {
	private final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(new QueryValidator(), Person.class);

	@Test
	void test_findAll() {
		assertEquals(selectQueryBuilder.findAll(), "SELECT * FROM users;");
	}
}