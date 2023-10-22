package persistence.sql.dml.h2;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.core.EntityMetadataModels;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.where.EntityCertification;
import persistence.sql.dml.where.FetchWhereQuery;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.dml.where.WhereQueryBuilder;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class H2DeleteQueryBuilderTest {

    private DeleteQueryBuilder deleteQueryBuilder;

    @BeforeEach
    void setUp() {
        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);
        EntityMetadataModels entityMetadataModels = new EntityMetadataModels(Set.of(entityMetadataModel));
        EntityMetadataModelHolder entityMetadataModelHolder = new EntityMetadataModelHolder(entityMetadataModels);
        deleteQueryBuilder = new H2DeleteQueryBuilder(entityMetadataModelHolder);
    }

    @DisplayName("Entity 타입과 FetchWhereQuery를 받아 delete query를 생성하여 반환한다")
    @Test
    void createDeleteQuery() {
        // given
        EntityCertification<DepthPersonFixtureEntity> certification = EntityCertification.certification(DepthPersonFixtureEntity.class);
        WhereQuery idEqual = certification.equal("id", 1L);
        WhereQuery nameEqual = certification.equal("name", "ok");

        WhereQueryBuilder whereQueryBuilder = WhereQueryBuilder.builder();
        FetchWhereQuery fetchWhereQueries = whereQueryBuilder.or(List.of(idEqual, nameEqual));

        // when
        String deleteQuery = deleteQueryBuilder.delete(DepthPersonFixtureEntity.class, fetchWhereQueries);

        // then
        assertThat(deleteQuery).isEqualTo("delete from DepthPersonFixtureEntity where id = 1 or name = 'ok'");
    }

}
