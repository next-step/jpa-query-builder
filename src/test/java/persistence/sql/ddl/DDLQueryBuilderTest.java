package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.study.ReflectionTest;

import static org.junit.jupiter.api.Assertions.*;

class DDLQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(DDLQueryBuilderTest.class);

    @Test
    public void testCreateTableQueryForPerson() {
        // Given: DDLQueryBuilder 인스턴스 생성
        DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder();

        // When: Person 클래스에 대한 CREATE TABLE 쿼리 생성
        String createTableQuery = DDLQueryBuilder.createTable(ddlQueryBuilder, Person.class);

        // Expected: 예상되는 쿼리
        String expectedQuery = "CREATE TABLE users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "nick_name VARCHAR(255), " +
                "old INTEGER, " +
                "email VARCHAR(255) NOT NULL" +
                ");";

        logger.info("expectedQuery: {}", expectedQuery);
        logger.info("actualQuery: {}", createTableQuery);

        // Then: 생성된 쿼리와 예상 쿼리 비교
        assertEquals(expectedQuery, createTableQuery);

    }
}
