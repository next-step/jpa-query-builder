package persistence.sql.ddl.drop;

import example.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(DropQueryBuilderTest.class);

    @Test
    @DisplayName("DDL Drop query 생성 테스트")
    void createDdlDropQueryTest() {
        Class<Person> personClass = Person.class;
        DropQueryBuilder queryBuilder = DropQueryBuilder.newInstance();
        String dropQuery = queryBuilder.build(personClass.getSimpleName());
        logger.debug("DDL drop query : {}", dropQuery);
    }
}
