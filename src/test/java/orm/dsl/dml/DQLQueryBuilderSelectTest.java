package orm.dsl.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;


import static org.assertj.core.api.Assertions.assertThat;
import static orm.util.ConditionUtils.eq;

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
        assertThat(query).isEqualTo("SELECT id,name,age FROM person");
    }

    @Test
    @DisplayName("SELECT 절 조건 포함 실행 테스트")
    void DQL_SELECT_문_WHERE_포함_테스트() {

        // given
        DQLQueryBuilder dqlQueryBuilder = new DQLQueryBuilder();

        // when
        String query = dqlQueryBuilder.selectFrom(Person.class)
                .where(eq("id", 1L))
                .build();

        // then
        assertThat(query).isEqualTo("SELECT id,name,age FROM person WHERE id = 1");
    }

    @Test
    @DisplayName("SELECT 절 다중 조건 포함 실행 테스트 - 가변인자는 AND 고정이다.")
    void DQL_SELECT_문_WHERE_다중_포함_테스트_가변인자() {

        // given
        DQLQueryBuilder dqlQueryBuilder = new DQLQueryBuilder();

        // when
        String query = dqlQueryBuilder.selectFrom(Person.class)
                .where(
                    eq("id", 1L),
                    eq("name", "설동민")
                )
                .build();

        // then
        assertThat(query).isEqualTo("SELECT id,name,age FROM person WHERE id = 1 AND name = 설동민");
    }

    @Test
    @DisplayName("SELECT 절 다중 조건 포함 실행 테스트 - 체이닝된 조건절을 만들어보자")
    void DQL_SELECT_문_WHERE_다중_포함_테스트_AND() {

        // given
        DQLQueryBuilder dqlQueryBuilder = new DQLQueryBuilder();

        // when
        String query = dqlQueryBuilder.selectFrom(Person.class)
                .where(
                        eq("id", 1L)
                        .and(eq("name", "Sight"))
                        .or(eq("age", 30))
                )
                .build();

        // then
        assertThat(query).isEqualTo("SELECT id,name,age FROM person WHERE id = 1 AND name = Sight OR age = 30");
    }
}
