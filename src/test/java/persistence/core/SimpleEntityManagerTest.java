package persistence.core;

import database.DatabaseServer;
import database.H2;
import entityloaderfixture.depth.DepthPersonFixtureEntity;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.JdbcTypeJavaClassMapping;
import persistence.sql.ddl.TableDdlQueryBuilder;
import persistence.sql.dml.DmlGenerator;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleEntityManagerTest {

    private DatabaseServer server;

    private JdbcTemplate jdbcTemplate;

    private DmlGenerator dmlGenerator;

    private TableDdlQueryBuilder tableDdlQueryBuilder;

    private EntityMetadataModel testEntityMetadataModel;

    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws SQLException {
        EntityMetadataModelFactory entityMetadataModelFactory = new EntityMetadataModelFactory();
        EntityMetadataModels entityMetadataModels = entityMetadataModelFactory.createEntityMetadataModels(List.of(DepthPersonFixtureEntity.class));
        EntityMetadataModelHolder entityMetadataModelHolder = new EntityMetadataModelHolder(entityMetadataModels);
        dmlGenerator = new DmlGenerator(entityMetadataModelHolder);
        tableDdlQueryBuilder = new TableDdlQueryBuilder(new JdbcTypeJavaClassMapping());

        testEntityMetadataModel = entityMetadataModels.getMetadataModels()
                .stream()
                .filter(it -> it.isSameEntityType(DepthPersonFixtureEntity.class))
                .findFirst()
                .orElseThrow();

        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        entityManager = new SimpleEntityManager(jdbcTemplate, dmlGenerator, entityMetadataModelHolder);
        String ddlQUery = tableDdlQueryBuilder.createDdlQuery(testEntityMetadataModel);
        jdbcTemplate.execute(ddlQUery);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(tableDdlQueryBuilder.createDropTableQuery(testEntityMetadataModel));
        server.stop();
    }

    @DisplayName("DepthPersonFixtureEntity를 저장한다")
    @Test
    void persist() throws Exception {
        // given
        DepthPersonFixtureEntity depthPersonFixtureEntity = getDepthPersonFixtureEntity();

        // when
        entityManager.persist(depthPersonFixtureEntity);
        DepthPersonFixtureEntity depthPersonFixtureEntity1 = entityManager.find(DepthPersonFixtureEntity.class, 1L);

        // then
        assertThat(depthPersonFixtureEntity1).isEqualTo(depthPersonFixtureEntity);
    }

    @DisplayName("DepthPersonFixtureEntity를 삭제한다")
    @Test
    void delete() throws Exception {
        // given
        DepthPersonFixtureEntity depthPersonFixtureEntity = getDepthPersonFixtureEntity();
        entityManager.persist(depthPersonFixtureEntity);

        // when
        entityManager.remove(depthPersonFixtureEntity);

        DepthPersonFixtureEntity findDepthPersonFixtureEntity = entityManager.find(DepthPersonFixtureEntity.class, 1L);
        // then
        assertThat(findDepthPersonFixtureEntity).isNull();
    }

    private DepthPersonFixtureEntity getDepthPersonFixtureEntity() throws NoSuchFieldException, IllegalAccessException {
        DepthPersonFixtureEntity depthPersonFixtureEntity = new DepthPersonFixtureEntity("리리미", 30);
        Field idField = depthPersonFixtureEntity.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(depthPersonFixtureEntity, 1L);
        return depthPersonFixtureEntity;
    }
}
