package orm.dsl.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class DQLQueryBuilderSelectTest {

    @Test
    @DisplayName("SELECT 절 생성 테스트")
    void DQL_SELECT_문_테스트() {
        // given
        DQLQueryBuilder dqlQueryBuilder = new DQLQueryBuilder();

        // when
        String query = dqlQueryBuilder.selectFrom(Person.class)
                .build();

        // then
        assertThat(query).isEqualTo("SELECT (id,name,age) FROM person");
    }
}
