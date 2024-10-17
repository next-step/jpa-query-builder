package persistence.sql.dml.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

public class SelectQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블의 모든 컬럼에 대한 select query")
    void selectQuery() {
        DMLQueryBuilder queryBuilder = new SelectQueryBuilder();
        String expectedQuery = """
                select id, nick_name, old, email from users""";
        assertEquals(queryBuilder.build(Person.class), expectedQuery);
    }

}
