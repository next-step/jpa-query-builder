package persistence.sql.ddl.metadata;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class H2EntityIdMetaDataTest {

	@Test
	@DisplayName("Id가 있으면 PRIMARY KEY를 반환한다.")
	void getMetaDateQueryReturnValue() throws NoSuchFieldException {
		H2EntityIdMetaData h2EntityIdMetaData = new H2EntityIdMetaData();
		Field field = FixtureClass.class.getDeclaredField("id");

		String result = h2EntityIdMetaData.getMetaDateQuery(field);

		assertEquals( " PRIMARY KEY", result);
	}

	@Test
	@DisplayName("Id가 없으면 빈 문자열을 반환한다.")
	void getMetaDateQueryReturnEmptyString() throws NoSuchFieldException {
		H2EntityIdMetaData h2EntityIdMetaData = new H2EntityIdMetaData();
		Field field = FixtureClass.class.getDeclaredField("name");

		String result = h2EntityIdMetaData.getMetaDateQuery(field);

		assertEquals( "", result);
	}
}
