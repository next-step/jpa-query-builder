package persistence.sql.dml;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import fixture.FetchWhereQueryFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.EntityMetadataModel;
import persistence.core.EntityMetadataModelHolder;
import persistence.core.EntityMetadataModels;
import persistence.sql.dml.DmlGenerator;
import persistence.sql.dml.where.FetchWhereQuery;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DmlGeneratorTest {

    private DmlGenerator dmlGenerator;

    @BeforeEach
    void setUp() {
        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);
        EntityMetadataModels entityMetadataModels = new EntityMetadataModels(Set.of(entityMetadataModel));
        dmlGenerator = new DmlGenerator(new EntityMetadataModelHolder(entityMetadataModels));
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

    @DisplayName("Entity를 받아 select All 쿼리를 생성한다")
    @Test
    void findAll() {
        // when
        String findAllQuery = dmlGenerator.findAll(DepthPersonFixtureEntity.class);

        // then
        assertThat(findAllQuery).isEqualTo("select id, name, age from DepthPersonFixtureEntity");
    }

    @DisplayName("Entity와 where 조건을 받아 select 쿼리를 생성한다")
    @Test
    void findBy() {
        // given
        FetchWhereQuery fixtureDepthPersonFetchWhereQuery = FetchWhereQueryFixture.getFixtureDepthPersonFetchWhereQuery();

        // when
        String findByQuery = dmlGenerator.findBy(DepthPersonFixtureEntity.class, fixtureDepthPersonFetchWhereQuery);

        // then
        assertThat(findByQuery).isEqualTo("select id, name, age from DepthPersonFixtureEntity where id = 1 and name = 'ok'");
    }

    @DisplayName("Entity와 where 조건을 받아 delete 쿼리를 생성한다")
    @Test
    void delete() {
        // given
        FetchWhereQuery fixtureDepthPersonFetchWhereQuery = FetchWhereQueryFixture.getFixtureDepthPersonFetchWhereQuery();

        // when
        String deleteQuery = dmlGenerator.delete(DepthPersonFixtureEntity.class, fixtureDepthPersonFetchWhereQuery);

        // then
        assertThat(deleteQuery).isEqualTo("delete from DepthPersonFixtureEntity where id = 1 and name = 'ok'");
    }
}
