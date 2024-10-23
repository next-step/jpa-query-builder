package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnDefinitionsTest {
    @DisplayName("테이블 컬럼을 생성 한다.")
    @Test
    void createColumn() {
        final ColumnDefinitionFactory columnDefinitions = new ColumnDefinitionFactory(Person.class, new H2Dialect());
        assertEquals(expected(), columnDefinitions.create());
    }

    private List<ColumnDefinition> expected() {
        return List.of(
                new ColumnDefinition("id", "BIGINT", " AUTO_INCREMENT", "", " PRIMARY KEY"),
                new ColumnDefinition("nick_name", "VARCHAR(255)", "", "", ""),
                new ColumnDefinition("old", "INTEGER", "", "", ""),
                new ColumnDefinition("email", "VARCHAR(255)", "", " NOT NULL", "")
        );
    }
}
