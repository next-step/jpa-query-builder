package persistence.sql.metadata;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityMetadataTest {
    private final EntityMetadata entityMetadata = new EntityMetadata(Person.class);

    @DisplayName("Person 객체의 테이블 명을 가져온다.")
    @Test
    void test_getTableName() {
        //Given

        //When & Then
        assertEquals(entityMetadata.getTableName(), "user");
    }


}
