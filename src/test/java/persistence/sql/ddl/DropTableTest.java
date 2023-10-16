package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.Person;
import persistence.exception.InvalidEntityException;
import persistence.person.ExistTablePerson;
import persistence.person.NonExistentEntityPerson;

class DropTableTest {

    @Test
    @DisplayName("Person Entity를 이용한 DROP QUERY가 정확히 생성 되었는지 검증")
    void drop() {
        //given
        final String expectedSql = "DROP TABLE users";

        //when
        final String result = DropTable.drop(Person.class);

        //then
        assertThat(result).isEqualTo(expectedSql);
    }

    @Test
    @DisplayName("@Entity가 없는 객체의 경우 InvalidEntityException 발생")
    void notExistentEntity() {
        //given
        Class<NonExistentEntityPerson> personClass = NonExistentEntityPerson.class;

        //when & then
        assertThrows(InvalidEntityException.class, () -> DropTable.drop(personClass));
    }

    @Test
    @DisplayName("@Table의 name이 존재하면 해당 값으로 테이블 삭제 쿼리 생성")
    void notExistentTable() {
        //given
        final Class<ExistTablePerson> personClass = ExistTablePerson.class;
        final String expectedSql = "DROP TABLE users";

        //when
        final String result = DropTable.drop(personClass);

        //then
        assertThat(result).isEqualTo(expectedSql);
    }
}
