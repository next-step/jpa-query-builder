package persistence.sql.dialect.h2;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.ColumnDialect;

import static org.assertj.core.api.Assertions.assertThat;

class H2ColumnDialectTest {
    private ColumnDialect dialect;

    @BeforeEach
    void setUp() {
        dialect = H2ColumnDialect.getInstance();
    }

    @Test
    @DisplayName("id 를 Column 으로 변환한다.")
    void idToColumn() throws NoSuchFieldException {
        final String actual = dialect.getSqlColumn(
                Person.class.getDeclaredField("id")
        );
        assertThat(actual).isEqualTo("id BIGINT AUTO_INCREMENT PRIMARY KEY");
    }

    @Test
    @DisplayName("name 를 Column 으로 변환한다.")
    void nameToColumn() throws NoSuchFieldException {
        final String actual = dialect.getSqlColumn(
                Person.class.getDeclaredField("name")
        );
        assertThat(actual).isEqualTo("nick_name VARCHAR(255)");
    }

    @Test
    @DisplayName("age 를 Column 으로 변환한다. String 타입이 아니면 Column length 가 표시되어서는 안된다.")
    void ageToColumn() throws NoSuchFieldException {
        final String actual = dialect.getSqlColumn(
                Person.class.getDeclaredField("age")
        );
        assertThat(actual).isEqualTo("old INTEGER");
    }

    @Test
    @DisplayName("email 를 Column 으로 변환한다.")
    void emailToColumn() throws NoSuchFieldException {
        final String actual = dialect.getSqlColumn(
                Person.class.getDeclaredField("email")
        );
        assertThat(actual).isEqualTo("email VARCHAR(320) NOT NULL");
    }
}
