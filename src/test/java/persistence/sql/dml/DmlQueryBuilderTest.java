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
        final Person person = new Person(1L, "Kent Beck", 64, "beck@example.com");
        assertEquals(expectedForInsert(), dmlQueryBuilder.insert(Person.class, person));
    }

    @DisplayName("클래스 정보를 바탕으로 리스트를 위한 SELECT 쿼리를 생성한다.")
    @Test
    void findAll() {
        final DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder(new H2Dialect());
        assertEquals(expectedForFindAll(), dmlQueryBuilder.select(Person.class));
    }

    @DisplayName("클래스 정보를 바탕으로 단건을 위한 SELECT 쿼리를 생성한다.")
    @Test
    void findById(){
        final DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder(new H2Dialect());
        assertEquals(expectedForFindById(), dmlQueryBuilder.select(Person.class, 1L));
    }

    private String expectedForFindById() {
        return "SELECT * FROM USERS WHERE id = 1;";
    }

    private String expectedForFindAll() {
        return "SELECT * FROM USERS;";
    }

    private String expectedForInsert() {
        return "INSERT INTO USERS (id, nick_name, old, email) VALUES (1, 'Kent Beck', 64, 'beck@example.com');";
    }
}
