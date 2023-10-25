package persistence.sql.dml;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.core.EntityMetadataModels;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.dml.where.EntityCertification;
import persistence.sql.dml.where.FetchWhereQuery;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.dml.where.WhereQueryBuilder;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    private SelectQueryBuilder selectQueryBuilder;

    @BeforeEach
    void setUp() {
        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);
        EntityMetadataModels entityMetadataModels = new EntityMetadataModels(Set.of(entityMetadataModel));
        EntityMetadataModelHolder entityMetadataModelHolder = new EntityMetadataModelHolder(entityMetadataModels);

        selectQueryBuilder = new SelectQueryBuilder(entityMetadataModelHolder);
    }


    @DisplayName("Entity Class 타입을 받아 Find All Select 쿼리를 생성한다")
    @Test
    void createFindAllSelectQuery() {
        // when
        String findAllSelectQuery = selectQueryBuilder.findAll(DepthPersonFixtureEntity.class);

        // then
        assertThat(findAllSelectQuery).isEqualTo("select id, name, age from DepthPersonFixtureEntity");
    }

    @DisplayName("Entity Class 타입과, FetchWhereQuerie를 받아 Select 쿼리를 생성한다")
    @Test
    void createSelectQuery() {
        // given
        EntityCertification<DepthPersonFixtureEntity> certification = EntityCertification.certification(DepthPersonFixtureEntity.class);
        WhereQuery idEqual = certification.equal("id", 1L);
        WhereQuery nameEqual = certification.equal("name", "ok");

        FetchWhereQuery fetchWhereQuery = WhereQueryBuilder.builder().and(List.of(idEqual, nameEqual));

        // when
        String whereQuery = selectQueryBuilder.findBy(DepthPersonFixtureEntity.class, fetchWhereQuery);

        // then
        assertThat(whereQuery).isEqualTo("select id, name, age from DepthPersonFixtureEntity where id = 1 and name = 'ok'");
    }

}
