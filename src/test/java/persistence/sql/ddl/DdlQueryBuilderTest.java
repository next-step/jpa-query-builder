package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DdlQueryBuilderTest {
    @Test()
    @DisplayName("기본 @Id 어노테이션만 지닌 Person 엔티티의 CREATE TABLE 쿼리를 생성한다.")
    void createPersonDdlTest() {
        DdlQueryBuilder queryBuilder = new DdlQueryBuilder();

        String expectedQuery = "CREATE TABLE \"Person\" (" +
                "\"id\" bigint NOT NULL, " +
                "\"name\" varchar(255) NULL, " +
                "\"age\" int NULL, " +
                "PRIMARY KEY (\"id\"));";
        String resultQuery = queryBuilder.createTable(Person.class);

        assertEquals(resultQuery, expectedQuery);
    }
}
