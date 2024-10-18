package orm.dsl.dml;

import config.PluggableH2test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.mapper.PersonRowMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static orm.util.ConditionUtils.eq;
import static steps.Steps.Person_엔티티_생성;
import static steps.Steps.테이블_생성;

public class DMLQueryBuilderDeleteTest extends PluggableH2test {

    @Test
    @DisplayName("DELETE 절 생성 테스트")
    void DML_DELETE_문_테스트() {
        // given
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder();

        // when
        String query = dmlQueryBuilder.deleteFrom(Person.class)
                .build();

        // then
        assertThat(query).isEqualTo("DELETE FROM person");
    }

    @Test
    @DisplayName("DELETE 절 조건 포함 실행 테스트")
    void DML_DELETE_문_조건절_테스트() {
        // given
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder();

        // when
        String query = dmlQueryBuilder.deleteFrom(Person.class)
                .where(
                    eq("id", 1L)
                        .and(eq("name", "설동민"))
                        .or(eq("age", 30))
                )
                .build();

        // then
        assertThat(query).isEqualTo("DELETE FROM person WHERE id = 1 AND name = '설동민' OR age = 30");
    }

    @Test
    @DisplayName("DELETE 절 조건 실제 실행 테스트")
    void DML_DELETE_문_실행_테스트() {
        runInH2Db((jdbcTemplate) -> {
            // given
            테이블_생성(jdbcTemplate, Person.class);
            Person_엔티티_생성(jdbcTemplate, new Person(1L, 30, "설동민"));
            Person_엔티티_생성(jdbcTemplate, new Person(2L, 30, "설동민2"));

            DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(jdbcTemplate);
            DQLQueryBuilder dqlQueryBuilder = new DQLQueryBuilder(jdbcTemplate);

            // when
            dmlQueryBuilder.deleteFrom(Person.class)
                    .where(eq("id", 1).or(eq("id", 2L)))
                    .execute();

            List<Person> people = dqlQueryBuilder.selectFrom(Person.class)
                    .findAll()
                    .fetch(new PersonRowMapper());

            // then
            assertThat(people).asList().hasSize(0);
        });
    }
}

