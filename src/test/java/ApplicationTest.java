import builder.ddl.builder.CreateQueryBuilder;
import builder.ddl.builder.DropQueryBuilder;
import builder.ddl.DDLBuilderData;
import builder.ddl.dataType.DB;
import builder.ddl.dataType.H2DataType;
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
        DropQueryBuilder queryBuilder = new DropQueryBuilder();
        String dropQuery = queryBuilder.buildQuery(DDLBuilderData.createDDLBuilderData(Person.class, DB.H2));
        jdbcTemplate.execute(dropQuery);
        this.h2DBConnection.stop();
    }

    @DisplayName("Users 테이블을 생성한다.")
    @Test
    void createUsersTableTest() {
        //given
        CreateQueryBuilder queryBuilder = new CreateQueryBuilder();
        String createQuery = queryBuilder.buildQuery(DDLBuilderData.createDDLBuilderData(Person.class, DB.H2));
        //when
        jdbcTemplate.execute(createQuery);

        //then
        RowMapper<String> rowMapper = resultSet -> resultSet.getString(1);
        List<String> objects = jdbcTemplate.query("SHOW TABLES", rowMapper);

        Assertions.assertThat(objects).hasSize(1)
                .containsExactly("USERS");
    }

}
