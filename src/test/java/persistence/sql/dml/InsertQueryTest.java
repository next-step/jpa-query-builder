package persistence.sql.dml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.InvalidEntityException;
import persistence.person.NonExistentEntityPerson;
import persistence.person.NonExistentTablePerson;

class InsertQueryTest {

    @Test
    @DisplayName("Person 객체를 읽어 @Transient 필드 제외하고 성공적으로 insert query 생성")
    void success() {
        //given
        final String expectedQuery = "INSERT INTO users (id, nick_name, old, email) VALUES(1, 'name', 3, 'zz@cc.com')";
        final Person person = new Person(1L, "name", 3, "zz@cc.com", 1);

        //when
        String query = InsertQuery.create(person);

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(query).isEqualTo(expectedQuery);
            softAssertions.assertThat(query).isNotEqualToIgnoringCase("index");
        });

    }

    @Test
    @DisplayName("@Entity가 없는 객체의 경우 insert query 생성하지 않고 exception 발생")
    void isNotEntity() {
        //given
        final NonExistentEntityPerson person = new NonExistentEntityPerson(1L, "name", 3);

        //when & then
        assertThrows(InvalidEntityException.class, () -> InsertQuery.create(person));
    }

    @Test
    @DisplayName("@Table이 없는 객체의 경우 클래스명을 기반으로 insert query 생성")
    void isNonTableName() {
        //given
        final String expectedQuery = "INSERT INTO NonExistentTablePerson (id, nick_name, old, email) VALUES(1, 'name', 3, 'zz@cc.com')";
        final NonExistentTablePerson person = new NonExistentTablePerson(1L, "name", 3, "zz@cc.com");

        //when
        String query = InsertQuery.create(person);

        //then
        assertThat(query).isEqualTo(expectedQuery);
    }
}
