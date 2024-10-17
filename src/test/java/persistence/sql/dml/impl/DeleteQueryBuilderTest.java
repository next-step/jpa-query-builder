package persistence.sql.dml.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.clause.Clause;
import persistence.sql.clause.WhereConditionalClause;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.data.QueryType;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.fixture.PersonV3;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DeleteQueryBuilder 테스트")
class DeleteQueryBuilderTest {
    private final DeleteQueryBuilder builder = new DeleteQueryBuilder(CamelToSnakeConverter.getInstance());
    private final MetadataLoader<PersonV3> loader = new SimpleMetadataLoader<>(PersonV3.class);

    @Test
    @DisplayName("build 함수는 조건절을 전달하면 조건절이 추가 된 DELETE 쿼리를 생성한다.")
    void testDeleteQueryBuild() {
        // when
        String query = builder.build(loader, WhereConditionalClause.builder().column("id").eq("1"));

        // then
        assertThat(query).isEqualTo("DELETE FROM users WHERE id = 1");
    }

    @Test
    @DisplayName("build 함수는 조건절을 전달하지 않으면 조건절이 추가되지 않은 DELETE 쿼리를 생성한다.")
    void testDeleteQueryBuildWithoutWhereClause() {
        // when
        String query = builder.build(loader);

        // then
        assertThat(query).isEqualTo("DELETE FROM users");
    }

    @Test
    @DisplayName("queryType 함수는 DELETE를 반환한다.")
    void testQueryType() {
        // when
        var queryType = builder.queryType();

        // then
        assertThat(queryType).isEqualTo(QueryType.DELETE);
    }

    @ParameterizedTest
    @CsvSource({"DELETE, true", "SELECT, false", "UPDATE, false", "INSERT, false"})
    @DisplayName("supported 함수는 매개변수가 DELETE일 경우 true를 반환한다.")
    void testSupported(QueryType queryType, boolean expected) {
        // when
        var supported = builder.supported(queryType);

        // then
        assertThat(supported).isEqualTo(expected);
    }
}