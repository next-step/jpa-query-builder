package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CreateQueryBuilderTest {

    @Test
    @DisplayName("Person Entity 를 위한 CREATE 쿼리를 생성한다.")
    void build() {
        String expected = "CREATE TABLE users ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "nick_name VARCHAR(255), "
                + "old INTEGER, "
                + "email VARCHAR(320) NOT NULL"
                + ")";
        String actual = new CreateQueryBuilder<>(
                Person.class
        ).build();
        assertThat(actual).isEqualTo(expected);
    }
}
