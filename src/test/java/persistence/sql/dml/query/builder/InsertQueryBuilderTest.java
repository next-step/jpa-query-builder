package persistence.sql.dml.query.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.query.builder.InsertQueryBuilder;

public class InsertQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블에 대한 insert query 검증")
    void insertQuery() {
        InsertQueryBuilder queryBuilder = new InsertQueryBuilder();
        String expectedQuery = """
                insert into table users (id, nick_name, old, email) values (?, ?, ?, ?)""";
        assertEquals(queryBuilder.build(Person.class, new H2Dialect()), expectedQuery);
    }

}
