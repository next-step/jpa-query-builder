package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.H2Dialect;
import persistence.sql.ddl.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DmlQueryBuilderTest {
    @DisplayName("클래스 정보를 바탕으로 INSERT 쿼리를 생성한다.")
    @Test
    void insert() {
        final DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder(new H2Dialect());
        final Person person = new Person("Kent Beck", 64, "beck@example.com");
        assertEquals(expectedForInsert(), dmlQueryBuilder.insert(Person.class, person));
    }

    @DisplayName("클래스 정보를 바탕으로 SELECT 쿼리를 생성한다.")
    @Test
    void select() {
        final DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder(new H2Dialect());
        assertEquals(expectedForSelect(), dmlQueryBuilder.select(Person.class));
    }

    private String expectedForSelect() {
        return "SELECT * FROM USERS;";
    }

    private String expectedForInsert() {
        return "INSERT INTO USERS (nick_name, old, email) VALUES ('Kent Beck', 64, 'beck@example.com');";
    }
}
