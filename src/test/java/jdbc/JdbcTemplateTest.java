package jdbc;

import database.DatabaseServer;
import database.H2;
import exception.EmptyResultException;
import exception.IncorrectResultSizeException;
import org.junit.jupiter.api.*;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.query.DdlQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import test.PersonGenerator;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTemplateTest {

    Person person = PersonGenerator.getDefualtPerson();
    JdbcTemplate jdbcTemplate;
    InsertQueryBuilder insertQueryBuilder;
    EntityMetaData entityMetaData;

    @BeforeEach
    void setUp() throws SQLException {
        entityMetaData = new EntityMetaData(person);

        final DatabaseServer server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());

        //테이블 생성
        DdlQueryBuilder ddlQueryBuilder = DdlQueryBuilder.getInstance();
        jdbcTemplate.execute(ddlQueryBuilder.createTable(entityMetaData));

        insertQueryBuilder = new InsertQueryBuilder();
    }


    @Test
    @DisplayName("result data 가 없을 때 테스트")
    void noResultTest() {
        String insertQuery = insertQueryBuilder.create(entityMetaData);
//        jdbcTemplate.execute(insertQuery);

        assertThrows(EmptyResultException.class, this::queryForObject);
    }


    @Test
    @DisplayName("result data 가 1개일때 테스트")
    void singleResultTest() {
        String insertQuery = insertQueryBuilder.create(entityMetaData);
        jdbcTemplate.execute(insertQuery);
        queryForObject();
    }

    @Test
    @DisplayName("result data 가 2개 이상일 때 테스트")
    void moreThenTwoResult() {
        String insertQuery = insertQueryBuilder.create(entityMetaData);
        jdbcTemplate.execute(insertQuery);
        jdbcTemplate.execute(insertQuery);

        assertThrows(IncorrectResultSizeException.class, this::queryForObject
        );
    }


    private void queryForObject() {
        Person p = jdbcTemplate.queryForObject("select * from users",
                (resultSet) -> {
                    Person person1 = new Person();
                    person1.setId(resultSet.getLong("id"));
                    person1.setAge(resultSet.getInt("old"));
                    person1.setName(resultSet.getString("nick_name"));
                    person1.setEmail(resultSet.getString("email"));
                    return person1;
                });
    }


}