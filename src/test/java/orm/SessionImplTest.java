package orm;

import config.PluggableH2test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.dsl.QueryBuilder;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static steps.Steps.테이블_생성;

public class SessionImplTest extends PluggableH2test {

    @Test
    @DisplayName("persist 메서드를 사용하면 엔티티 Object의 Equality와 Identity가 모두 유지된다.")
    void persistence_테스트_equality_identity() {
        runInH2Db(jdbcTemplate -> {

            // given
            테이블_생성(jdbcTemplate, Person.class);

            SessionImpl session = new SessionImpl(new QueryBuilder(jdbcTemplate));
            Person newPerson = new Person(1L, 30, "설동민");

            // when
            Person person = session.persist(newPerson);

            // then
            assertThat(person)
                    .isSameAs(newPerson) // Identity 검증
                    .satisfies(p -> { // Equality 검증
                        assertThat(p.getId()).isEqualTo(1L);
                        assertThat(p.getAge()).isEqualTo(30);
                        assertThat(p.getName()).isEqualTo("설동민");
                    });
        });
    }
}
