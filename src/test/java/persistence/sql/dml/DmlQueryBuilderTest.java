package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DmlQueryBuilderTest {
    @DisplayName("클래스 정보를 바탕으로 INSERT 쿼리를 생성한다.")
    @Test
    void insert() {
        final DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder(Person.class);
        assertEquals(getExpected(), dmlQueryBuilder.insert());
    }

    private String getExpected() {
        return """
                INSERT INTO users (nick_name, old, email)
                VALUES ('Kent Beck', 64, 'beck@example.com');
                """;
    }
}
