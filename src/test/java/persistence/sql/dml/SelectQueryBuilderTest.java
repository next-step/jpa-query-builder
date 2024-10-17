package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.ExceptionUtil;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectQueryBuilderTest {

    @Test
    void SELECT_쿼리_조회() {
        SelectQuery h2SelectQueryBuilder = new SelectQuery(Person.class);
        assertThat(h2SelectQueryBuilder.findAll()).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @Test
    void 매개변수_NULL로_예외_발생() {
        assertThatThrownBy(() -> new SelectQuery(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionUtil.CLASS_NULL_MESSAGE);
    }

    @Test
    void 아이디로_조회_쿼리() {
        SelectQuery h2SelectQueryBuilder = new SelectQuery(Person.class);
        assertThat(h2SelectQueryBuilder.findById(1L)).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id=1");
    }

}
