package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.ExceptionUtil;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class H2DeleteQueryBuilderTest {

    @Test
    void DELETE_쿼리_생성() {
        DeleteQueryBuilder deleteQueryBuilder = new H2DeleteQueryBuilder(Person.class);
        String delete = deleteQueryBuilder.delete();
        assertThat(deleteQueryBuilder.delete()).isEqualTo("drop table users");
    }

    @Test
    void 객체_생성시_Null_일_경우() {
        assertThatThrownBy(() -> new H2DeleteQueryBuilder(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionUtil.CLASS_NULL_MESSAGE);
    }

}
