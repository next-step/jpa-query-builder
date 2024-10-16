package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnDefinitionsTest {
    private static final String INDENTATION = "    ";

    @Test
    void value() {
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
