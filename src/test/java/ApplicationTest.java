import builder.QueryBuilderDDL;
import builder.h2.H2QueryBuilderDDL;
import database.DatabaseServer;
import database.H2;
import entity.Person;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class ApplicationTest {

    private DatabaseServer server;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();
    }

    @DisplayName("Users 테이블을 생성한다.")
    @Test
    void createUsersTableTest() throws SQLException {
        //given
        QueryBuilderDDL queryBuilderDDL = new H2QueryBuilderDDL();
        String createQuery = queryBuilderDDL.buildCreateQuery(Person.class);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        //when
        jdbcTemplate.execute(createQuery);

        //then
        RowMapper<String> rowMapper = resultSet -> resultSet.getString(1);
        List<String> objects = jdbcTemplate.query("SHOW TABLES", rowMapper);

        Assertions.assertThat(objects).hasSize(1)
                .containsExactly("USERS");
    }

    @DisplayName("Users 테이블을 제거한다.")
    @Test
    void dropUsersTableTest() throws SQLException {
        //given
        QueryBuilderDDL queryBuilderDDL = new H2QueryBuilderDDL();
        String createQuery = queryBuilderDDL.buildCreateQuery(Person.class);
        String dropQuery = queryBuilderDDL.buildDropQuery(Person.class);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        //when
        jdbcTemplate.execute(createQuery);
        jdbcTemplate.execute(dropQuery);

        //then
        RowMapper<String> rowMapper = resultSet -> resultSet.getString(1);
        List<String> objects = jdbcTemplate.query("SHOW TABLES", rowMapper);

        Assertions.assertThat(objects).hasSize(0);
    }
}
