package persistence.sql.dml.select;

import example.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(SelectQueryBuilderTest.class);

    @Test
    @DisplayName("FindAll query 테스트")
    void findAllQueryTest() {
        Class<Person> personClass = Person.class;
        String selectQuery = SelectQueryBuilder.generateQuery(personClass);

        logger.debug("query : {}", selectQuery);
    }

    @Test
    @DisplayName("FindById query 테스트")
    void findByIdTest() {
        Class<Person> personClass = Person.class;

        String selectQuery = SelectQueryBuilder.generateQuery(personClass, String.valueOf(1L));

        logger.debug("query : {}", selectQuery);
    }
}
