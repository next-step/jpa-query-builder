package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectQueryBuilderTest {
	private final Person person = new Person(1L, "hhhhhwi", 1, "aab555586@gmail.com", 0);

	@DisplayName("Person 객체로 SELECT 쿼리 생성 테스트")
	@Test
	void test_buildQuery() {
		assertEquals(
				new SelectQueryBuilder(Person.class, new WhereClauseBuilder(person)).buildQuery(),
				"SELECT * FROM users WHERE id = 1 AND nick_name = 'hhhhhwi' AND old = 1 AND email = 'aab555586@gmail.com';"
		);

	}

	@DisplayName("Person 객체로 전체 SELECT 쿼리 생성 테스트")
	@Test
	void test_buildFindByIdQuery() {
		assertEquals(new SelectQueryBuilder(Person.class, null).buidFindAllQuery(),
				"SELECT * FROM users;"
		);
	}

	@DisplayName("Person 객체로 PK 조건의 SELECT 쿼리 생성 테스트")
	@Test
	void test_buildFindAllQuery() {
		assertEquals(
				new SelectQueryBuilder(Person.class, new WhereClauseBuilder(person)).buildFindByIdQuery(),
				"SELECT * FROM users WHERE id = 1;"
		);
	}

}
