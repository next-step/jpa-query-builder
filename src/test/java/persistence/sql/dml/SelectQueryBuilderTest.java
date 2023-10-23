package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectQueryBuilderTest {
	@DisplayName("Person 객체로 SELECT 쿼리 생성 테스트")
	@Test
	void test_buildQuery() throws Exception {
		assertEquals(new SelectQueryBuilder(Person.class, List.of("name", "age"), List.of("hhhhhwi", "1")).buildQuery(), "SELECT * FROM users WHERE nick_name = 'hhhhhwi' AND old = 1;");

	}

	@DisplayName("Person 객체로 전체 SELECT 쿼리 생성 테스트")
	@Test
	void test_findAll() throws Exception {
		assertEquals(new SelectQueryBuilder(Person.class, new ArrayList<>(), new ArrayList<>()).buildQuery(), "SELECT * FROM users;");
	}

	@DisplayName("Person 객체로 PK 조건의 SELECT 쿼리 생성 테스트")
	@Test
	void test_findById() throws Exception {
		assertEquals(new SelectQueryBuilder(Person.class, List.of("id"), List.of("1")).buildQuery(), "SELECT * FROM users WHERE id = 1;");
	}

}
