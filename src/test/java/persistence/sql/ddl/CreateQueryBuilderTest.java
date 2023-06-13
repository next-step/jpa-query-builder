package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class CreateQueryBuilderTest {

    @Test
    @DisplayName("Person Entity 를 위한 create 쿼리를 생성한다.")
    void build() {
        String expected = "create table PERSON (\n"
                + "id BIGINT PRIMARY KEY,\n"
                + "name TEXT,\n"
                + "age BIGINT\n"
                + ")";
        String actual = new CreateQueryBuilder<>(
                Person.class
        ).build();
        assertThat(actual).isEqualTo(expected);
    }
}
