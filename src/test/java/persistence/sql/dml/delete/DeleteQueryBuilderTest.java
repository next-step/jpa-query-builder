package persistence.sql.dml.delete;

import example.entity.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(DeleteQueryBuilderTest.class);

    @Test
    @DisplayName("Delete query 테스트")
    void deleteQueryTest() {
        Class<PersonV3> personV3Class = PersonV3.class;

        String deleteQuery = DeleteQueryBuilder.newInstance()
                .entityClass(personV3Class)
                .whereIdCondition(String.valueOf(1L))
                .build();

        logger.debug("{}", deleteQuery);
    }
}
