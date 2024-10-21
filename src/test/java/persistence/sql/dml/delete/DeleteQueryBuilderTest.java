package persistence.sql.dml.delete;

import example.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(DeleteQueryBuilderTest.class);

    @Test
    @DisplayName("Delete query 테스트")
    void deleteQueryTest() {
        Class<Person> personClass = Person.class;

        String deleteQuery = DeleteQueryBuilder.generateQuery(personClass, String.valueOf(1L));

        logger.debug("{}", deleteQuery);
    }
}
