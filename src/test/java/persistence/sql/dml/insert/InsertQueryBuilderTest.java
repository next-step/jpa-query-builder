package persistence.sql.dml.insert;

import example.entity.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(InsertQueryBuilderTest.class);

    @Test
    @DisplayName("Insert query 테스트")
    void insertQueryTest() throws Exception {
        PersonV3 personV3 = new PersonV3();
        personV3.setName("sdgvas");
        personV3.setAge(123);

        String insertQuery = InsertQueryBuilder.newInstance()
                .entity(personV3)
                .build();

        logger.debug("{}", insertQuery);
    }
}
