package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.Person;
import persistence.sql.Dialect;
import persistence.sql.dialect.h2.H2Dialect;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FindAllQueryBuilderTest {

    @DisplayName("@Entity 애노테이션이 붙은 클래스만 쿼리를 생성할 수 있다.")
    @Test
    void shouldFailWhenEntityIsNotAnnotated() {
        assertThatThrownBy(() -> {
            new FindAllQueryBuilder(new H2Dialect(), FindAllQueryBuilderTest.TestWithNoEntityAnnotation.class);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static class TestWithNoEntityAnnotation {
    }

    @DisplayName("엔티티에 알맞는 findAll (select) 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("selectQueryTestParam")
    void insertQueryTest(Dialect dialect, Class<?> entityClass, String expectedQuery) {
        FindAllQueryBuilder findAllQueryBuilder = new FindAllQueryBuilder(dialect, entityClass);
        String actualQuery = findAllQueryBuilder.getQuery();
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> selectQueryTestParam() {
        return Stream.of(
                Arguments.of(
                        new H2Dialect(),
                        Person.class,
                        "select id, nick_name, old, email from users"
                )
        );
    }

}
