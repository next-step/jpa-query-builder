package persistence.sql.ddl.h2;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class H2DdlBuilderTest {
    private DdlBuilder ddl;

    @BeforeEach
    void setUp() {
        ddl = H2DdlBuilder.getInstance();
    }

    @Test
    @DisplayName("Person Entity 를 위한 CREATE 쿼리를 생성한다.")
    void createQuery() {
        String expected = "CREATE TABLE users ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "nick_name VARCHAR(255), "
                + "old INTEGER, "
                + "email VARCHAR(320) NOT NULL"
                + ")";
        assertThat(
                ddl.getCreateQuery(Person.class)
        ).isEqualTo(expected);
    }

    @Test
    @DisplayName("Person Entity 를 위한 drop 쿼리를 생성한다.")
    void dropQuery() {
        final String expected = "DROP TABLE IF EXISTS users";
        assertThat(
                ddl.getDropQuery(Person.class)
        ).isEqualTo(expected);
    }
}
