package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertQueryBuilderTest {
	@DisplayName("Person 객체로 INSERT 쿼리 생성 테스트")
	@Test
	void test_buildQuery() {
		Person person = new Person("hhhhhwi", 1, "aab555586@gmail.com", 0);
		assertEquals(
				new InsertQueryBuilder(person).buildQuery(),
				"INSERT INTO users (nick_name, old, email) VALUES ('hhhhhwi',1,'aab555586@gmail.com');"
		);
	}
}
