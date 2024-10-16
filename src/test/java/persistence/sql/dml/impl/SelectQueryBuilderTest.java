package persistence.sql.dml.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.clause.WhereConditionalClause;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.fixture.PersonV3;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SelectQueryBuilder 테스트")
class SelectQueryBuilderTest {
    private final SelectQueryBuilder builder = new SelectQueryBuilder(CamelToSnakeConverter.getInstance());
    private final MetadataLoader<PersonV3> loader = new SimpleMetadataLoader<>(PersonV3.class);

    @Test
    @DisplayName("build 함수는 SELECT 쿼리를 생성한다.")
    void testSelectQueryBuild() {
        // given
        WhereConditionalClause clause = WhereConditionalClause.builder().column("id").eq("1");

        // when
        String query = builder.build(loader, clause);

        // then
        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1");
    }

}