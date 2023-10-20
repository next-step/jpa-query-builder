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

class InsertQueryBuilderTest {

    @DisplayName("@Entity 애노테이션이 붙은 클래스만 쿼리를 생성할 수 있다.")
    @Test
    void shouldFailWhenEntityIsNotAnnotated() {
        assertThatThrownBy(() -> {
            new InsertQueryBuilder(new H2Query(), InsertQueryBuilderTest.TestWithNoEntityAnnotation.class);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static class TestWithNoEntityAnnotation {
    }

    @DisplayName("엔티티에 알맞는 Insert 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("insertQueryTestParam")
    void insertQueryTest(Query query, Object entity, String expectedQuery) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(query, entity);
        String actualQuery = insertQueryBuilder.getQuery();
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> insertQueryTestParam() {
        return Stream.of(
                Arguments.of(
                        new H2Query(),
                        new Person("test1", 10, "test1@gmail.com", 0),
                        "insert into users (id, nick_name, old, email) values (default, 'test1', 10, 'test1@gmail.com')"
                )
        );
    }

}
