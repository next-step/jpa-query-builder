package persistence.sql.ddl.schema;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2ColumnType;


@DisplayName("ColumnMeta 테스트")
class ColumnMetaTest {

    @Test
    @DisplayName("@Column의 name이 지정되있지 않은 Field의 정보를 통해 매핑될 Column 정보를 생성할 수 있다.")
    void createColumnMetaWithNoColumnName() throws NoSuchFieldException {
        final String idFieldName = "id";
        final String indexFieldName = "index";

        final ColumnMeta idColumnMeta = ColumnMeta.of(ColumnMetaFixture.class.getDeclaredField(idFieldName), new H2ColumnType());
        final ColumnMeta indexColumnMeta = ColumnMeta.of(ColumnMetaFixture.class.getDeclaredField(indexFieldName), new H2ColumnType());

        assertAll(
            () -> assertThat(idColumnMeta.getColumnName()).isEqualTo(idFieldName),
            () -> assertThat(indexColumnMeta.getColumnName()).isEqualTo(indexFieldName)
        );
    }

    @Test
    @DisplayName("@Column의 name이 지정되있는 Field의 정보를 통해 매핑될 Column 정보를 생성할 수 있다.")
    void createColumnMetaWithColumnName() throws NoSuchFieldException {
        final String nameFieldName = "name";
        final String nameFieldColumnName = "nick_name";

        final ColumnMeta nameColumnMeta = ColumnMeta.of(ColumnMetaFixture.class.getDeclaredField(nameFieldName), new H2ColumnType());

        assertAll(
            () -> assertThat(nameColumnMeta.getColumnName()).isEqualTo(nameFieldColumnName)
        );
    }

    @Test
    @DisplayName("@Column의 Not Null 제약조건을 표현할 수 있다.")
    void createColumnMetaWithColumnNotNullConstraint() throws NoSuchFieldException {
        final String indexFieldName = "index";
        final String indexColumnWithConstraint = "index bigint NOT NULL";

        final ColumnMeta nameColumnMeta = ColumnMeta.of(ColumnMetaFixture.class.getDeclaredField(indexFieldName), new H2ColumnType());

        assertAll(
            () -> assertThat(nameColumnMeta.getColumn()).isEqualTo(indexColumnWithConstraint)
        );
    }

    @Test
    @DisplayName("@GeneratedValue의 strategy에 따라 Primary Key 채번 전략을 선택할 수 있다.")
    void createColumnMetaWithGeneratedValueStrategy() throws NoSuchFieldException {
        final String generatedValueIdFieldName = "generatedValueId";
        final String generatedValueColumnWithStrategy = "generatedvalueid bigint PRIMARY KEY generated by default as identity";

        final ColumnMeta generatedValueIdColumnMeta = ColumnMeta.of(ColumnMetaFixture.class.getDeclaredField(generatedValueIdFieldName),
            new H2ColumnType());

        assertAll(
            () -> assertThat(generatedValueIdColumnMeta.getColumn()).isEqualTo(generatedValueColumnWithStrategy)
        );
    }


    static class ColumnMetaFixture {

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
}