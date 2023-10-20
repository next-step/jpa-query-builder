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

class H2FindByIdQueryTest {

    @DisplayName("엔티티에 알맞는 select 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("h2FindByIdQueryTestParams")
    void h2FindByIdQueryTest(Class<?> entityClass, Object id, String expectedQuery) {
        H2FindByIdQuery findByIdQuery = new H2FindByIdQuery();
        String actualQuery = findByIdQuery.generateQuery(new EntityData(entityClass), id);
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> h2FindByIdQueryTestParams() {
        return Stream.of(
                Arguments.of(Person_Week1_Step2_Demand1.class, "test1",
                        "select id, name, age from person_week1_step2_demand1 where id = 'test1'"),
                Arguments.of(Person_Week1_Step2_Demand2.class, 1,
                        "select id, nick_name, old, email from person_week1_step2_demand2 where id = 1"),
                Arguments.of(Person_Week1_Step2_Demand3.class, 1,
                        "select id, nick_name, old, email from users where id = 1")
        );
    }

}
