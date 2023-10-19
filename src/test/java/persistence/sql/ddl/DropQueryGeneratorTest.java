package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.metadata.FixtureClass;

class DropQueryGeneratorTest {

	@Test
	void getDropQuery() {
		DropQueryGenerator dropQueryGenerator = new DropQueryGenerator(FixtureClass.class);

		String dropQuery = dropQueryGenerator.getDropQuery();

		assertEquals("DROP TABLE fixture;", dropQuery);
	}
}
