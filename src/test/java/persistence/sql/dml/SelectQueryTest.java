package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectQueryTest {

    @Test
    void SELECT_쿼리_조회() {
        SelectQuery h2SelectQueryBuilder = new SelectQuery(Person.class);
        assertThat(h2SelectQueryBuilder.findAll()).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @Test
    void 매개변수_NULL로_예외_발생() {
        assertThatThrownBy(() -> new SelectQuery(null))
                .isInstanceOf(RequiredClassException.class)
                .hasMessage(ExceptionMessage.REQUIRED_CLASS.getMessage());
    }

    @Test
    void 아이디로_조회_쿼리() {
        SelectQuery h2SelectQueryBuilder = new SelectQuery(Person.class);
        assertThat(h2SelectQueryBuilder.findById(1L)).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id=1");
    }

}
