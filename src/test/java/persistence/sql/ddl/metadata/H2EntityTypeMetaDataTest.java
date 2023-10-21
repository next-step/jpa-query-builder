package persistence.sql.ddl.metadata;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import persistence.sql.fixture.FixtureClass;

class H2EntityTypeMetaDataTest {

	@Test
	void getMetaDateQueryReturnEmptyString() throws NoSuchFieldException {
		H2EntityTypeMetaData h2EntityIdMetaData = new H2EntityTypeMetaData();
		Field field = FixtureClass.class.getDeclaredField("id");

		String result = h2EntityIdMetaData.getMetaDateQuery(field);

		assertEquals( "", result);
	}

	@Test
	void getMetaDateQueryReturnUniqueValue() throws NoSuchFieldException {
		H2EntityTypeMetaData h2EntityIdMetaData = new H2EntityTypeMetaData();
		Field field = FixtureClass.class.getDeclaredField("uniqueColumn");

		String result = h2EntityIdMetaData.getMetaDateQuery(field);

		assertEquals( " UNIQUE", result);
	}

	@Test
	void getMetaDateQueryReturnNotNullValue() throws NoSuchFieldException {
		H2EntityTypeMetaData h2EntityIdMetaData = new H2EntityTypeMetaData();
		Field field = FixtureClass.class.getDeclaredField("notNullColumn");

		String result = h2EntityIdMetaData.getMetaDateQuery(field);

		assertEquals( " NOT NULL", result);
	}

	@Test
	void getMetaDateQueryReturnUniqueAndNotNullValue() throws NoSuchFieldException {
		H2EntityTypeMetaData h2EntityIdMetaData = new H2EntityTypeMetaData();
		Field field = FixtureClass.class.getDeclaredField("uniqueNotNullColumn");

		String result = h2EntityIdMetaData.getMetaDateQuery(field);

		assertEquals( " UNIQUE NOT NULL", result);
	}
}
