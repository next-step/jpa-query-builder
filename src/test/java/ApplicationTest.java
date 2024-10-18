import builder.ddl.DDLBuilder;
import builder.ddl.DDLType;
import builder.ddl.h2.H2DDLBuilder;
import database.H2DBConnection;
import entity.Person;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class ApplicationTest {

    private H2DBConnection h2DBConnection;
    private JdbcTemplate jdbcTemplate;

    //정확한 테스트를 위해 메소드마다 DB실행
    @BeforeEach
    void eachSetUp() throws SQLException {
        this.h2DBConnection = new H2DBConnection();
        this.jdbcTemplate = this.h2DBConnection.start();
    }

    //정확한 테스트를 위해 메소드마다 테이블 DROP
    @AfterEach
    void tearDown() {
        DDLBuilder ddlBuilder = new H2DDLBuilder();
        String dropQuery = ddlBuilder.queryBuilder(DDLType.DROP, Person.class);
        jdbcTemplate.execute(dropQuery);
        this.h2DBConnection.stop();
    }

    @DisplayName("Users 테이블을 생성한다.")
    @Test
    void createUsersTableTest() {
        //given
        DDLBuilder ddlBuilder = new H2DDLBuilder();
        String createQuery = ddlBuilder.queryBuilder(DDLType.CREATE, Person.class);
        //when
        jdbcTemplate.execute(createQuery);

        //then
        RowMapper<String> rowMapper = resultSet -> resultSet.getString(1);
        List<String> objects = jdbcTemplate.query("SHOW TABLES", rowMapper);

        Assertions.assertThat(objects).hasSize(1)
                .containsExactly("USERS");
    }

}
