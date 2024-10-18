package persistence.sql.dml.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static persistence.sql.dml.query.DMLQueryBuilderRegistry.getQueryBuilder;
import static persistence.sql.dml.query.DMLType.DELETE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.dml.query.metadata.ColumnName;
import persistence.sql.dml.query.metadata.WhereCondition;

public class DeleteQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블의 모든 컬럼에 대한 delete query")
    void deleteQuery() {
        DeleteQueryBuilder queryBuilder = (DeleteQueryBuilder) getQueryBuilder(DELETE);
        String expectedQuery = """
                delete from users""";
        assertEquals(queryBuilder.build(Person.class), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person Entity 테이블의 특정 id 컬럼에 대한 delete query")
    void deleteQueryWhereId() {
        DeleteQueryBuilder queryBuilder = (DeleteQueryBuilder) getQueryBuilder(DELETE);
        String expectedQuery = """
                delete from users where id = ?""";
        assertEquals(queryBuilder.build(
                Person.class,
                List.of(new WhereCondition(new ColumnName("id"), "="))
        ), expectedQuery);
    }

}
