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

        Person person = new Person(3L, "zz", 30, "zz", 1);

        //when
        String query = DeleteQuery.create(person, 3L);

        //then
        assertThat(query).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@Column 있는 @Id 값 조건으로 delete문 생성")
    void deleteQueryByColumn() {
        //given
        final Long id = 3L;
        final String expectedQuery = String.format("DELETE FROM selectPerson WHERE select_person_id = %s", id);

        SelectPerson person = new SelectPerson(3L, "zz", 30, "zz", 1);

        //when
        String query = DeleteQuery.create(person, 3L);

        //then
        assertThat(query).isEqualTo(expectedQuery);
    }
}