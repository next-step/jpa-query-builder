package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.metadata.FixtureClass;

class EntityColumnsTest {

	@Test
	@DisplayName("입력 받은 필드의 컬럼 쿼리를 반환한다.")
	void getColumnQuery() {
		EntityColumns entityColumns = new EntityColumns(FixtureClass.class);
		String result = entityColumns.getColumnQuery();

		String expected = "id BIGINT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(255),uniqueColumn VARCHAR(255) UNIQUE,notNullColumn VARCHAR(255) NOT NULL,uniqueNotNullColumn VARCHAR(255) UNIQUE NOT NULL";
		assertEquals(expected, result);
	}
}
