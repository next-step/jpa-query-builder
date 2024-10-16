package persistence.sql.ddl.drop;

import example.entity.PersonV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DdlDropQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(DdlDropQueryBuilderTest.class);

    @Test
    @DisplayName("DDL Drop query 생성 테스트")
    void createDdlDropQueryTest() {
        Class<PersonV2> personClass = PersonV2.class;
        DdlDropQueryBuilder queryBuilder = DdlDropQueryBuilder.newInstance();
        String dropQuery = queryBuilder.build(personClass.getSimpleName());
        logger.debug("DDL drop query : {}", dropQuery);
    }
}
