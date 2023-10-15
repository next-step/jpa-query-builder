package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;

import static org.junit.jupiter.api.Assertions.*;

class DropQueryBuilderTest {

    @Test
    @DisplayName("Entity 애노테이션 검증 통과")
    void existsAnnotation() {
        assertDoesNotThrow(() -> DropQueryBuilder.getQuery(Person.class));
    }

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> DropQueryBuilder.getQuery(PureDomain.class), "Drop Query 빌드 대상이 아닙니다.");
    }
}