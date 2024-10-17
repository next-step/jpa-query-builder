package persistence.sql.dml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.dml.query.DMLQueryBuilder;
import persistence.sql.dml.query.SelectQueryBuilder;

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
