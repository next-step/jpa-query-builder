package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.example.Person;

import static org.assertj.core.api.Assertions.*;

class CreateQueryBuilderTest {
    @Test
    @DisplayName("create 쿼리를 생성한다.")
    void create() {
        // given
        final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);

        // when
        final String query = createQueryBuilder.create();

        // then
        assertThat(query).isEqualTo(
                "CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL)");
    }
}
