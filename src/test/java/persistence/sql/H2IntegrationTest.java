package persistence.sql;

import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.fixture.EntityWithColumn;
import persistence.sql.dml.DmlQueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class H2IntegrationTest {

    private H2 h2;
    private static final Logger log = LoggerFactory.getLogger(H2IntegrationTest.class);

    @BeforeEach
    void setUp() throws Exception {
        h2 = new H2();
        h2.start();
        clearTables();
    }

    private void clearTables() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(h2.getConnection());

        String fetchTablesSql = """
                 SELECT TABLE_NAME\s
                 FROM INFORMATION_SCHEMA.TABLES\s
                 WHERE TABLE_NAME = '%s'\s
                \s""".formatted("entity_with_column".toUpperCase());

        jdbcTemplate.query(fetchTablesSql, rs -> rs.getString("TABLE_NAME"))
                .forEach(tableName -> {
                            jdbcTemplate.execute("DROP TABLE " + tableName);
                            log.info("Dropped table: {}", tableName);
                        }
                );
    }

    @AfterEach
    void tearDown() {
        h2.stop();
    }

    @DisplayName("H2 데이터베이스에 테이블을 생성한다")
    @Test
    void createTable() throws Exception {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        String createTableQuery = ddlQueryBuilder.buildCreateQuery(EntityWithColumn.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(h2.getConnection());
        jdbcTemplate.execute(createTableQuery);

        String selectTableQuery = """
                 SELECT COUNT(*) AS cnt \s
                 FROM INFORMATION_SCHEMA.TABLES \s
                 WHERE TABLE_NAME = '%s'\s
                \s""";
        Integer count = jdbcTemplate.queryForObject(
                selectTableQuery.formatted("entity_with_column".toUpperCase()),
                rs -> rs.getInt("cnt")
        );

        assertThat(count).isEqualTo(1);
    }

    @DisplayName("H2 데이터베이스에 데이터를 삽입한다")
    @Test
    void insert() throws Exception {
        DmlQueryBuilder<EntityWithColumn> dmlQueryBuilder = DmlQueryBuilder.from(EntityWithColumn.class);
        EntityWithColumn entityWithColumn = new EntityWithColumn(1L, "my_column", "without_column", "not_null_column");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(h2.getConnection());
        jdbcTemplate.execute("create table entity_with_column (id bigint not null, my_column varchar(255), without_column varchar(255), not_null_column varchar(255) not null, primary key (id))");
        jdbcTemplate.execute(dmlQueryBuilder.buildInsertQuery(entityWithColumn));

        String existsQuery = "SELECT COUNT(*) AS cnt FROM entity_with_column WHERE id = 1";
        Integer count = jdbcTemplate.queryForObject(existsQuery, rs -> rs.getInt("cnt"));

        assertThat(count).isEqualTo(1);
    }

    @DisplayName("H2 데이터베이스에서 데이터를 조회한다")
    @Test
    void select() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(h2.getConnection());
        jdbcTemplate.execute("create table entity_with_column (id bigint not null, my_column varchar(255), without_column varchar(255), not_null_column varchar(255) not null, primary key (id))");
        jdbcTemplate.execute("insert into entity_with_column (id, my_column, without_column, not_null_column) values (1, 'my_column', 'without_column', 'not_null_column')");

        DmlQueryBuilder<EntityWithColumn> dmlQueryBuilder = DmlQueryBuilder.from(EntityWithColumn.class);
        EntityWithColumn entityWithColumn = jdbcTemplate.queryForObject(dmlQueryBuilder.buildSelectByIdQuery(1L), rs -> new EntityWithColumn(
                rs.getLong("id"),
                rs.getString("my_column"),
                rs.getString("without_column"),
                rs.getString("not_null_column")
        ));

        assertSoftly(softly -> {
            softly.assertThat(entityWithColumn.getId()).isEqualTo(1L);
            softly.assertThat(entityWithColumn.getWithColumn()).isEqualTo("my_column");
            softly.assertThat(entityWithColumn.getWithoutColumn()).isEqualTo("without_column");
            softly.assertThat(entityWithColumn.getNotNullColumn()).isEqualTo("not_null_column");
        });
    }

    @DisplayName("H2 데이터베이스에서 데이터를 삭제한다")
    @Test
    void delete() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(h2.getConnection());
        jdbcTemplate.execute("create table entity_with_column (id bigint not null, my_column varchar(255), without_column varchar(255), not_null_column varchar(255) not null, primary key (id))");
        jdbcTemplate.execute("insert into entity_with_column (id, my_column, without_column, not_null_column) values (1, 'my_column', 'without_column', 'not_null_column')");

        DmlQueryBuilder<EntityWithColumn> dmlQueryBuilder = DmlQueryBuilder.from(EntityWithColumn.class);
        jdbcTemplate.execute(dmlQueryBuilder.buildDeleteByIdQuery(1L));

        String existsQuery = "SELECT COUNT(*) AS cnt FROM entity_with_column WHERE id = 1";
        Integer count = jdbcTemplate.queryForObject(existsQuery, rs -> rs.getInt("cnt"));

        assertThat(count).isZero();
    }

    @DisplayName("H2 데이터베이스에서 테이블을 삭제한다")
    @Test
    void dropTable() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(h2.getConnection());
        jdbcTemplate.execute("create table entity_with_column (id bigint not null, my_column varchar(255), without_column varchar(255), not_null_column varchar(255) not null, primary key (id))");

        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        String dropTableQuery = ddlQueryBuilder.buildDropQuery(EntityWithColumn.class);
        jdbcTemplate.execute(dropTableQuery);

        String selectTableQuery = """
                 SELECT COUNT(*) AS cnt \s
                 FROM INFORMATION_SCHEMA.TABLES \s
                 WHERE TABLE_NAME = '%s'\s
                \s""";
        Integer count = jdbcTemplate.queryForObject(
                selectTableQuery.formatted("entity_with_column".toUpperCase()),
                rs -> rs.getInt("cnt")
        );

        assertThat(count).isZero();
    }

}
