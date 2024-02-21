package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    @Test
    @DisplayName("select 쿼리를 만들 수 있다.")
    void buildFindQuery() {
        //given
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);

        //when
        String query = selectQueryBuilder.build(1L);

        //then
        assertThat(query).isEqualTo("SELECT * FROM users WHERE id = 1");
    }
}
