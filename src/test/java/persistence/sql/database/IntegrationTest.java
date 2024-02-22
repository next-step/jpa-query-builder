package persistence.sql.database;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.entity.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Integration] Execute Query Integration Test")
class IntegrationTest {
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

        String sql = queryBuilder.buildCreateQuery(Person.class);
        jdbcTemplate.execute(sql);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
            + "FROM information_schema.tables "
            + "WHERE table_name = ?"
            + "LIMIT 1;");
        preparedStatement.setString(1, "PERSON");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        assertThat(resultSet.getInt(1)).isNotZero();
    }

}
