package persistence.sql.dml.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static persistence.sql.dml.query.DMLQueryBuilderRegistry.getQueryBuilder;
import static persistence.sql.dml.query.DMLType.DELETE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

public class DeleteQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블의 모든 컬럼에 대한 delete query")
    void deleteQuery() {
        DeleteQueryBuilder queryBuilder = (DeleteQueryBuilder) getQueryBuilder(DELETE);
        String expectedQuery = """
                delete from users""";
        assertEquals(queryBuilder.build(Person.class), expectedQuery);
    }

}
