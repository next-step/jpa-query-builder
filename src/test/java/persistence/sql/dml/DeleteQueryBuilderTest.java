package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredObjectException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeleteQueryBuilderTest {

    @Test
    void DELETE_쿼리_생성() {
        Person person = new Person(1L, "name", 1, "email@email.com");
        DeleteQuery deleteQueryBuilder = new DeleteQuery(person);
        assertThat(deleteQueryBuilder.makeQuery()).isEqualTo("DELETE FROM users WHERE id=1");
    }

    @Test
    void 객체_생성시_Null_일_경우() {
        assertThatThrownBy(() -> new DeleteQuery(null))
                .isInstanceOf(RequiredObjectException.class)
                .hasMessage(ExceptionMessage.REQUIRED_OBJECT.getMessage());
    }

}
