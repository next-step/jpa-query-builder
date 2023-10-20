package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.Person_Week1_Step2_Demand1;
import persistence.entity.Person_Week1_Step2_Demand2;
import persistence.entity.Person_Week1_Step2_Demand3;
import persistence.sql.Query;
import persistence.sql.dialect.h2.H2Query;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DropQueryBuilderTest {

    @DisplayName("@Entity 애노테이션이 붙은 클래스만 쿼리를 생성할 수 있다.")
    @Test
    void shouldFailWhenEntityIsNotAnnotated() {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(new H2Query());
        assertThatThrownBy(() -> dropQueryBuilder.getQuery(TestWithNoEntityAnnotation.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static class TestWithNoEntityAnnotation {
    }

    @DisplayName("엔티티에 알맞는 drop 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("dropQueryTestParam")
    void dropQueryTest(Query query, Class<?> entityClass, String expectedQuery) {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(query);
        String actualQuery = dropQueryBuilder.getQuery(entityClass);
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> dropQueryTestParam() {
        return Stream.of(
                Arguments.of(new H2Query(), Person_Week1_Step2_Demand1.class, "drop table if exists person_week1_step2_demand1 CASCADE"),
                Arguments.of(new H2Query(), Person_Week1_Step2_Demand2.class, "drop table if exists person_week1_step2_demand2 CASCADE"),
                Arguments.of(new H2Query(), Person_Week1_Step2_Demand3.class, "drop table if exists users CASCADE")
        );
    }

}
