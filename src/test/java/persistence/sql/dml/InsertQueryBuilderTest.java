package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.QueryValidator;

import static org.junit.jupiter.api.Assertions.*;

class InsertQueryBuilderTest {
	@Test
	void buildQuery() {
		Person person = new Person("hhhhhwi", 1, "aab555586@gmail.com", 0);
		assertEquals(new InsertQueryBuilder(new QueryValidator(), person).buildQuery(), "INSERT INTO users (nick_name, old, email) VALUES ('hhhhhwi',1,'aab555586@gmail.com');");
	}
}