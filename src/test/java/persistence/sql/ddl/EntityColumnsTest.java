package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.fixture.FixtureClass;

class EntityColumnsTest {

	@Test
	@DisplayName("입력 받은 필드의 컬럼 쿼리를 반환한다.")
	void getColumnQuery() {
		EntityColumns entityColumns = new EntityColumns(FixtureClass.class);
		String result = entityColumns.getColumnQuery();

		String expected = "id BIGINT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(255),uniqueColumn VARCHAR(255) UNIQUE,notNullColumn VARCHAR(255) NOT NULL,uniqueNotNullColumn VARCHAR(255) UNIQUE NOT NULL";
		assertEquals(expected, result);
	}

	@Test
	@DisplayName("Transient 어노테이션이 붙은 필드는 컬럼 쿼리를 반환하지 않는다.")
	void getColumnQueryReturnNotTransient() {
		EntityColumns entityColumns = new EntityColumns(FixtureClass.class);
		String result = entityColumns.getColumnQuery();

		assertEquals(result.contains("transientColumn"), false);
	}

	@Test
	@DisplayName("PRIMARY KEY가 없을 경우, 예외를 반환한다.")
	void getColumnQueryThrowException() {
		String errorMessage = assertThrows(IllegalArgumentException.class, () -> new EntityColumns(FixtureClassNotHavePrimaryKey.class))
			.getMessage();

		assertEquals("primary key는 하나만 존재해야 합니다.", errorMessage);
	}

	private class FixtureClassNotHavePrimaryKey {
		private Long id;
	}
}
