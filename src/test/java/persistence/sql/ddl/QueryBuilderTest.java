package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryBuilderTest {

    private static final Logger logger = LoggerFactory.getLogger(QueryBuilderTest.class);

    @Test
    @DisplayName("Person 엔터티 create 쿼리 만들기")
    public void createQueryTest() {
        QueryBuilder queryBuilder = new QueryBuilder();

        String query = queryBuilder.create(Person.class);

        logger.debug(query);
        assertThat(query).isEqualTo("CREATE TABLE Person (" +
                "id BIGINT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "age INT);");
    }

}
