package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.ExceptionUtil;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class H2SelectQueryBuilderTest {

    @Test
    void SELECT_쿼리_조회() {
        H2SelectQueryBuilder h2SelectQueryBuilder = new H2SelectQueryBuilder(Person.class);
        assertThat(h2SelectQueryBuilder.findAll()).isEqualTo("SELECT id, nick_name, old, email FROM users");
    }

    @Test
    void 매개변수_NULL로_예외_발생() {
        assertThatThrownBy(() -> new H2SelectQueryBuilder(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionUtil.CLASS_NULL_MESSAGE);
    }

    @Test
    void 아이디로_조회_쿼리() {
        H2SelectQueryBuilder h2SelectQueryBuilder = new H2SelectQueryBuilder(Person.class);
        assertThat(h2SelectQueryBuilder.findById(1L)).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id=1");
    }

}
