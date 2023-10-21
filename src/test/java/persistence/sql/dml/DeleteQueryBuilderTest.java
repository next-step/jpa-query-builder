package persistence.sql.dml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.FieldEmptyException;
import persistence.sql.QueryGenerator;
import persistence.testFixtures.Person;

class DeleteQueryBuilderTest {

    @Test
    @DisplayName("삭제하는 구문을 생성한다.")
    void delete() {
        //given
        QueryGenerator<Person> query = QueryGenerator.from(Person.class);

        //when
        final String sql = query.delete(1L);

        //then
        assertThat(sql).isEqualTo("DELETE FROM users WHERE id = 1");
    }

    @Test
    @DisplayName("id가 비어있으면 예외가 발생한다.")
    void deleteId() {
        //given
        QueryGenerator<Person> query = QueryGenerator.from(Person.class);

        //when & then
        assertThatExceptionOfType(FieldEmptyException.class)
                .isThrownBy(() -> query.delete(null));
    }

}
