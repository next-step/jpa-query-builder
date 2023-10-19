package persistence.sql.dml.h2;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;
import persistence.sql.dml.TableDmlQueryBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class H2TableDmlQueryBuilderTest {


    @DisplayName("EntityMetadataModel과 Entity를 받아 insert 쿼리를 생성한다")
    @Test
    void createInsertQuery () {
        // given
        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);

        // when
        DepthPersonFixtureEntity depthPersonFixtureEntity = new DepthPersonFixtureEntity("리리미", 30);
        TableDmlQueryBuilder tableDmlQueryBuilder = new H2TableDmlQueryBuilder();

        String insertQuery = tableDmlQueryBuilder.createInsertQuery(entityMetadataModel, depthPersonFixtureEntity);

        // then
        assertThat(insertQuery).isEqualTo("insert into DepthPersonFixtureEntity(name, age) values ('리리미', 30)");
    }

}
