package orm.dsl.dml;

import config.PluggableH2test;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mapper.PersonRowMapper;
import persistence.sql.ddl.Person;


import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static orm.util.ConditionUtils.eq;
import static steps.Steps.Person_엔티티_생성;
import static steps.Steps.테이블_생성;

public class DQLQueryBuilderSelectTest extends PluggableH2test {

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
        assertThat(query).isEqualTo("SELECT id,name,age FROM person WHERE id = 1 AND name = '설동민'");
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
                        .and(eq("name", "설동민"))
                        .or(eq("age", 30))
                )
                .build();

        // then
        assertThat(query).isEqualTo("SELECT id,name,age FROM person WHERE id = 1 AND name = '설동민' OR age = 30");
    }

    @Test
    @DisplayName("SELECT 절 실제 쿼리 실행 테스트")
    void DQL_SELECT_실제_쿼리_실행() {
        runInH2Db((jdbcTemplate) -> {
            // given
            Person newPerson = new Person(1L, 30, "설동민");
            테이블_생성(jdbcTemplate, Person.class);
            Person_엔티티_생성(jdbcTemplate, newPerson);
            DQLQueryBuilder dqlQueryBuilder = new DQLQueryBuilder(jdbcTemplate);

            // when
            Person person = dqlQueryBuilder.selectFrom(Person.class)
                    .where(eq("id", 1L))
                    .fetchOne(new PersonRowMapper());

            // then
            assertThat(person).hasNoNullFieldsOrPropertiesExcept("id", "name", "age");
        });
    }
}
