package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class H2SelectQueryBuilderTest {

    @Test
    void SELECT_쿼리_조회() {
        H2SelectQueryBuilder h2SelectQueryBuilder = new H2SelectQueryBuilder(Person.class);
        assertThat(h2SelectQueryBuilder.findAllQuery()).isEqualTo("SELECT nick_name, old, email FROM users");
    }

    @Test
    void 매개변수_NULL로_예외_발생() {
        assertThatThrownBy(() -> new H2SelectQueryBuilder(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Class가 존재하지 않습니다.");
    }

}
