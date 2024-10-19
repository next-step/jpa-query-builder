package persistence.sql.dml.query.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.query.builder.DeleteQueryBuilder;

public class DeleteQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블의 모든 컬럼에 대한 delete query")
    void deleteQuery() {
        DeleteQueryBuilder queryBuilder = new DeleteQueryBuilder();
        String expectedQuery = """
                delete from users""";
        assertEquals(queryBuilder.build(Person.class, new H2Dialect()), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person Entity 테이블의 특정 id 컬럼에 대한 delete query")
    void deleteQueryWhereId() {
        DeleteQueryBuilder queryBuilder = new DeleteQueryBuilder();
        String expectedQuery = """
                delete from users where id = ?""";
        assertEquals(queryBuilder.build(Person.class, new H2Dialect()), expectedQuery);
    }

}
