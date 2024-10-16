package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.dialect.H2Dialect;

import static org.junit.jupiter.api.Assertions.*;

class DropQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person 테이블에 대한 drop query 검증")
    void dropQuery() {
        DropQueryBuilder queryBuilder = new DropQueryBuilder();
        String dropQuery = "drop table if exists users";
        assertEquals(queryBuilder.build(Person.class, new H2Dialect()), dropQuery);
    }

}
