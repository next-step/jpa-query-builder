package sql.dml;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.Person;
import persistence.sql.dml.QueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    QueryBuilder queryBuilder = new QueryBuilder(Person.class);
    Class<?> clazz = Person.class;

    final DatabaseServer server = new H2();

    JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

    public QueryBuilderTest() throws SQLException {
    }

    @Test
    @DisplayName("findAll 테스트")
    void findAll() {
        jdbcTemplate.execute("CREATE TABLE USERS (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL)");
        queryBuilder.run(jdbcTemplate);
        assertThat(queryBuilder.findAll(jdbcTemplate)).isEqualTo("");
    }

    @Test
    @DisplayName("findById테스트")
    void findById() {
        queryBuilder.findById(jdbcTemplate, 1L);
    }

    @Test
    @DisplayName("insert문 스트링 체크")
    void insert() {
        Person person = new Person(1L, "jskim", 33, "qazwsx3745@naver.com");
        assertThat(queryBuilder.insert(person)).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('jskim', 33, 'qazwsx3745@naver.com')");
    }

    @Test
    @DisplayName("column파트 스트링 체크")
    void getColumnClaus() {
        String testString = queryBuilder.columnsClause(clazz);
        assertThat(testString).isEqualTo("nick_name, old, email");
    }

    @Test
    @DisplayName("테이블 이름 확인")
    void getTablename() {
        assertThat(queryBuilder.getTableName()).isEqualTo("users");
    }

    @Test
    @DisplayName("테이블에 넣을 값 비교")
    void getValueClause() {
        String query = queryBuilder.valueClause(queryBuilder.generatePersonData().get(0));
        assertThat(query).isEqualTo("'jskim', 33, 'qazwsx3745@naver.com'");
    }
//INSERT INTO users (nick_name, old, email) VALUES ('jskim', 33, 'qazwsx3745@naver.com');
}
