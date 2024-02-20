package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
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

        dmlQueryBuilder = new DMLQueryBuilder(person);
    }

    @Test
    public void insertQueryTest() throws Exception {
        String query = dmlQueryBuilder.insertQuery();
        assertEquals("INSERT INTO users (nick_name, old, email) VALUES ('"+name+"', "+age+", '"+email+"')", query);
    }

}
