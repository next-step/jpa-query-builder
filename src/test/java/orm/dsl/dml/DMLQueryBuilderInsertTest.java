package orm.dsl.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import test_entity.PersonWithAI;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DMLQueryBuilderInsertTest {

    @Test
    @DisplayName("INSERT 절 생성 테스트")
    void DML_INSERT_문_테스트() {
        // given
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder();
        Person person = new Person(1L, 30, "설동민");

        // when
        String query = dmlQueryBuilder.insertInto(Person.class)
                .values(person)
                .build();

        // then
        assertThat(query).isEqualTo("INSERT INTO person (id,name,age) VALUES (1,'설동민',30)");
    }

    @Test
    @DisplayName("INSERT 절 bulkInsert 테스트")
    void DML_INSERT_문_벌크_테스트() {
        // given
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder();
        final var people = List.of(
                new Person(1L, 30, "설동민"),
                new Person(2L, 30, "설동민2"),
                new Person(2L, 30, "설동민3")
        ) ;

        // when
        String query = dmlQueryBuilder.insertInto(Person.class)
                .values(people)
                .build();

        // then
        assertThat(query).isEqualTo("INSERT INTO person (id,name,age) VALUES (1,'설동민',30), (2,'설동민2',30), (2,'설동민3',30)");
    }

    @Test
    @DisplayName("테이블 pk가 AutoIncrement 인 경우, INSERT 절에 PK를 제외하고 쿼리를 만든다.")
    void DML_INSERT_문_AI_테스트() {
        // given
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder();
        final var people = List.of(
                new PersonWithAI(1L, 30, "설동민"),
                new PersonWithAI(2L, 30, "설동민2"),
                new PersonWithAI(2L, 30, "설동민3")
        ) ;

        // when
        String query = dmlQueryBuilder.insertInto(PersonWithAI.class)
                .values(people)
                .build();

        // then
        assertThat(query).isEqualTo("INSERT INTO person_ai (name,age) VALUES ('설동민',30), ('설동민2',30), ('설동민3',30)");
    }
}

