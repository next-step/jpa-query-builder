package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.DbmsStrategy;
import persistence.testutils.ReflectionTestSupport;

import static org.assertj.core.api.Assertions.*;

class InsertDMLQueryBuilderTest {

    @Test
    void build() {
        Person person = new Person();
        ReflectionTestSupport.setFieldValue(person, "id", 1L);
        ReflectionTestSupport.setFieldValue(person, "name", "name");
        ReflectionTestSupport.setFieldValue(person, "age", 20);
        ReflectionTestSupport.setFieldValue(person, "email", "asdf@asdf.com");

        InsertDMLQueryBuilder<Person> dmlQueryBuilder = new InsertDMLQueryBuilder<>(DbmsStrategy.H2, person);

        assertThat(dmlQueryBuilder.build()).isEqualTo("INSERT INTO USERS (ID, NICK_NAME, OLD, EMAIL) VALUES (1, 'name', 20, 'asdf@asdf.com');");
    }
}