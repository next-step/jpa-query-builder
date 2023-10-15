package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateQueryBuilderTest {

    @Test
    @DisplayName("Entity, PK 애노테이션 검증 정상 통과")
    void existsAnnotation() {
        assertDoesNotThrow(() -> CreateQueryBuilder.getQuery(Person.class));
    }

    @Test
    @DisplayName("쿼리 정상 빌드 테스트")
    void getQuery() {
        String createQuery = CreateQueryBuilder.getQuery(Person.class);
        assertThat(createQuery).isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR, old INT, email VARCHAR NOT NULL);");
    }

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> CreateQueryBuilder.getQuery(PureDomain.class), "Create Query 빌드 대상이 아닙니다.");
    }

}