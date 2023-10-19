package persistence.sql.ddl.dialect.h2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.Person_Week1_Step2_Demand1;
import persistence.entity.Person_Week1_Step2_Demand2;
import persistence.entity.Person_Week1_Step2_Demand3;
import persistence.sql.ddl.entity.EntityData;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class H2DropQueryTest {

    @DisplayName("엔티티에 알맞는 drop 쿼리를 생성한다.")
    @ParameterizedTest
    @MethodSource("dropQueryTestParam")
    void dropQueryTest(Class<?> entityClass, String expectedQuery) {
        H2DropQuery dropQuery = new H2DropQuery();
        String actualQuery = dropQuery.generateQuery(new EntityData(entityClass));
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> dropQueryTestParam() {
        return Stream.of(
                Arguments.of(Person_Week1_Step2_Demand1.class, "drop table if exists person_week1_step2_demand1 CASCADE"),
                Arguments.of(Person_Week1_Step2_Demand2.class, "drop table if exists person_week1_step2_demand2 CASCADE"),
                Arguments.of(Person_Week1_Step2_Demand3.class, "drop table if exists users CASCADE")
        );
    }
}