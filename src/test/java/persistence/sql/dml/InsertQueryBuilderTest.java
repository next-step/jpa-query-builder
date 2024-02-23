package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.entity.NotEntityPerson;
import persistence.sql.dml.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InsertQueryBuilderTest {

    private static final InsertQueryBuilder INSERT_QUERY_BUILDER = new InsertQueryBuilder();

    @Test
    @DisplayName("Person 객체를 이용한 DML INSERT 생성 테스트")
    void DMLInsertTest() {
        // given
        String expectedQuery = "INSERT INTO USERS (NICK_NAME, OLD, EMAIL) VALUES ('Jamie', 34, 'jaesungahn91@gmail.com');";
        Person person = new Person("Jamie", 34, "jaesungahn91@gmail.com");

        // when
        String actualQuery = INSERT_QUERY_BUILDER.getInsertQueryString(person);

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@Entity 어노테이션이 없는 NotEntityPerson 객체를 이용한 DML INSERT 생성 실패 테스트")
    void DMLInsertFailTest() {
        // given
        NotEntityPerson notEntityPerson = new NotEntityPerson("Jamie", 34, "jaesungahn91@gmail.com");
        String message = "Does not have an @Entity annotation.";

        // when & then
        assertThatThrownBy(() -> INSERT_QUERY_BUILDER.getInsertQueryString(notEntityPerson))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

}
