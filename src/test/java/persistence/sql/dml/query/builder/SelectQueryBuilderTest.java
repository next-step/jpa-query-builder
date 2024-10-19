package persistence.sql.dml.query.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;

public class SelectQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블의 모든 컬럼에 대한 select query")
    void selectQuery() {
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        String expectedQuery = """
                select id, nick_name, old, email from users""";
        assertEquals(queryBuilder.build(Person.class, new H2Dialect()), expectedQuery);
    }

    @Test
    @DisplayName("[성공] Person Entity 테이블의 id 컬럼에 대한 select query")
    void selectQueryById() {
        SelectQueryBuilder queryBuilder = new SelectQueryBuilder();
        String expectedQuery = """
                select id, nick_name, old, email from users where id = ?""";
        assertEquals(queryBuilder.build(Person.class, new H2Dialect()), expectedQuery);
    }

}
