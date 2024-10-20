package persistence.sql.ddl.query.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.metadata.TableName;

class DropQueryBuilderTest {

    @Test
    @DisplayName("[성공] Person 테이블에 대한 drop query 검증")
    void dropQuery() {
        DropQueryBuilder queryBuilder = DropQueryBuilder.builder(new H2Dialect())
                .drop(new TableName(Person.class));
        String dropQuery = "drop table if exists users";
        assertEquals(queryBuilder.build(), dropQuery);
    }

}
