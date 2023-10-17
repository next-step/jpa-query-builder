package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import persistence.sql.ddl.QueryDdl;

class QueryDmlTest {
    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void start() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());

        createTable(Person.class);
    }

    @Test
    @DisplayName("@Entity가 설정되어 있지 않은 경우 Query를 생성하지 않음")
    void notEntity() {
        //given
        Person person = new Person(1L, "name", 3, "zz@cc.com", 1);

        String query = QueryDml.insert(person);

        jdbcTemplate.execute("INSERT INTO users (id, nick_name, old, email) VALUES(null, null, null, null)");

        Assertions.assertThat(query).isNull();
    }

    private <T> void createTable(Class<T> tClass) {
        jdbcTemplate.execute(QueryDdl.create(tClass));
    }

    @AfterEach
    void stop() {
        server.stop();
    }
}
