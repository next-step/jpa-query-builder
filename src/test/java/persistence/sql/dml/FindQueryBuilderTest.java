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

class FindQueryBuilderTest {

    @DisplayName("Person을 이용하여 findAll 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void build(Table table, String findAllQuery) {
        FindQueryBuilder findQueryBuilder = new FindQueryBuilder(table);

        String result = findQueryBuilder.build();

        assertThat(result).isEqualTo(findAllQuery);
    }

    private static Stream<Arguments> build() {
        return Stream.of(
                Arguments.arguments(new Table(Person1.class), "SELECT id,name,age FROM person1;"),
                Arguments.arguments(new Table(Person2.class), "SELECT id,nick_name,old,email FROM person2;"),
                Arguments.arguments(new Table(Person3.class), "SELECT id,nick_name,old,email FROM users;")
        );
    }
}
