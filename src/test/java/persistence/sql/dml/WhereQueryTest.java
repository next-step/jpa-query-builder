package persistence.sql.dml;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.where.WhereQuery;

class WhereQueryTest {

    @DisplayName("id를 찾는 WhereQuery를 생성한다")
    @Test
    void createWhereQuery() {
        // given
        String columnName = "id";
        String value = "1";

        // when
        WhereQuery whereQuery = WhereQuery.builder(DepthPersonFixtureEntity.class)
                .equal(columnName, value)
                .build();

        // then

    }
}
