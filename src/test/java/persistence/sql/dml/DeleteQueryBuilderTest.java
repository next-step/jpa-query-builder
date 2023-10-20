package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.Person;
import persistence.sql.Query;
import persistence.sql.dialect.h2.H2Query;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeleteQueryBuilderTest {

    @DisplayName("@Entity 애노테이션이 붙은 클래스만 쿼리를 생성할 수 있다.")
    @Test
    void shouldFailWhenEntityIsNotAnnotated() {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(new H2Query());
        assertThatThrownBy(() -> deleteQueryBuilder.getQuery(new TestWithNoEntityAnnotation()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static class TestWithNoEntityAnnotation {
    }

    @DisplayName("엔티티에 알맞는 delete 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("deleteQueryTestParam")
    void deleteQueryTest(Query query, Object entity, String expectedQuery) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(query);
        String actualQuery = deleteQueryBuilder.getQuery(entity);
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> deleteQueryTestParam() {
        return Stream.of(
                Arguments.of(
                        new H2Query(),
                        new Person(1L, "test1", 10, "test1@gmail.com", 0),
                        "delete from users where id = 1"
                )
        );
    }

}
