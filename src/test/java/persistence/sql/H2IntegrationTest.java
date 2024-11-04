package persistence.sql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.DatabaseTest;
import persistence.sql.ddl.fixture.EntityWithColumn;
import persistence.sql.dml.DmlQueryBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class H2IntegrationTest extends DatabaseTest {

    @Override
    protected List<String> getTableNames() {
        return List.of("entity_with_column");
    }

    @DisplayName("H2 데이터베이스에 테이블을 생성한다")
    @Test
    void createTable() {
        createTable(EntityWithColumn.class);

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
    void insert() {
        jdbcTemplate.execute("create table entity_with_column (id bigint not null, my_column varchar(255), without_column varchar(255), not_null_column varchar(255) not null, primary key (id))");

        insert(new EntityWithColumn(1L, "my_column", "without_column", "not_null_column"));
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) AS cnt FROM entity_with_column WHERE id = 1", rs -> rs.getInt("cnt"));

        assertThat(count).isEqualTo(1);
    }

    @DisplayName("database 데이터베이스에서 데이터를 조회한다")
    @Test
    void select() {
        jdbcTemplate.execute("create table entity_with_column (id bigint not null, my_column varchar(255), without_column varchar(255), not_null_column varchar(255) not null, primary key (id))");
        jdbcTemplate.execute("insert into entity_with_column (id, my_column, without_column, not_null_column) values (1, 'my_column', 'without_column', 'not_null_column')");

        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        EntityWithColumn entityWithColumn = jdbcTemplate.queryForObject(dmlQueryBuilder.buildSelectByIdQuery(EntityWithColumn.class, 1L), rs -> new EntityWithColumn(
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
    void delete() {
        jdbcTemplate.execute("create table entity_with_column (id bigint not null, my_column varchar(255), without_column varchar(255), not_null_column varchar(255) not null, primary key (id))");
        jdbcTemplate.execute("insert into entity_with_column (id, my_column, without_column, not_null_column) values (1, 'my_column', 'without_column', 'not_null_column')");

        delete(new EntityWithColumn(1L, "my_column", "without_column", "not_null_column"));

        String existsQuery = "SELECT COUNT(*) AS cnt FROM entity_with_column WHERE id = 1";
        Integer count = jdbcTemplate.queryForObject(existsQuery, rs -> rs.getInt("cnt"));

        assertThat(count).isZero();
    }

    @DisplayName("H2 데이터베이스에서 테이블을 삭제한다")
    @Test
    void drop() {
        jdbcTemplate.execute("create table entity_with_column (id bigint not null, my_column varchar(255), without_column varchar(255), not_null_column varchar(255) not null, primary key (id))");

        dropTable(EntityWithColumn.class);

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
