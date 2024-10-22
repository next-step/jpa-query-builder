package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DmlQueryBuilderTest {
    @DisplayName("클래스 정보를 바탕으로 INSERT 쿼리를 생성한다.")
    @Test
    void insert() {
        final DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        final Person person = new Person("Kent Beck", 64, "beck@example.com");
        assertEquals(getExpected(), dmlQueryBuilder.insert(Person.class, person));
    }

    private String getExpected() {
        return "INSERT INTO USERS (nick_name, old, email) VALUES ('Kent Beck', 64, 'beck@example.com');";
    }
}
