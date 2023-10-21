package persistence.sql.ddl.metadata;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.fixture.FixtureClass;

class H2EntityFieldTypeTest {

	@Test
	@DisplayName("필드 타입에 맞는 데이터 타입을 반환한다.")
	void getFieldType() throws NoSuchFieldException {
		EntityFieldType entityFieldType = new H2EntityFieldType();
		Field field = FixtureClass.class.getDeclaredField("name");

		assertEquals("VARCHAR(255)", entityFieldType.getDataType(field));
	}
}
