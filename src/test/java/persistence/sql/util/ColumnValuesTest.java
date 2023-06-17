package persistence.sql.util;

import domain.Person;
import domain.PersonFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnValuesTest {

    @Test
    @DisplayName("Person 객체들의 필드값을 ColumnValues 로 추출할 수 있다.")
    void filterTransient() {
        Assertions.assertThat(ColumnValues.render(
                PersonFixture.createPerson(),
                ColumnFields.forInsert(Person.class)
        )).isEqualTo(" VALUES ('고정완', 30, 'ghojeong@email.com')");
    }
}
