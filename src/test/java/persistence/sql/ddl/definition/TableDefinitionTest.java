package persistence.sql.ddl.definition;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TableDefinitionTest {

    @Entity
    @Table(name = "test_table")
    private static class TableDefinitionTestEntity {
        @Id
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(length = 100)
        private String address;

        @Column(nullable = false)
        private Long notNullColumn;

        @Transient
        private Integer transientColumn;

    }

    @Test
    @DisplayName("Class 정보를 이용해서 Table Definition을 생성한다.")
    void shouldCreateTableDefinition() throws Exception {
        TableDefinition tableDefinition = new TableDefinition(TableDefinitionTestEntity.class);

        assertAll(
                () -> assertThat(tableDefinition.tableName()).isEqualTo("test_table"),
                () -> assertThat(tableDefinition.queryableColumns()).hasSize(4),
                () -> assertThat(tableDefinition.tableId().name()).isEqualTo("id")
        );
    }
}
