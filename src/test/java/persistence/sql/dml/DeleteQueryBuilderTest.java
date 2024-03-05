package persistence.sql.dml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.notcolumn.Person;

class DeleteQueryBuilderTest {

    @DisplayName("[요구사항4.1] Delete All 쿼리를 반환한다.")
    @Test
    void 요구사항2_test() {
        // given
        String expected = "DELETE FROM users";

        // when
        String actual = new DeleteQueryBuilder(Person.class).deleteAll();

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("[요구사항4.2] Delete by Id 쿼리를 반환한다.")
    @Test
    void 요구사항3_test() {
        // given
        Long id = 1L;

        // when
        String query = new DeleteQueryBuilder(Person.class).deleteById(id);

        // then
        Assertions.assertThat(query).isEqualTo(String.format("DELETE FROM users where id = %d", id));
    }
}