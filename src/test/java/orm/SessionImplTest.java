package orm;

import config.PluggableH2test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.dsl.QueryBuilder;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static steps.Steps.테이블_생성;

public class SessionImplTest extends PluggableH2test {


    // TODO: 추후 Hibernate 1차 캐시를 구현하면 newPerson 객체와 person 객체의 equality뿐 아리나 identity 까지 같아야 한다. (아직은 다름)
    @Test
    @DisplayName("insert 후 find 메서드를 사용하면 엔티티 Object의 Equality만 유지된다.")
    void find_테스트() {
        runInH2Db(jdbcTemplate -> {

            // given
            테이블_생성(jdbcTemplate, Person.class);

            SessionImpl session = new SessionImpl(new QueryBuilder(jdbcTemplate));
            Person newPerson = new Person(1L, 30, "설동민");
            session.persist(newPerson);

            // when
            Person person = session.find(Person.class, 1L);

            // then
            assertThat(person)
                    .satisfies(p -> { // Equality 검증
                        assertThat(p.getId()).isEqualTo(1L);
                        assertThat(p.getAge()).isEqualTo(30);
                        assertThat(p.getName()).isEqualTo("설동민");
                    });
        });
    }

    @Test
    @DisplayName("persist 메서드를 사용하면 엔티티 Object의 Equality와 Identity가 모두 유지된다.")
    void persistence_테스트() {
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

    @Test
    @DisplayName("delete 후 find 메서드를 사용하면 엔티티 결과는 null이 리턴된다.")
    void delete_테스트() {
        runInH2Db(jdbcTemplate -> {

            // given
            테이블_생성(jdbcTemplate, Person.class);

            SessionImpl session = new SessionImpl(new QueryBuilder(jdbcTemplate));
            session.persist(new Person(1L, 30, "설동민"));
            Person person = session.find(Person.class, 1L);

            // when
            session.remove(person);
            Person result = session.find(Person.class, 1L);

            // then
            assertThat(result).isNull();
        });
    }

    // TODO: 추후 Hibernate 1차 캐시를 구현하면 persistence() 메서드 하나로 insert와 update를 구분 할 수 있어야한다.
    @Test
    @DisplayName("update 수정된 엔티티를 조회한다.")
    void update_테스트() {
        runInH2Db(jdbcTemplate -> {

            // given
            테이블_생성(jdbcTemplate, Person.class);
            SessionImpl session = new SessionImpl(new QueryBuilder(jdbcTemplate));
            session.persist(new Person(1L, 30, "설동민"));
            Person person = session.find(Person.class, 1L);

            // when
            person.setName("설동민 - 수정함");
            session.update(person);
            Person result = session.find(Person.class, 1L);

            // then
            assertThat(result).satisfies(p -> {
                assertThat(p.getId()).isEqualTo(1L);
                assertThat(p.getAge()).isEqualTo(30);
                assertThat(p.getName()).isEqualTo("설동민 - 수정함");
            });
        });
    }
}
