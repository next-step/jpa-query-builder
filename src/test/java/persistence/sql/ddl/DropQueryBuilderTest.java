package persistence.sql.ddl;

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

public class DropQueryBuilderTest {

    @DisplayName("person을 이용하여 drop 쿼리 생성하기")
    @ParameterizedTest
    @MethodSource
    void build(Table table, String dropQuery) {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(table);

        String result = dropQueryBuilder.build();

        assertThat(result).isEqualTo(dropQuery);
    }

    private static Stream<Arguments> build() {
        return Stream.of(
                Arguments.arguments(new Table(Person1.class), "DROP TABLE person1;"),
                Arguments.arguments(new Table(Person2.class), "DROP TABLE person2;"),
                Arguments.arguments(new Table(Person3.class), "DROP TABLE users;")
        );
    }
}
