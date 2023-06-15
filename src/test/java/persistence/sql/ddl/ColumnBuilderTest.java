package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnBuilderTest {

    @Test
    @DisplayName("id 를 Column 으로 변환한다.")
    void idToColumn() throws NoSuchFieldException {
        final String actual = new ColumnBuilder(
                Person.class.getDeclaredField("id")
        ).build();
        assertThat(actual)
                .isEqualTo("id BIGINT AUTO_INCREMENT PRIMARY KEY");
    }

    @Test
    @DisplayName("name 를 Column 으로 변환한다.")
    void nameToColumn() throws NoSuchFieldException {
        final String actual = new ColumnBuilder(
                Person.class.getDeclaredField("name")
        ).build();
        assertThat(actual)
                .isEqualTo("nick_name VARCHAR(255)");
    }

    @Test
    @DisplayName("age 를 Column 으로 변환한다. String 타입이 아니면 Column length 가 표시되어서는 안된다.")
    void ageToColumn() throws NoSuchFieldException {
        final String actual = new ColumnBuilder(
                Person.class.getDeclaredField("age")
        ).build();
        assertThat(actual)
                .isEqualTo("old INTEGER");
    }

    @Test
    @DisplayName("email 를 Column 으로 변환한다.")
    void emailToColumn() throws NoSuchFieldException {
        final String actual = new ColumnBuilder(
                Person.class.getDeclaredField("email")
        ).build();
        assertThat(actual)
                .isEqualTo("email VARCHAR(320) NOT NULL");
    }
}
