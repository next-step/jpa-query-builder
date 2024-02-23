package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dml.DMLQueryBuilder;

public class DMLQueryBuilderTest {

    private Person person;
    private DMLQueryBuilder dmlQueryBuilder;

    final String name = "kassy";
    final int age = 30;
    final String email = "test@gmail.com";

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setEmail(email);

        dmlQueryBuilder = DMLQueryBuilder.getInstance();
    }

    @Test
    @DisplayName("insert 쿼리 생성")
    public void insertQueryTest() {
        String query = dmlQueryBuilder.insertSql(person);

        assertEquals("INSERT INTO users (nick_name, old, email) VALUES ('"+name+"', "+age+", '"+email+"')", query);
    }

    @Test
    @DisplayName("selectAll 쿼리 생성")
    public void selectAllQueryTest() {
        String query = dmlQueryBuilder.selectAllSql(Person.class);

        assertEquals("SELECT * FROM users", query);
    }

    @Test
    @DisplayName("selectById 쿼리 생성")
    void selectByIdSqlTest() {
        String query = dmlQueryBuilder.selectByIdQuery(Person.class, 1L);

        assertEquals("SELECT * FROM users WHERE id = 1", query);
    }

    @Test
    @DisplayName("delete 쿼리 생성")
    void deleteSqlTest() {
        person.setId(1L);
        String query = dmlQueryBuilder.deleteSql(person);

        assertEquals("DELETE FROM users WHERE id = 1", query);
    }
}
