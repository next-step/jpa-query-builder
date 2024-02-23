package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.h2.jdbc.JdbcResultSet;
import org.h2.result.ResultInterface;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import persistence.entity.annotatedentity.Person;

import static java.sql.ResultSetMetaData.columnNullable;
import static persistence.sql.ddl.common.TestSqlConstant.DROP_TABLE;
import static persistence.sql.ddl.common.TestSqlConstant.SELECT_ALL_ROWS;

class QueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(QueryBuilderTest.class);
    DatabaseServer server;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        try {
            server = new H2();
            server.start();
            jdbcTemplate = new JdbcTemplate(server.getConnection());
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(DROP_TABLE);
        server.stop();
    }

    @Test
    @DisplayName("[요구사항 1] @Column 애노테이션이 없는 Person 엔티티를 이용하여 create 쿼리 만든다.")
    void 요구사항1_test() throws SQLException {
        //given
        List<String> expectedColumnNames = List.of("ID", "NAME", "AGE");
        String expectedTableName = "PERSON";

        // when
        jdbcTemplate.execute(new QueryBuilder(persistence.entity.baic.Person.class).buildWithoutAnnotation());

        // then
        ResultInterface tableSchema = getTableSchema();
        Assertions.assertThat(getTableName(tableSchema)).isEqualTo(expectedTableName);
        Assertions.assertThat(getColumnNames(tableSchema).containsAll(expectedColumnNames)).isTrue();
    }

    @Test
    @DisplayName("[요구사항 2] @Column 애노테이션이 있는 Person 엔티티를 이용하여 create 쿼리 만든다.")
    void 요구사항2_test() throws SQLException {
        //given
        List<String> expectedColumnNames = List.of("ID", "NICK_NAME", "OLD", "EMAIL");
        String expectedTableName = "PERSON";

        // when
        jdbcTemplate.execute(new QueryBuilder(Person.class).buildWithAnnotation());

        // then
        ResultInterface tableSchema = getTableSchema();
        Assertions.assertThat(getTableName(tableSchema)).isEqualTo(expectedTableName);
        Assertions.assertThat(getColumnNames(tableSchema).containsAll(expectedColumnNames)).isTrue();

        Assertions.assertThat(hasNullableColumn("NICK_NAME")).isTrue();
        Assertions.assertThat(hasNullableColumn("OLD")).isTrue();
        Assertions.assertThat(hasNullableColumn("EMAIL")).isFalse();
    }

    /**
     * 주어진 컬럼이 nullable하면 true
     */
    private boolean hasNullableColumn(String columnName) throws SQLException {
        ResultSetMetaData meta = getQueryResult().getMetaData();
        int columnCount = meta.getColumnCount();

        int columnIdx = 0;
        for (int i = 1; i <= columnCount; i++) {
            if (!meta.getColumnName(i).equals(columnName)) {
                continue;
            }
            columnIdx = i;
            break;
        }
        return columnNullable == meta.isNullable(columnIdx);
    }

    /**
     * 테이블 스키마를 리턴한다.
     */
    private ResultInterface getTableSchema() throws SQLException {
        ResultSet selectQueryResult = getQueryResult();
        return ((JdbcResultSet) selectQueryResult).getResult();
    }

    /**
     * 쿼리 결과 값을 리턴한다.
     */
    private ResultSet getQueryResult() throws SQLException {
        return server.getConnection().createStatement().executeQuery(SELECT_ALL_ROWS);
    }

    /**
     * 컬럼 이름 목록을 리턴한다.
     */
    private static List<String> getColumnNames(ResultInterface tableSchema) {
        List<String> actualColumnNames = new ArrayList<>();
        int columnSize = tableSchema.getVisibleColumnCount();

        for (int i = 0; i < columnSize; i++) {
            actualColumnNames.add(tableSchema.getColumnName(i));
        }
        return actualColumnNames;
    }

    /**
     * 테이블 이름을 리턴한다.
     */
    private static String getTableName(ResultInterface tableSchema) {
        return tableSchema.getTableName(0);
    }
}