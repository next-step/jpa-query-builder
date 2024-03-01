package persistence.sql.dml.clause;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WhereClauseTest {

    @Test
    @DisplayName("필드 값이 존재할 경우 where 절을 조회한다")
    void getWhereClause_1() {
        // given
        Person person = new Person("name", 26, null, 1);

        // when
        String result = WhereClause.getWhereClause(person);

        // then
        assertThat(result).isEqualTo("nick_name = 'name' AND old = 26");
    }
}
