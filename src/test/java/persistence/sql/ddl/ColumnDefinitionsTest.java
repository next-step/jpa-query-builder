package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnDefinitionsTest {
    private static final String INDENTATION = "    ";

    @DisplayName("테이블 컬럼을 생성 한다.")
    @Test
    void createColumn() {
        final ColumnDefinitions columnDefinitions = new ColumnDefinitions(Person.class, new H2Dialect());
        assertEquals(expected(), columnDefinitions.value(Person.class));
    }

    private String expected() {
        return INDENTATION + """
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            nick_name VARCHAR(255),
            old INTEGER,
            email VARCHAR(255) NOT NULL""".replaceAll("\n", "\n" + INDENTATION);
    }
}
