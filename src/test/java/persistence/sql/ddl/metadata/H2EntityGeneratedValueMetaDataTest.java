package persistence.sql.ddl.metadata;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class H2EntityGeneratedValueMetaDataTest {

	@Test
	@DisplayName("GeneratedValue이 없으면 빈 문자열을 반환한다.")
	void getMetaDateQueryReturnEmptyString() throws NoSuchFieldException {
		H2EntityGeneratedValueMetaData h2EntityGeneratedValueMetaData = new H2EntityGeneratedValueMetaData();
		Field name = FixtureClass.class.getDeclaredField("name");

		String result = h2EntityGeneratedValueMetaData.getMetaDateQuery(name);

		assertEquals("", result);
	}

	@Test
	@DisplayName("GeneratedValue이 있으면 AUTO_INCREMENT를 반환한다.")
	void getMetaDateQueryReturnValue() throws NoSuchFieldException {
		H2EntityGeneratedValueMetaData h2EntityGeneratedValueMetaData = new H2EntityGeneratedValueMetaData();
		Field name = FixtureClass.class.getDeclaredField("id");

		String result = h2EntityGeneratedValueMetaData.getMetaDateQuery(name);

		assertEquals(" AUTO_INCREMENT", result);
	}
}
