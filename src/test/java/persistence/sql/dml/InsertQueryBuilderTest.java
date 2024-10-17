package persistence.sql.dml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

public class InsertQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person Entity 테이블에 대한 insert query 검증")
    void insertQuery() {
        DMLQueryBuilder queryBuilder = new InsertQueryBuilder();
        String expectedQuery = """
                insert into table users (id, nick_name, old, email) values (?, ?, ?, ?)""";
        assertEquals(queryBuilder.build(Person.class), expectedQuery);
    }

}
