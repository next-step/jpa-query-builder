package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.example.Person;

import static org.assertj.core.api.Assertions.*;

class DropQueryBuilderTest {
    @Test
    @DisplayName("drop 쿼리를 생성한다.")
    void drop() {
        // given
        final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);

        // when
        final String query = dropQueryBuilder.drop();

        // then
        assertThat(query).isEqualTo("DROP TABLE IF EXISTS users");
    }
}
