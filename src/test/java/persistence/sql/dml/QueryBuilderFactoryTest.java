package persistence.sql.dml;

import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.clause.Clause;
import persistence.sql.QueryBuilderFactory;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.data.QueryType;
import persistence.sql.clause.InsertColumnValueClause;
import persistence.sql.clause.SetValueClause;
import persistence.sql.dml.impl.SimpleMetadataLoader;
import persistence.sql.clause.WhereConditionalClause;
import persistence.sql.fixture.PersonV3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderFactoryTest {
    private final QueryBuilderFactory factory = QueryBuilderFactory.getInstance();
    private static final MetadataLoader<PersonV3> loader = new SimpleMetadataLoader<>(PersonV3.class);


    @ParameterizedTest
    @DisplayName("buildQuery 함수는 매개변수 queryType에 따라 적절한 쿼리를 생성한다.")
    @MethodSource("provideQueryBuildParameters")
    void testBuildQuery(QueryType queryType, String expected, List<Clause> clauses) {
        // given

        // when
        String actual = factory.buildQuery(queryType, loader, clauses.toArray(Clause[]::new));

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideQueryBuildParameters() {
        PersonV3 person = new PersonV3("catsbi", 55, "catsbi@naver.com", 123);

        List<Field> fields = loader.getFieldAllByPredicate(field -> !field.isAnnotationPresent(Id.class));
        List<Clause> setClauses = fields.stream()
                .map(field -> (Clause)SetValueClause.newInstance(field, person,
                        loader.getColumnName(field, CamelToSnakeConverter.getInstance())))
                .collect(Collectors.toCollection(ArrayList::new));
        Clause idClause = new WhereConditionalClause("id", "1", "=");
        setClauses.add(idClause);

        return Stream.of(
                Arguments.of(QueryType.SELECT, "SELECT id, nick_name, old, email FROM users WHERE id = 1", List.of(idClause)),
                Arguments.of(QueryType.INSERT, "INSERT INTO users (nick_name, old, email) VALUES ('catsbi', 55, 'catsbi@naver.com')", List.of(InsertColumnValueClause.newInstance(person, CamelToSnakeConverter.getInstance()))),
                Arguments.of(QueryType.UPDATE, "UPDATE users SET nick_name = 'catsbi', old = 55, email = 'catsbi@naver.com' WHERE id = 1", setClauses),
                Arguments.of(QueryType.DELETE, "DELETE FROM users WHERE id = 1", List.of(idClause))
        );
    }

}