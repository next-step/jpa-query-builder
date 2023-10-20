package persistence.sql.dml;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryGenerator;
import persistence.testFixtures.Person;

class SelectQueryBuilderTest {

    @Test
    @DisplayName("전체를 조회하는 구문을 생성한다.")
    void selectAll() {
        //given
        SelectQueryBuilder<Person> select = QueryGenerator.from(Person.class).select();

        //when
        String sql = select.findAll();

        //then
        assertThat(sql).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

}
