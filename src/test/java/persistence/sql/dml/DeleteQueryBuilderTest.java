package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.metadata.EntityMetadata;

import static org.junit.jupiter.api.Assertions.*;

class DeleteQueryBuilderTest {
	private final Person person = new Person(1L, "hhhhhwi", 1, "aab555586@gmail.com", 0);

	private final EntityMetadata entityMetadata = new EntityMetadata(Person.class);

	@DisplayName("Person 객체로 DELETE 쿼리 생성 테스트")
	@Test
	void test_buildQuery() {
		assertEquals(
				new DeleteQueryBuilder().buildQuery(entityMetadata),
				"DELETE FROM users';"
		);
	}

	@DisplayName("Person 객체로 DELETE 쿼리 생성 테스트")
	@Test
	void test_buildQueryWithWhereClause() {
		assertEquals(
				new DeleteQueryBuilder().buildQuery(entityMetadata, new WhereClauseBuilder(person)),
				"DELETE FROM users WHERE id = 1 AND nick_name = 'hhhhhwi' AND old = 1 AND email = 'aab555586@gmail.com';"
		);
	}
}
