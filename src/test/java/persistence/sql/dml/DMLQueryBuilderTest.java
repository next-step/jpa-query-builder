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
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table, person);

        String result = dmlQueryBuilder.buildInsertQuery();

        assertThat(result).isEqualTo(insertQuery);
    }

    private static Stream<Arguments> buildInsertQuery() {
        return Stream.of(
                Arguments.arguments(new Table(Person1.class), new Person1("qwer", 1), "INSERT INTO person1 (id,name,age) values (null,qwer,1);"),
                Arguments.arguments(new Table(Person2.class), new Person2("qwert", 2, "email@email.com"), "INSERT INTO person2 (id,nick_name,old,email) values (null,qwert,2,email@email.com);"),
                Arguments.arguments(new Table(Person3.class), new Person3("qwerty", 3, "email2@email.com"), "INSERT INTO users (id,nick_name,old,email) values (null,qwerty,3,email2@email.com);")
        );
    }
}
