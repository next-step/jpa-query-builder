package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class CreateQueryBuilderTest {

    @Test
    @DisplayName("Person Entity 를 위한 create 쿼리를 생성한다.")
    void build() {
        String expected = "CREATE TABLE users (\n"
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY,\n"
                + "nick_name VARCHAR(255),\n"
                + "old BIGINT(3),\n"
                + "email VARCHAR(255) NOT NULL\n"
                + ")";
        String actual = new CreateQueryBuilder<>(
                Person.class
        ).build();
        assertThat(actual).isEqualTo(expected);
    }
}
