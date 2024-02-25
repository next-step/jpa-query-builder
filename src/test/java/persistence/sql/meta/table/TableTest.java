package persistence.sql.meta.table;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TableTest {

    @Test
    @DisplayName("생성/테이블 메타정보/성공")
    void createTable() {
        assertThat(new Table(Person.class)).isInstanceOf(Table.class);
    }

    @Test
    @DisplayName("생성/EntityAnnotation 없는 테이블 메타정보/IllegalArgumentException")
    void createTableFail() {
        assertThatThrownBy(() -> new Table(Person2.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Entity()
    public static class Person {
        @Id
        private Long id;
    }

    public static class Person2 {
        private Long id;
    }
}
