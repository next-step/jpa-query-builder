package persistence.sql.ddl.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;
import persistence.sql.ddl.CreateQueryBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class H2CreateQueryBuilderTest {

    private static final H2CreateQueryBuilder createQueryBuilder = H2CreateQueryBuilder.getInstance();

    @Test
    @DisplayName("Entity, PK 애노테이션 검증 정상 통과")
    void existsAnnotation() {
        assertDoesNotThrow(() -> createQueryBuilder.getQuery(Person.class));
    }

    @Test
    @DisplayName("쿼리 정상 빌드 테스트")
    void getQuery() {
        String createQuery = createQueryBuilder.getQuery(Person.class);
        assertThat(createQuery).isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR, old INT, email VARCHAR NOT NULL);");
    }

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> createQueryBuilder.getQuery(PureDomain.class), "Create Query 빌드 대상이 아닙니다.");
    }
}
