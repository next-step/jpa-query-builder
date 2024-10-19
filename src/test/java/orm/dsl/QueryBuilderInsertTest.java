package orm.dsl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.exception.OrmPersistenceException;
import persistence.sql.ddl.Person;
import test_entity.PersonWithAI;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class QueryBuilderInsertTest {

    QueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        queryBuilder = new QueryBuilder();
    }

    @Test
    @DisplayName("INSERT 절 생성 테스트")
    void DML_INSERT_문_테스트() {
        // given
        Person person = new Person(1L, 30, "설동민");

        // when
        String query = queryBuilder.insertInto(Person.class)
                .value(person)
                .extractSql();

        // then
        assertThat(query).isEqualTo("INSERT INTO person (id,name,age) VALUES (1,'설동민',30)");
    }

    @Test
    @DisplayName("INSERT 절 bulkInsert 테스트")
    void DML_INSERT_문_벌크_테스트() {
        // given
        final var people = List.of(
                new Person(1L, 30, "설동민"),
                new Person(2L, 30, "설동민2"),
                new Person(2L, 30, "설동민3")
        ) ;

        // when
        String query = queryBuilder.insertInto(Person.class)
                .values(people)
                .extractSql();

        // then
        assertThat(query).isEqualTo("INSERT INTO person (id,name,age) VALUES (1,'설동민',30), (2,'설동민2',30), (2,'설동민3',30)");
    }

    @Test
    @DisplayName("INSERT 절 bulkInsert 중복실행 테스트")
    void DML_INSERT_문_벌크_중복실행_테스트() {
        // when
        String query = queryBuilder.insertInto(Person.class)
                .values(List.of(new Person(1L, 30, "설동민")))
                .values(List.of(new Person(2L, 30, "설동민2")))
                .extractSql();

        // then
        assertThat(query).isEqualTo("INSERT INTO person (id,name,age) VALUES (1,'설동민',30), (2,'설동민2',30)");
    }

    @Test
    @DisplayName("테이블 pk가 AutoIncrement 인 경우, INSERT 절에 PK를 제외하고 쿼리를 만든다.")
    void DML_INSERT_문_AI_테스트() {
        // given
        final var people = List.of(
                new PersonWithAI(1L, 30, "설동민"),
                new PersonWithAI(2L, 30, "설동민2"),
                new PersonWithAI(2L, 30, "설동민3")
        ) ;

        // when
        String query = queryBuilder.insertInto(PersonWithAI.class)
                .values(people)
                .extractSql();

        // then
        assertThat(query).isEqualTo("INSERT INTO person_ai (name,age) VALUES ('설동민',30), ('설동민2',30), ('설동민3',30)");
    }

    @Test
    @DisplayName("INSERT 절에 emptyList가 들어갈 경우 에러가 발생한다.")
    void DML_INSERT_문_insert할_값이_없는_경우() {
        // given
        final List<PersonWithAI> people = List.of();

        // when & then
        assertThatThrownBy(
                () -> queryBuilder.insertInto(PersonWithAI.class)
                .values(people)
                .extractSql()
        ).isInstanceOf(OrmPersistenceException.class)
        .hasMessage("insert 할 값이 없습니다.");
    }
}

