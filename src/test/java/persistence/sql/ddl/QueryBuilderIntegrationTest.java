package persistence.sql.ddl;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.*;
import persistence.sql.model.TableName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcServerTest
public class QueryBuilderIntegrationTest {

    private static final String SELECT_TABLES_SQL = "SELECT table_name FROM information_schema.tables WHERE table_schema='PUBLIC'";
    @Test
    void 테이블_생성() {
        Class<Person> clazz = Person.class;

        CreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(clazz);
        JdbcTemplate jdbcTemplate = JdbcServerExtension.getJdbcTemplate();
        jdbcTemplate.execute(createQueryBuilder.makeQuery());

        List<String> tableNames = jdbcTemplate.query(
                SELECT_TABLES_SQL,
                resultSet -> resultSet.getString("table_name")
        );

        TableName tableName = new TableName(clazz);
        assertThat(tableNames).contains(tableName.getValue().toUpperCase());
    }

    @Test
    void 테이블_제거() {
        Class<Person> clazz = Person.class;

        AbstractCreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(clazz);
        JdbcTemplate jdbcTemplate = JdbcServerExtension.getJdbcTemplate();
        jdbcTemplate.execute(createQueryBuilder.makeQuery());

        H2DropQueryBuilder dropQueryBuilder = new H2DropQueryBuilder(clazz);
        jdbcTemplate.execute(dropQueryBuilder.makeQuery());

        List<String> tableNames = jdbcTemplate.query(
                SELECT_TABLES_SQL,
                resultSet -> resultSet.getString("table_name")
        );

        assertThat(tableNames).isEmpty();
    }

    @Test
    void 데이터_삽입() {
        final String name = "이름";
        final Integer age = 11;
        final String email = "email@test.com";
        Person person = new Person(name, age, email, null);

        JdbcTemplate jdbcTemplate = JdbcServerExtension.getJdbcTemplate();

        CreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(Person.class);
        jdbcTemplate.execute(createQueryBuilder.makeQuery());

        InsertQueryBuilder insertQueryBuilder = new H2InsertQueryBuilder(person);
        jdbcTemplate.execute(insertQueryBuilder.makeQuery());
    }

    @Test
    void 데이터_삭제() {
        JdbcTemplate jdbcTemplate = JdbcServerExtension.getJdbcTemplate();

        CreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(Person.class);
        jdbcTemplate.execute(createQueryBuilder.makeQuery());

        final String name = "이름";
        final Integer age = 11;
        final String email = "email@test.com";
        Person person = new Person(name, age, email, null);
        InsertQueryBuilder insertQueryBuilder = new H2InsertQueryBuilder(person);
        jdbcTemplate.execute(insertQueryBuilder.makeQuery());

        SelectQueryBuilder selectQueryBuilder = new H2SelectQueryBuilder(Person.class);
        Person findByIdPerson = jdbcTemplate.queryForObject(selectQueryBuilder.findById(1L), resultSet -> {
            long id = resultSet.getLong("id");
            int old = resultSet.getInt("old");
            String getEmail = resultSet.getString("email");
            String nickname = resultSet.getString("nick_name");
            return new Person(id, nickname, old, getEmail);
        });

        DeleteQueryBuilder deleteQueryBuilder = new H2DeleteQueryBuilder(findByIdPerson);
        jdbcTemplate.execute(deleteQueryBuilder.delete());
    }
}
