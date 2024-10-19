package persistence.sql.dml.query.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.query.SelectQuery;
import persistence.sql.dml.query.WhereCondition;

public class SelectQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블의 모든 컬럼에 대한 select query")
    void selectQuery() {
        SelectQuery query = new SelectQuery(Person.class);
        SelectQueryBuilder queryBuilder = SelectQueryBuilder.builder(new H2Dialect())
                .select(query.columnNames())
                .from(query.tableName());
        String expectedQuery = """
                select id, nick_name, old, email from users""";
        assertEquals(queryBuilder.build(), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person Entity 테이블의 id 컬럼에 대한 select query")
    void selectQueryById() {
        SelectQuery query = new SelectQuery(Person.class);
        SelectQueryBuilder queryBuilder = SelectQueryBuilder.builder(new H2Dialect())
                .select(query.columnNames())
                .from(query.tableName())
                .where(List.of(new WhereCondition("id", "=", 1L)));

        String expectedQuery = """
                select id, nick_name, old, email from users where id = 1""";
        assertEquals(queryBuilder.build(), expectedQuery);
    }

}
