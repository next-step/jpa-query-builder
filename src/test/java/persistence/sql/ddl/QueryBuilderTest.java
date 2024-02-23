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
import persistence.entity.annotated.Person;

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
        jdbcTemplate.execute(new QueryBuilder(persistence.entity.basic.Person.class).getCreateQuery());

        // then
        ResultInterface tableSchema = getTableSchema(expectedTableName);
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
        ResultInterface tableSchema = getTableSchema(expectedTableName);
        Assertions.assertThat(getTableName(tableSchema)).isEqualTo(expectedTableName);
        Assertions.assertThat(getColumnNames(tableSchema).containsAll(expectedColumnNames)).isTrue();

        Assertions.assertThat(hasNullableColumn("NICK_NAME", expectedTableName)).isTrue();
        Assertions.assertThat(hasNullableColumn("OLD", expectedTableName)).isTrue();
        Assertions.assertThat(hasNullableColumn("EMAIL", expectedTableName)).isFalse();
    }

    @Test
    @DisplayName("[요구사항 3] 3.1 @Table 애노테이션이 붙은 필드의 name을 테이블명으로 가지는 create 쿼리 만들")
    void 요구사항3_1_test() throws SQLException {
        //given
        String expectedTableName = "USERS";

        // when
        jdbcTemplate.execute(new QueryBuilder(persistence.entity.notcolumn.Person.class).buildWithAnnotation());

        // then
        ResultInterface tableSchema = getTableSchema(expectedTableName);
        Assertions.assertThat(getTableName(tableSchema)).isEqualTo(expectedTableName);
    }

    @Test
    @DisplayName("[요구사항 3.2] @Transient 애노테이션이 붙은 필드는 제하고 create 쿼리 만든다.")
    void 요구사항3_2_test() throws SQLException {
        //given
        List<String> expectedColumnNames = List.of("ID", "NICK_NAME", "OLD", "EMAIL");
        String expectedTableName = "USERS";

        // when
        jdbcTemplate.execute(new QueryBuilder(persistence.entity.notcolumn.Person.class).buildWithAnnotation());

        // then
        ResultInterface tableSchema = getTableSchema(expectedTableName);
        Assertions.assertThat(getColumnNames(tableSchema).containsAll(expectedColumnNames)).isTrue();

        Assertions.assertThat(tableSchema.getVisibleColumnCount()).isEqualTo(4);
        Assertions.assertThat(hasNullableColumn("NICK_NAME", expectedTableName)).isTrue();
        Assertions.assertThat(hasNullableColumn("OLD", expectedTableName)).isTrue();
        Assertions.assertThat(hasNullableColumn("EMAIL", expectedTableName)).isFalse();
    }

    /**
     * 주어진 컬럼이 nullable하면 true
     */
    private boolean hasNullableColumn(String columnName, String tableName) throws SQLException {
        ResultSetMetaData meta = getQueryResult(tableName).getMetaData();
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
    private ResultInterface getTableSchema(String tableName) throws SQLException {
        ResultSet resultSet = getQueryResult(tableName);
        return ((JdbcResultSet) resultSet).getResult();
    }

    /**
     * 쿼리 결과 값을 리턴한다.
     */
    private ResultSet getQueryResult(String tableName) throws SQLException {
        return server.getConnection().createStatement().executeQuery(String.format(SELECT_ALL_ROWS, tableName));
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