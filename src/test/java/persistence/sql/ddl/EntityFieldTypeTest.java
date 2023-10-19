package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EntityFieldTypeTest {

	@Test
	void getFieldType() {
		EntityFieldType entityFieldType = EntityFieldType.find(String.class);

		assertEquals("VARCHAR(255)", entityFieldType.getDataType());
	}
}
