package persistence.sql.dialect.h2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.Person_Week1_Step2_Demand1;
import persistence.entity.Person_Week1_Step2_Demand2;
import persistence.entity.Person_Week1_Step2_Demand3;
import persistence.sql.entity.EntityData;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class H2FindAllQueryTest {

    @DisplayName("엔티티에 알맞는 select 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("h2FindAllQueryTestParams")
    void h2FindAllQueryTest(Class<?> entityClass, String expectedQuery) {
        H2FindAllQuery findAllQuery = new H2FindAllQuery();
        String actualQuery = findAllQuery.generateQuery(new EntityData(entityClass));
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> h2FindAllQueryTestParams() {
        return Stream.of(
                Arguments.of(Person_Week1_Step2_Demand1.class,
                        "select id, name, age from person_week1_step2_demand1"),
                Arguments.of(Person_Week1_Step2_Demand2.class,
                        "select id, nick_name, old, email from person_week1_step2_demand2"),
                Arguments.of(Person_Week1_Step2_Demand3.class,
                        "select id, nick_name, old, email from users")
        );
    }
}
