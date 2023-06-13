package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ColumnBuilderTest {

    @Test
    @DisplayName("Integer 타입은 BIGINT 로 변환된다.")
    void integerToColumn() throws NoSuchFieldException {
        final ColumnBuilder builder = new ColumnBuilder(
                Person.class.getDeclaredField("age")
        );
        assertThat(builder.build())
                .isEqualTo("age BIGINT");
    }

    @Test
    @DisplayName("Long 타입은 BIGINT 로 변환된다.")
    void longToColumn() throws NoSuchFieldException {
        final ColumnBuilder builder = new ColumnBuilder(
                Person.class.getDeclaredField("id")
        );
        assertThat(builder.build())
                .isEqualTo("id BIGINT PRIMARY KEY");
    }

    @Test
    @DisplayName("String 타입은 TEXT 로 변환된다.")
    void stringToColumn() throws NoSuchFieldException {
        final ColumnBuilder builder = new ColumnBuilder(
                Person.class.getDeclaredField("name")
        );
        assertThat(builder.build())
                .isEqualTo("name TEXT");
    }
}
