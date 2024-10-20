package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectQueryBuilderTest {

    @Test
    void SELECT_쿼리_조회() {
        SelectQueryBuilder h2SelectQueryBuilder = new SelectQueryBuilder(Person.class);
        assertThat(h2SelectQueryBuilder.findAll()).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @Test
    void 매개변수_NULL로_예외_발생() {
        assertThatThrownBy(() -> new SelectQueryBuilder(null))
                .isInstanceOf(RequiredClassException.class)
                .hasMessage(ExceptionMessage.REQUIRED_CLASS.getMessage());
    }

    @Test
    void 아이디로_조회_쿼리() {
        SelectQueryBuilder h2SelectQueryBuilder = new SelectQueryBuilder(Person.class);
        assertThat(h2SelectQueryBuilder.findById(1L)).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id=1");
    }

}
