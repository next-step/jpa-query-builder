package persistence.sql.dml.h2;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.core.EntityMetadataModels;
import persistence.sql.dml.SelectQueryBuilder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class H2SelectQueryBuilderTest {

    private SelectQueryBuilder selectQueryBuilder;

    @BeforeEach
    void setUp() {
        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);
        EntityMetadataModels entityMetadataModels = new EntityMetadataModels(Set.of(entityMetadataModel));
        EntityMetadataModelHolder entityMetadataModelHolder = new EntityMetadataModelHolder(entityMetadataModels);

        selectQueryBuilder = new H2SelectQueryBuilder(entityMetadataModelHolder);
    }


    @DisplayName("Entity Class 타입을 받아 Find All Select 쿼리를 생성한다")
    @Test
    void createFindAllSelectQuery() {
        // when
        String findAllSelectQuery = selectQueryBuilder.findAll(DepthPersonFixtureEntity.class);

        // then
        assertThat(findAllSelectQuery).isEqualTo("select id, name, age from DepthPersonFixtureEntity");
    }

    @DisplayName("Entity Class 타입과, FetchWhereQueries를 받아 Select 쿼리를 생성한다")
    @Test
    void createSelectQuery() {
        // given


    }

}
