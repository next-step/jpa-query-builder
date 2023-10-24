package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteQueryBuilderTest {
	private final Person person = new Person(1L, "hhhhhwi", 1, "aab555586@gmail.com", 0);

	@DisplayName("Person 객체로 DELETE 쿼리 생성 테스트")
	@Test
	void test_buildQuery() {
		assertEquals(
				new DeleteQueryBuilder(Person.class, new WhereClauseBuilder(person)).buildQuery(),
				"DELETE FROM users WHERE id = 1 AND nick_name = 'hhhhhwi' AND old = 1 AND email = 'aab555586@gmail.com';"
		);
	}
}
