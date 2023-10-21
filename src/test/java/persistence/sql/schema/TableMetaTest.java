package persistence.sql.schema;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TableMeta 테스트")
class TableMetaTest {

    @Test
    @DisplayName("Class의 이름을 통해 테이블 정보를 생성할 수 있다.")
    void canCreateTableMeta() {
        final String tableName = "TABLEMETAFIXTURE";
        final TableMeta tableMeta = TableMeta.of(TableMetaFixture.class);

        assertThat(tableMeta.getTableName()).isEqualTo(tableName);
    }

    @Test
    @DisplayName("@Table의 name을 통해 테이블 정보를 생성할 수 있다.")
    void canCreateTableMetaWithTableName() {
        final String tableName = "TABLE_META";
        final TableMeta tableMeta = TableMeta.of(TableMetaFixtureWithTableName.class);

        assertThat(tableMeta.getTableName()).isEqualTo(tableName);
    }

    static class TableMetaFixture {

        @Id
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(nullable = false)
        private Long index;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long generatedValueId;
    }

    @Table(name = "table_meta")
    static class TableMetaFixtureWithTableName {

        @Id
        private Long id;
    }
}