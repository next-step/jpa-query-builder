package persistence.sql.dml.where;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WhereQueryBuilderTest {


    @DisplayName("WhereQueryBuilder로 부터 and 조건의 FetchWhereQueries를 생성하여 and 조건의 where query를 반환한다")
    @Test
    void createFetchWhereAndQueries() {
        // given
        EntityCertification<DepthPersonFixtureEntity> certification = EntityCertification.certification(DepthPersonFixtureEntity.class);
        WhereQuery idEqual = certification.equal("id", 1L);
        WhereQuery nameEqual = certification.equal("name", "ok");

        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);

        // when
        WhereQueryBuilder whereQueryBuilder = WhereQueryBuilder.builder();
        FetchWhereQueries fetchWhereQueries = whereQueryBuilder.and(List.of(idEqual, nameEqual)).build();

        List<String> queries = fetchWhereQueries.getQueries(entityMetadataModel);

        // then
        assertThat(queries).containsExactly("id = 1 and name = 'ok'");
    }

    @DisplayName("WhereQueryBuilder로 부터 or 조건의 FetchWhereQueries를 생성하여 or 조건의 where query를 반환한다")
    @Test
    void createFetchWhereOrQueries() {
        // given
        EntityCertification<DepthPersonFixtureEntity> certification = EntityCertification.certification(DepthPersonFixtureEntity.class);
        WhereQuery idEqual = certification.equal("id", 1L);
        WhereQuery nameEqual = certification.equal("name", "ok");

        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);

        // when
        WhereQueryBuilder whereQueryBuilder = WhereQueryBuilder.builder();
        FetchWhereQueries fetchWhereQueries = whereQueryBuilder.or(List.of(idEqual, nameEqual)).build();

        List<String> queries = fetchWhereQueries.getQueries(entityMetadataModel);

        // then
        assertThat(queries).containsExactly("id = 1 or name = 'ok'");
    }
}
