package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.fixture.FixtureClass;

class EntityColumnTest {

	@DisplayName("입력 받은 필드의 컬럼 쿼리를 반환한다.")
	@ParameterizedTest
	@CsvSource({
		"id, id BIGINT AUTO_INCREMENT PRIMARY KEY",
		"name, name VARCHAR(255)",
		"uniqueColumn, uniqueColumn VARCHAR(255) UNIQUE",
		"notNullColumn, notNullColumn VARCHAR(255) NOT NULL",
		"uniqueNotNullColumn, uniqueNotNullColumn VARCHAR(255) UNIQUE NOT NULL"
	})
	void getColumnQuery(String input, String expected) throws NoSuchFieldException {
		Field field = FixtureClass.class.getDeclaredField(input);
		EntityColumn entityColumn = new EntityColumn(field);

		String result = entityColumn.getColumnQuery();

		assertEquals(expected, result);
	}

	@DisplayName("id 필드를 가지고 있는지 확인한다.")
	@ParameterizedTest
	@CsvSource({
		"id, true",
		"name, false",
	})
	void hasId(String id, boolean expected) throws NoSuchFieldException {
		Field field = FixtureClass.class.getDeclaredField(id);
		EntityColumn entityColumn = new EntityColumn(field);

		boolean result = entityColumn.hasId();

		assertEquals(expected, result);
	}
}
