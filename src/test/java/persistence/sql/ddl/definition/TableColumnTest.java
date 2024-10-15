package persistence.sql.ddl.definition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Dialect;
import persistence.sql.H2Dialect;
import persistence.sql.definition.TableColumn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TableColumnTest {

    @Entity
    private static class TableColumnTestEntity {

        @Id
        private Long id;

        private String column1;

        @Column(name = "column")
        private String column2;

        @Column(length = 100)
        private String column3;

        @Column(nullable = false)
        private Long column4;

        private Integer column5;
    }

    @Test
    @DisplayName("Column에 맞게 적절하게 query를 생성한다.")
    void shouldCreateQuery() throws Exception {
        TableColumn tableColumn1 = new TableColumn(TableColumnTestEntity.class.getDeclaredField("column1"));
        TableColumn tableColumn2 = new TableColumn(TableColumnTestEntity.class.getDeclaredField("column2"));
        TableColumn tableColumn3 = new TableColumn(TableColumnTestEntity.class.getDeclaredField("column3"));
        TableColumn tableColumn4 = new TableColumn(TableColumnTestEntity.class.getDeclaredField("column4"));
        TableColumn tableColumn5 = new TableColumn(TableColumnTestEntity.class.getDeclaredField("column5"));

        StringBuilder query1 = new StringBuilder();
        StringBuilder query2 = new StringBuilder();
        StringBuilder query3 = new StringBuilder();
        StringBuilder query4 = new StringBuilder();
        StringBuilder query5 = new StringBuilder();

        Dialect dialect = new H2Dialect();

        tableColumn1.applyToCreateQuery(query1, dialect);
        tableColumn2.applyToCreateQuery(query2, dialect);
        tableColumn3.applyToCreateQuery(query3, dialect);
        tableColumn4.applyToCreateQuery(query4, dialect);
        tableColumn5.applyToCreateQuery(query5, dialect);

        // Then
        assertAll(
                () -> assertThat(query1.toString()).isEqualTo("column1 VARCHAR(255), "),
                () -> assertThat(query2.toString()).isEqualTo("column VARCHAR(255), "),
                () -> assertThat(query3.toString()).isEqualTo("column3 VARCHAR(100), "),
                () -> assertThat(query4.toString()).isEqualTo("column4 BIGINT NOT NULL, "),
                () -> assertThat(query5.toString()).isEqualTo("column5 INTEGER, ")
        );
    }

}
