package persistence.sql.dml.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.dml.query.metadata.ColumnName;
import persistence.sql.dml.query.metadata.WhereCondition;

public class SelectQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블의 모든 컬럼에 대한 select query")
    void selectQuery() {
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        String expectedQuery = """
                select id, nick_name, old, email from users""";
        assertEquals(queryBuilder.build(Person.class), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person Entity 테이블의 id 컬럼에 대한 select query")
    void selectQueryById() {
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        String expectedQuery = """
                select id, nick_name, old, email from users where id = ?""";
        assertEquals(
                queryBuilder.build(Person.class, List.of(new WhereCondition(new ColumnName("id"), "="))),
                expectedQuery
        );
    }

}
