package persistence.sql.ddl.definition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.SqlType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ColumnDefinitionTest {

    @Entity
    private static class ColumnDefinitionTestEntity {

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
    @DisplayName("Column의 name은 1. @Column(name=..) 2. 필드명 으로 생성된다")
    void shouldCreateColumnName() throws Exception {
        ColumnDefinition column1 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column1"));
        ColumnDefinition column2 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column2"));
        ColumnDefinition column3 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column3"));

        assertAll(
                () -> assertThat(column1.name()).isEqualTo("column1"),
                () -> assertThat(column2.name()).isEqualTo("column"),
                () -> assertThat(column3.name()).isEqualTo("column3")
        );
    }

    @Test
    @DisplayName("Column의 type은 SqlType으로 생성된다")
    void shouldCreateColumnType() throws Exception {
        ColumnDefinition column3 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column3"));
        ColumnDefinition column4 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column4"));
        ColumnDefinition column5 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column5"));

        assertAll(
                () -> assertThat(column3.type()).isEqualTo(SqlType.VARCHAR),
                () -> assertThat(column4.type()).isEqualTo(SqlType.BIGINT),
                () -> assertThat(column5.type()).isEqualTo(SqlType.INTEGER)
        );

    }

    @Test
    @DisplayName("Nullable은 1.@Column(nullable=..) 2. true로 생성된다")
    void shouldCreateNullable() throws Exception {
        ColumnDefinition column1 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column1"));
        ColumnDefinition column4 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column4"));

        assertAll(
                () -> assertThat(column1.nullable()).isTrue(),
                () -> assertThat(column4.nullable()).isFalse()
        );
    }

    @Test
    @DisplayName("Column의 length는 1. @Column(length=..) 2. DEFAULT_LENGTH로 생성된다")
    void shouldCreateColumnLength() throws Exception {
        ColumnDefinition column1 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column1"));
        ColumnDefinition column3 = new ColumnDefinition(ColumnDefinitionTestEntity.class.getDeclaredField("column3"));

        assertAll(
                () -> assertThat(column1.length()).isEqualTo(255),
                () -> assertThat(column3.length()).isEqualTo(100)
        );
    }
}
