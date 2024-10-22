package persistence.sql.dml.update;

import example.entity.Person;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(UpdateQueryBuilderTest.class);

    @Test
    void updateQueryTest() {
        Person person = new Person();
        person.setId(1L);
        person.setName("new name");
        person.setAge(22);
        person.setEmail("new email");

        String updateQuery = UpdateQueryBuilder.generateQuery(person);

        logger.debug("{}", updateQuery);
    }
}