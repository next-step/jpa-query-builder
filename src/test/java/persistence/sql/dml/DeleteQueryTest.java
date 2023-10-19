package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.person.SelectPerson;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryTest {
    @Test
    @DisplayName("@Column 없는 @Id 값 조건으로 delete문 생성")
    void deleteQuery() {
        //given
        final Long id = 3L;
        final String expectedQuery = String.format("DELETE FROM users WHERE id = %s", id);

        Class<Person> aClass = Person.class;

        //when
        String query = DeleteQuery.create(aClass, 3L);

        //then
        assertThat(query).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@Column 있는 @Id 값 조건으로 delete문 생성")
    void deleteQueryByColumn() {
        //given
        final Long id = 3L;
        final String expectedQuery = String.format("DELETE FROM selectPerson WHERE select_person_id = %s", id);

        Class<SelectPerson> aClass = SelectPerson.class;

        //when
        String query = DeleteQuery.create(aClass, 3L);

        //then
        assertThat(query).isEqualTo(expectedQuery);
    }
}