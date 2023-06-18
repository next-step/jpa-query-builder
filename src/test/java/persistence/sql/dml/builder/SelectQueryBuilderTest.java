package persistence.sql.dml.builder;

import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {
    private final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(PersonV3.class);

    @Test
    @DisplayName("findAll 쿼리를 반환한다")
    public void findAll() {
        String query = selectQueryBuilder.findAll();

        assertThat(query).isEqualTo("select id, nick_name, old, email from users");
    }

    @Test
    @DisplayName("findById 쿼리를 반환한다")
    void findByIdSql() {
        String query = selectQueryBuilder.findById(1L);

        assertThat(query).isEqualTo("select id, nick_name, old, email from users where id=1");
    }

}