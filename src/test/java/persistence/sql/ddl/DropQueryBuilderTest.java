package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DropQueryBuilderTest {
    private DropQueryBuilder dropQueryBuilder = new DropQueryBuilder();

    @DisplayName("Person 객체로 DROP 쿼리 생성 테스트")
    @Test
    void test_buildQuery() {
        assertEquals(dropQueryBuilder.buildQuery(Person.class), "DROP TABLE users;");
    }
}
