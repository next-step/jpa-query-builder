package persistence.sql.dml.h2;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.core.EntityMetadataModels;
import persistence.sql.dml.DmlGenerator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class H2DmlGeneratorTest {

    private DmlGenerator dmlGenerator;

    @BeforeEach
    void setUp() {
        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);
        EntityMetadataModels entityMetadataModels = new EntityMetadataModels(Set.of(entityMetadataModel));
        dmlGenerator = new H2DmlGenerator(new EntityMetadataModelHolder(entityMetadataModels));
    }


    @DisplayName("Entity를 받아 insert 쿼리를 생성한다")
    @Test
    void createInsertQuery() {
        // given
        DepthPersonFixtureEntity depthPersonFixtureEntity = new DepthPersonFixtureEntity("리리미", 30);

        // when
        String insertQuery = dmlGenerator.insert(depthPersonFixtureEntity);

        // then
        assertThat(insertQuery).isEqualTo("insert into DepthPersonFixtureEntity(name, age) values ('리리미', 30)");
    }

}
