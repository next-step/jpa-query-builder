package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

class SelectAllQueryBuilderTest {

    @Test
    @DisplayName("findAll 쿼리를 만들 수 있다.")
    void buildFindAllQuery() {
        //given
        SelectAllQueryBuilder selectAllQueryBuilder = new SelectAllQueryBuilder();

        //when
        String query = selectAllQueryBuilder.build(Person.class);

        //then
        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }
}
