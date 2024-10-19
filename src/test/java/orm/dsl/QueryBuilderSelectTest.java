package orm.dsl;

import config.PluggableH2test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.mapper.PersonRowMapper;
import persistence.sql.ddl.Person;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static orm.dsl.DSL.eq;
import static steps.Steps.Person_엔티티_생성;
import static steps.Steps.테이블_생성;

public class QueryBuilderSelectTest extends PluggableH2test {

    QueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        queryBuilder = new QueryBuilder();
    }

    @Test
    @DisplayName("SELECT 절 생성 테스트")
    void DQL_SELECT_문_테스트() {
        // when
        String query = queryBuilder.selectFrom(Person.class)
                .extractSql();

        // then
        assertThat(query).isEqualTo("SELECT id,name,age FROM person");
    }

    @Test
    @DisplayName("SELECT 절 조건 포함 실행 테스트")
    void DQL_SELECT_문_WHERE_포함_테스트() {
        // when
        String query = queryBuilder.selectFrom(Person.class)
                .where(eq("id", 1L))
                .extractSql();

        // then
        assertThat(query).isEqualTo("SELECT id,name,age FROM person WHERE id = 1");
    }

    @Test
    @DisplayName("SELECT 절 다중 조건 포함 실행 테스트 - 가변인자는 AND 고정이다.")
    void DQL_SELECT_문_WHERE_다중_포함_테스트_가변인자() {

        // when
        String query = queryBuilder.selectFrom(Person.class)
                .where(
                    eq("id", 1L),
                    eq("name", "설동민")
                )
                .extractSql();

        // then
        assertThat(query).isEqualTo("SELECT id,name,age FROM person WHERE id = 1 AND name = '설동민'");
    }

    @Test
    @DisplayName("SELECT 절 다중 조건 포함 실행 테스트 - 체이닝된 조건절을 만들어보자")
    void DQL_SELECT_문_WHERE_다중_포함_테스트_AND() {
        // when
        String query = queryBuilder.selectFrom(Person.class)
                .where(
                        eq("id", 1L)
                        .and(eq("name", "설동민"))
                        .or(eq("age", 30))
                )
                .extractSql();

        // then
        assertThat(query).isEqualTo("SELECT id,name,age FROM person WHERE id = 1 AND name = '설동민' OR age = 30");
    }

    @Test
    @DisplayName("SELECT 절 실제 쿼리 실행 테스트")
    void DQL_SELECT_실제_쿼리_실행() {
        runInH2Db((jdbcTemplate) -> {
            // given
            QueryBuilder queryBuilder = new QueryBuilder(jdbcTemplate);
            Person newPerson = new Person(1L, 30, "설동민");
            테이블_생성(jdbcTemplate, Person.class);
            Person_엔티티_생성(jdbcTemplate, newPerson);

            // when
            Person person = queryBuilder.selectFrom(Person.class)
                    .where(eq("id", 1L))
                    .fetchOne(new PersonRowMapper());

            // then
            assertThat(person).hasNoNullFieldsOrPropertiesExcept("id", "name", "age");
        });
    }

    @Test
    @DisplayName("SELECT 절 실제 쿼리 실행 테스트 - findById()")
    void findById_실헹() {
        runInH2Db((jdbcTemplate) -> {
            // given
            QueryBuilder queryBuilder = new QueryBuilder(jdbcTemplate);
            Person newPerson = new Person(1L, 30, "설동민");
            테이블_생성(jdbcTemplate, Person.class);
            Person_엔티티_생성(jdbcTemplate, newPerson);
            // when
            Person person = queryBuilder.selectFrom(Person.class).findById(1L)
                    .fetchOne(new PersonRowMapper());

            // then
            assertThat(person).hasNoNullFieldsOrPropertiesExcept("id", "name", "age")
                    .extracting("id").isEqualTo(1L);
        });
    }

    @Test
    @DisplayName("SELECT 절 실제 쿼리 실행 테스트 - findByAll()")
    void findAll_실행() {
        runInH2Db((jdbcTemplate) -> {
            // given
            QueryBuilder queryBuilder = new QueryBuilder(jdbcTemplate);
            테이블_생성(jdbcTemplate, Person.class);
            Person_엔티티_생성(jdbcTemplate, new Person(1L, 30, "설동민"));
            Person_엔티티_생성(jdbcTemplate, new Person(2L, 30, "설동민2"));

            // when
            List<Person> people = queryBuilder.selectFrom(Person.class).findAll()
                    .fetch(new PersonRowMapper());

            // then
            assertThat(people).asList()
                    .hasSize(2)
                    .allSatisfy(person -> assertThat(person).hasNoNullFieldsOrPropertiesExcept("id", "name", "age"));
        });
    }
}
