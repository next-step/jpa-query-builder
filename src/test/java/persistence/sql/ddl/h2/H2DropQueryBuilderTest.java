package persistence.sql.ddl.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class H2DropQueryBuilderTest {

    private final static H2DropQueryBuilder dropQueryBuilder = H2DropQueryBuilder.getInstance();

    @Test
    @DisplayName("Entity 애노테이션 검증 통과")
    void existsAnnotation() {
        assertDoesNotThrow(() -> dropQueryBuilder.getQuery(Person.class));
    }

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> dropQueryBuilder.getQuery(PureDomain.class), "Drop Query 빌드 대상이 아닙니다.");
    }

    @Test
    @DisplayName("쿼리 정상빌드 테스트")
    void getQuery() {
        String dropQuery = dropQueryBuilder.getQuery(Person.class);
        assertThat(dropQuery).isEqualTo("DROP TABLE users;");
    }

}
