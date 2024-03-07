package persistence.sql;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.targetentity.Person;
import persistence.sql.query.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class QueryExecutionTest {

    private final EntityMetaService entityMetaService = new EntityMetaService(new QueryBuilder());
    private final JdbcTemplate jdbcTemplate;

    public QueryExecutionTest() throws SQLException {
        DatabaseServer server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @Test
    void select_all_쿼리_실행_테스트() {
        // given
        String createTableQuery = entityMetaService.generateCreateTableQuery(persistence.sql.dml.targetentity.Person.class);
        jdbcTemplate.execute(createTableQuery);

        List<Object> values1 = List.of(1, "유인근", 29, "keun0390@naver.com");
        List<Object> values2 = List.of(2, "유인근123", 123, "keun123@naver.com");

        String insertQuery1 = entityMetaService.generateInsertQuery(persistence.sql.dml.targetentity.Person.class, values1);
        jdbcTemplate.execute(insertQuery1);

        String insertQuery2 = entityMetaService.generateInsertQuery(persistence.sql.dml.targetentity.Person.class, values2);
        jdbcTemplate.execute(insertQuery2);

        // when
        String selectAllQuery = entityMetaService.generateSelectAllQuery(Person.class);
        List<Person> persons = jdbcTemplate.query(selectAllQuery, resultSet -> {
            String id = resultSet.getString("id");
            String nickName = resultSet.getString("nick_name");
            String old = resultSet.getString("old");
            String email = resultSet.getString("email");
            return new Person(Long.valueOf(id), nickName, Integer.valueOf(old), email);
        });

        // then
        assertAll(
                () -> assertThat(persons.get(0)).isEqualTo(new Person(1L, "유인근", 29, "keun0390@naver.com")),
                () -> assertThat(persons.get(1)).isEqualTo(new Person(2L, "유인근123", 123, "keun123@naver.com"))
        );
    }
}
