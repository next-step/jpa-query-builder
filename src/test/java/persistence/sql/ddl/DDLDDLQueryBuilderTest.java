package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class DDLDDLQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(DDLDDLQueryBuilderTest.class);

    @Test
    public void testCreateTableQueryForPerson() {
        // Given: DDLQueryBuilder 인스턴스 생성
        QueryBuilderAdapter ddlQueryBuilder = new CreateTableDDLQueryBuilder();

        // When: Person 클래스에 대한 CREATE TABLE 쿼리 생성
        String createTableQuery = ddlQueryBuilder.executeQuery( Person.class, DDLType.CREATE);

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


    @Test
    public void testDropTableQueryForPerson() {
        // Given: DDLQueryBuilder 인스턴스 생성
        QueryBuilderAdapter ddlQueryBuilder = new DropTableQueryBuilder();

        // When: Person 클래스에 대한 DROP TABLE 쿼리 생성
        String dropTableQuery = ddlQueryBuilder.executeQuery( Person.class, DDLType.DROP);

        // Expected: 예상되는 쿼리
        String expectedQuery = "DROP TABLE users;";

        logger.info("expectedQuery: {}", expectedQuery);
        logger.info("actualQuery: {}", dropTableQuery);

        // Then: 생성된 쿼리와 예상 쿼리 비교
        assertEquals(expectedQuery, dropTableQuery);
    }
}
