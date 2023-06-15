package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteByIdQueryBuilderTest {

    @Test
    @DisplayName("Person Entity 를 위한 findById 쿼리를 생성한다.")
    void build() {
        String expected = "DELETE FROM users"
                + " WHERE id = 1";
        String actual = new DeleteByIdQueryBuilder<>(
                Person.class
        ).build(1);
        assertThat(actual).isEqualTo(expected);
    }
}
