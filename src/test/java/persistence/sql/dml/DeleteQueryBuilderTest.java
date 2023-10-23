package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeleteQueryBuilderTest {

	@DisplayName("Person 객체로 DELETE 쿼리 생성 테스트")
	@Test
	void test_buildQuery() throws Exception {
		assertEquals(new DeleteQueryBuilder(Person.class, List.of("name", "age"), List.of("hhhhhwi", "1")).buildQuery(), "DELETE FROM users WHERE nick_name = 'hhhhhwi' AND old = 1;");
	}
}
