package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.entity.Person;
import persistence.entity.Person_Week1_Step2_Demand1;
import persistence.entity.Person_Week1_Step2_Demand2;
import persistence.entity.Person_Week1_Step2_Demand3;
import persistence.sql.entity.EntityData;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    @DisplayName("조건절이 없는 SELECT 문을 생성한다 (findAll)")
    @ParameterizedTest
    @MethodSource("generateSelectQueryWithoutWhereClauseParams")
    void generateSelectQueryWithoutWhereClause(Class<?> entityClass, String expected) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String actualQuery = selectQueryBuilder.generateQuery(new EntityData(entityClass));
        assertThat(actualQuery).isEqualTo(expected);
    }

    private static Stream<Arguments> generateSelectQueryWithoutWhereClauseParams() {
        return Stream.of(
                Arguments.of(Person_Week1_Step2_Demand1.class,
                        "select id, name, age from person_week1_step2_demand1"),
                Arguments.of(Person_Week1_Step2_Demand2.class,
                        "select id, nick_name, old, email from person_week1_step2_demand2"),
                Arguments.of(Person_Week1_Step2_Demand3.class,
                        "select id, nick_name, old, email from users"),
                Arguments.of(Person.class,
                        "select id, nick_name, old, email from users")
        );
    }

    @DisplayName("조건절이 있는 SELECT 문을 생성한다. (ex findById)")
    @ParameterizedTest
    @MethodSource("generateSelectQueryWithWhereClauseParams")
    void generateSelectQueryWithWhereClause(Class<?> entityClass, Object id, String expectedQuery) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        EntityData entityData = new EntityData(entityClass);
        selectQueryBuilder.appendWhereClause(entityData.getEntityColumns().getIdColumn().getColumnName(), id);

        String actualQuery = selectQueryBuilder.generateQuery(entityData);
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    private static Stream<Arguments> generateSelectQueryWithWhereClauseParams() {
        return Stream.of(
                Arguments.of(
                        Person.class,
                        "test1",
                        "select id, nick_name, old, email from users where id = 'test1'"),
                Arguments.of(
                        Person.class,
                        1,
                        "select id, nick_name, old, email from users where id = 1")
        );
    }

}
