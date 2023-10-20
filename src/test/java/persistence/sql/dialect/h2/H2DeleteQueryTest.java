package persistence.sql.dialect.h2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.Person;
import persistence.sql.entity.EntityData;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class H2DeleteQueryTest {

    @DisplayName("엔티티에 알맞는 delete 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("h2DeleteQueryTestParams")
    void h2DeleteQueryTest(Object entity, String expectedQuery) {
        H2DeleteQuery deleteQuery = new H2DeleteQuery();
        String actualQuery = deleteQuery.generateQuery(new EntityData(entity.getClass()), entity);
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> h2DeleteQueryTestParams() {
        return Stream.of(
                Arguments.of(new Person(1L, "test1", 10, "test1@gmail.com", 0),
                        "delete from users where id = 1")
        );
    }

}
