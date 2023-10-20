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

class FindByIdQueryBuilderTest {

    @DisplayName("@Entity 애노테이션이 붙은 클래스만 쿼리를 생성할 수 있다.")
    @Test
    void shouldFailWhenEntityIsNotAnnotated() {
        FindByIdQueryBuilder findByIdQueryBuilder = new FindByIdQueryBuilder(new H2Query());
        assertThatThrownBy(() -> findByIdQueryBuilder.getQuery(TestWithNoEntityAnnotation.class, 0L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static class TestWithNoEntityAnnotation {
    }

    @DisplayName("엔티티에 알맞는 findAll (select) 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("selectQueryTestParam")
    void insertQueryTest(Query query, Class<?> entityClass, Object id, String expectedQuery) {
        FindByIdQueryBuilder findByIdQueryBuilder = new FindByIdQueryBuilder(query);
        String actualQuery = findByIdQueryBuilder.getQuery(entityClass, id);
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> selectQueryTestParam() {
        return Stream.of(
                Arguments.of(
                        new H2Query(),
                        Person.class,
                        "test1",
                        "select id, nick_name, old, email from users where id = 'test1'"),
                Arguments.of(
                        new H2Query(),
                        Person.class,
                        1,
                        "select id, nick_name, old, email from users where id = 1")
        );
    }

}
