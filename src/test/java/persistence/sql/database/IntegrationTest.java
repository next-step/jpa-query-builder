package persistence.sql.database;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.converter.EntityConverter;
import persistence.sql.converter.TypeMapper;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.entity.Person;
import persistence.sql.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Integration] Execute Query Integration Test")
class IntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);
    DatabaseServer databaseServer;

    @BeforeEach
    void setDatabase() throws SQLException {
        databaseServer = new H2();
        databaseServer.start();
    }

    @AfterEach
    void stopDatabase() {
        databaseServer.stop();
    }

    @DisplayName("@Entity 어노테이션이 적용된 클래스를 바탕으로 DB에 테이블을 생성한다.")
    @Test
    void create_table_success() throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder(new H2Dialect());

        Connection connection = databaseServer.getConnection();
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);

        EntityConverter entityConverter = new EntityConverter(new TypeMapper());
        Table personTable = entityConverter.convertEntityToTable(Person.class);

        String sql = queryBuilder.buildCreateQuery(personTable);
        jdbcTemplate.execute(sql);

        String tableName = Person.class.isAnnotationPresent(jakarta.persistence.Table.class) ? Person.class.getDeclaredAnnotation(jakarta.persistence.Table.class).name() : "PERSON";

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
            + "FROM information_schema.tables "
            + "WHERE table_name = ? and TABLE_SCHEMA = ? "
            + "LIMIT 1;");
        preparedStatement.setString(1, tableName.toUpperCase());
        preparedStatement.setString(2, "PUBLIC");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        assertThat(resultSet.getInt(1)).isNotZero();
    }


    @DisplayName("@Entity 어노테이션이 적용된 클래스를 바탕으로 DB에 테이블을 제거한다.")
    @Test
    void drop_table_success() throws SQLException {
        QueryBuilder queryBuilder = new QueryBuilder(new H2Dialect());

        Connection connection = databaseServer.getConnection();
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connection);

        EntityConverter entityConverter = new EntityConverter(new TypeMapper());
        Table personTable = entityConverter.convertEntityToTable(Person.class);

        String createQuery = queryBuilder.buildCreateQuery(personTable);
        jdbcTemplate.execute(createQuery);

        String dropQuery = queryBuilder.buildDropQuery(personTable);
        jdbcTemplate.execute(dropQuery);

        String tableName = Person.class.isAnnotationPresent(jakarta.persistence.Table.class) ? Person.class.getDeclaredAnnotation(jakarta.persistence.Table.class).name() : "PERSON";

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
            + "FROM information_schema.TABLES "
            + "WHERE TABLE_NAME = ?  and TABLE_SCHEMA = ? "
            + "LIMIT 1;");
        preparedStatement.setString(1, tableName.toUpperCase());
        preparedStatement.setString(2, "PUBLIC");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        assertThat(resultSet.getInt(1)).isZero();
    }


}
