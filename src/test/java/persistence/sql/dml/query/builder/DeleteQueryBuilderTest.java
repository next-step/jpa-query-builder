package persistence.sql.dml.query.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.query.WhereCondition;
import persistence.sql.metadata.TableName;

public class DeleteQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블의 모든 컬럼에 대한 delete query")
    void deleteQuery() {
        DeleteQueryBuilder queryBuilder = DeleteQueryBuilder.builder(new H2Dialect())
                .delete(new TableName(Person.class));
        String expectedQuery = """
                delete from users""";
        assertEquals(queryBuilder.build(), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person Entity 테이블의 특정 id 컬럼에 대한 delete query")
    void deleteQueryWhereId() {
        DeleteQueryBuilder queryBuilder = DeleteQueryBuilder.builder(new H2Dialect())
                .delete(new TableName(Person.class))
                .where(List.of(new WhereCondition("id", "=", 1L)));
        String expectedQuery = """
                delete from users where id = 1""";
        assertEquals(queryBuilder.build(), expectedQuery);
    }

}
