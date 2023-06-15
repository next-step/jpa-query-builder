package persistence.sql.common;

import domain.Person;
import domain.PersonFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnValuesTest {

    @Test
    @DisplayName("Person 객체들의 필드값을 ColumnValues 로 추출할 수 있다.")
    void filterTransient() {
        final Person person = PersonFixture.createPerson();
        final ColumnFields fields = ColumnFields.forInsert(Person.class);
        Assertions.assertThat(
                ColumnValues.of(person, fields)
                        .toString()
        ).isEqualTo(" VALUES (고정완, 30, ghojeong@email.com)");
    }
}
