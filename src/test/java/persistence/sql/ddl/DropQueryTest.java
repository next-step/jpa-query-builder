package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;

class DropQueryTest {

    @Test
    @DisplayName("Person Entity를 이용한 DROP QUERY가 정확히 생성 되었는지 검증")
    void drop() {
        //given
        final String expectedSql = "DROP TABLE users";

        //when
        final String result = DropQuery.drop(Person.class);

        //then
        assertThat(result).isEqualTo(expectedSql);
    }
}