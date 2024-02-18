package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.Person;

import static org.junit.jupiter.api.Assertions.*;

class DDLQueryBuilderTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void createTableQuery() {
        DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder();
        String query = ddlQueryBuilder.createTableQuery(Person.class);

        assertEquals("CREATE TABLE person (id BIGINT, name VARCHAR(255), age INT, PRIMARY KEY (id))", query);
    }

}
