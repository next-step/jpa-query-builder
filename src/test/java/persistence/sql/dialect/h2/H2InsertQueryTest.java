package persistence.sql.dialect.h2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.Person;
import persistence.sql.entity.EntityData;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class H2InsertQueryTest {

    @DisplayName("엔티티에 알맞는 insert 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("h2InsertQueryTestParams")
    void h2InsertQueryTest(Object entity, String expectedQuery) {
        H2InsertQuery insertQuery = new H2InsertQuery();
        String actualQuery = insertQuery.generateQuery(new EntityData(entity.getClass()), entity);
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> h2InsertQueryTestParams() {
        return Stream.of(
                Arguments.of(new Person("test1", 10, "test1@gmail.com", 0),
                        "insert into users (id, nick_name, old, email) values (default, 'test1', 10, 'test1@gmail.com')")
        );
    }

}
