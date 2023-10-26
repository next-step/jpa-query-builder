package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.metadata.EntityMetadata;

import static org.junit.jupiter.api.Assertions.*;

class DropQueryBuilderTest {
    private final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder();

    private final EntityMetadata entityMetadata = new EntityMetadata(Person.class);

    @DisplayName("Person 객체로 DROP 쿼리 생성 테스트")
    @Test
    void test_buildQuery() {
        assertEquals(dropQueryBuilder.buildQuery(entityMetadata),
                "DROP TABLE users IF EXISTS;");
    }
}
