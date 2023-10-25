package persistence.sql.dml;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.core.EntityMetadataModels;
import persistence.sql.dml.InsertQueryBuilder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {

    private InsertQueryBuilder insertQueryBuilder;

    @BeforeEach
    void setUp() {
        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);
        EntityMetadataModels entityMetadataModels = new EntityMetadataModels(Set.of(entityMetadataModel));
        EntityMetadataModelHolder entityMetadataModelHolder = new EntityMetadataModelHolder(entityMetadataModels);
        insertQueryBuilder = new InsertQueryBuilder(entityMetadataModelHolder);
    }

    @DisplayName("EntityMetadataModel과 Entity를 받아 insert 쿼리를 생성한다")
    @Test
    void createInsertQuery () {
        DepthPersonFixtureEntity depthPersonFixtureEntity = new DepthPersonFixtureEntity("리리미", 30);

        String insertQuery = insertQueryBuilder.createInsertQuery(depthPersonFixtureEntity);

        // then
        assertThat(insertQuery).isEqualTo("insert into DepthPersonFixtureEntity(name, age) values ('리리미', 30)");
    }

}
