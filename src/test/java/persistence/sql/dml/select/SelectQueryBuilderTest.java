package persistence.sql.dml.select;

import example.entity.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(SelectQueryBuilderTest.class);

    @Test
    @DisplayName("FindAll query 테스트")
    void findAllQueryTest() {
        Class<PersonV3> personV3Class = PersonV3.class;
        String selectQuery = SelectQueryBuilder.generateQuery(personV3Class);

        logger.debug("query : {}", selectQuery);
    }

    @Test
    @DisplayName("FindById query 테스트")
    void findByIdTest() {
        Class<PersonV3> personV3Class = PersonV3.class;

        String selectQuery = SelectQueryBuilder.generateQuery(personV3Class, String.valueOf(1L));

        logger.debug("query : {}", selectQuery);
    }
}
