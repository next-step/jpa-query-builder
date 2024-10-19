package persistence.sql.dml.query.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.query.InsertQuery;

public class InsertQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블에 대한 insert query 검증")
    void insertQuery() {
        InsertQuery query = new InsertQuery(Person.class);
        InsertQueryBuilder queryBuilder = InsertQueryBuilder.builder(new H2Dialect())
                .insert(
                        query.tableName(),
                        query.columnNames()
                )
                .values(query.columnNames());
        String expectedQuery = """
                insert into table users (id, nick_name, old, email) values (?, ?, ?, ?)""";
        assertEquals(queryBuilder.build(), expectedQuery);
    }

}
