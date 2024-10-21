package persistence.sql.dml.insert;

import example.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(InsertQueryBuilderTest.class);

    @Test
    @DisplayName("Insert query 테스트")
    void insertQueryTest() throws Exception {
        Person person = new Person();
        person.setName("sdgvas");
        person.setAge(123);

        String insertQuery = InsertQueryBuilder.generateQuery(person);

        logger.debug("{}", insertQuery);
    }
}
