package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.fixture.FixtureClass;

class DropQueryGeneratorTest {

	@Test
	@DisplayName("drop 쿼리를 반환한다.")
	void getDropQuery() {
		DropQueryGenerator dropQueryGenerator = new DropQueryGenerator(FixtureClass.class);

		String dropQuery = dropQueryGenerator.getDropQuery();

		assertEquals("DROP TABLE fixture;", dropQuery);
	}
}
