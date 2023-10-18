package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.InvalidEntityException;
import persistence.person.NonExistentTablePerson;
import persistence.person.NotEntityPerson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SelectQueryTest {

    @Test
    @DisplayName("@Entity 없는 클래스 select quert 생성시 오류")
    void notEntity() {
        //given
        final Class<NotEntityPerson> aClass = NotEntityPerson.class;
        final String methodName = "findAll";

        //when & then
        assertThrows(InvalidEntityException.class, () -> SelectQuery.create(aClass, methodName));
    }

    @Test
    @DisplayName("@Table name이 없을 경우 클래스 이름으로 select query 생성")
    void nonTableName() {
        //given
        final String expectedQuery = "SELECT id, nick_name, old, email FROM NonExistentTablePerson";

        final Class<NonExistentTablePerson> aClass = NonExistentTablePerson.class;
        final String methodName = "findAll";

        //when
        String query = SelectQuery.create(aClass, methodName);

        //then
        assertThat(query).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("전체 데이터 조회하는 select문 생성")
    void findAll() {
        //given
        final String expectedQuery = "SELECT id, nick_name, old, email FROM users";

        final Class<Person> aClass = Person.class;

        //when
        String query = SelectQuery.create(aClass, new Object() {
        }.getClass().getEnclosingMethod().getName());

        //then
        assertThat(query).isEqualTo(expectedQuery);
    }
}