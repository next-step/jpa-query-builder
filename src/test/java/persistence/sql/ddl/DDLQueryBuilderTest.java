package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.Person;
import persistence.entity.Person1;
import persistence.entity.Person2;

import static org.junit.jupiter.api.Assertions.*;

class DDLQueryBuilderTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private DDLQueryBuilder ddlQueryBuilder = DDLQueryBuilder.getInstance();

    @Test
    @DisplayName("CreateTableQuery_@Id 어노테이션은 Primary key 생성")
    void createTableQueryReq1() {
        String query = ddlQueryBuilder.createTableQuery(Person1.class);

        assertEquals("CREATE TABLE person1 (id BIGINT , name VARCHAR(255) , age INT , PRIMARY KEY (id))", query);
    }

    @Test
    @DisplayName("CreateTableQuery_@GeneratedValue 어노테이션은 Auto Increment 생성 및 @Column 컬럼명 생성")
    void createTableQueryReq2() {
        String query = ddlQueryBuilder.createTableQuery(Person2.class);

        assertEquals("CREATE TABLE person2 (id BIGINT AUTO_INCREMENT , nick_name VARCHAR(255) , old INT , email VARCHAR(255) NOT NULL , PRIMARY KEY (id))", query);
    }

    @Test
    @DisplayName("CreateTableQuery_@Transient제외")
    void createTableQueryReq3() {
        String query = ddlQueryBuilder.createTableQuery(Person.class);

        assertEquals("CREATE TABLE users (id BIGINT AUTO_INCREMENT , nick_name VARCHAR(255) , old INT , email VARCHAR(255) NOT NULL , PRIMARY KEY (id))", query);
    }

    @Test
    @DisplayName("DropTableQuery")
    void dropTableQuery() {
        String query = ddlQueryBuilder.dropTableQuery(Person.class);

        assertEquals("DROP TABLE users", query);
    }

}
