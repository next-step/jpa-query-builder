package persistence.sql.ddl.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class H2CreateQueryTest {

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
                H2CreateQuery.build(Person.class)
        ).isEqualTo(expected);
    }
}
