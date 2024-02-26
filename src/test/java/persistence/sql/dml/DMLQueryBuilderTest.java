package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.model.Table;
import persistence.study.sql.ddl.Person1;
import persistence.study.sql.ddl.Person2;
import persistence.study.sql.ddl.Person3;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DMLQueryBuilderTest {

    @DisplayName("Person을 이용하여 insert 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void buildInsertQuery(Table table, Object person, String insertQuery) {
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        String result = dmlQueryBuilder.buildInsertQuery(person);

        assertThat(result).isEqualTo(insertQuery);
    }

    private static Stream<Arguments> buildInsertQuery() {
        return Stream.of(
                Arguments.arguments(new Table(Person1.class), new Person1("qwer", 1), "INSERT INTO person1 (id,name,age) values (null,'qwer',1);"),
                Arguments.arguments(new Table(Person2.class), new Person2("qwert", 2, "email@email.com"), "INSERT INTO person2 (id,nick_name,old,email) values (null,'qwert',2,'email@email.com');"),
                Arguments.arguments(new Table(Person3.class), new Person3("qwerty", 3, "email2@email.com"), "INSERT INTO users (id,nick_name,old,email) values (null,'qwerty',3,'email2@email.com');")
        );
    }

    @DisplayName("Person을 이용하여 findAll 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void buildFindAllQuery(Table table, String findAllQuery) {
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        String result = dmlQueryBuilder.buildFindAllQuery();

        assertThat(result).isEqualTo(findAllQuery);
    }

    private static Stream<Arguments> buildFindAllQuery() {
        return Stream.of(
                Arguments.arguments(new Table(Person1.class), "SELECT id,name,age FROM person1;"),
                Arguments.arguments(new Table(Person2.class), "SELECT id,nick_name,old,email FROM person2;"),
                Arguments.arguments(new Table(Person3.class), "SELECT id,nick_name,old,email FROM users;")
        );
    }

    @DisplayName("Person을 이용하여 findById 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void buildFindByIdQuery(Table table, Object person, String findByIdQuery) {
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        String result = dmlQueryBuilder.buildFindByIdQuery(person);

        assertThat(result).isEqualTo(findByIdQuery);
    }

    private static Stream<Arguments> buildFindByIdQuery() {
        return Stream.of(
                Arguments.arguments(new Table(Person1.class), new Person1(1L, "qwer", 1), "SELECT id,name,age FROM person1 WHERE id=1;"),
                Arguments.arguments(new Table(Person2.class), new Person2(2L, "qwert", 2, "email@email.com"), "SELECT id,nick_name,old,email FROM person2 WHERE id=2;"),
                Arguments.arguments(new Table(Person3.class), new Person3(3L, "qwerty", 3, "email2@email.com"), "SELECT id,nick_name,old,email FROM users WHERE id=3;")
        );
    }
}
