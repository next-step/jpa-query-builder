package persistence.sql.dml.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class H2DeleteQueryBuilderTest {

    private static final H2DeleteQueryBuilder deleteQueryBuilder = H2DeleteQueryBuilder.getInstance();

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> deleteQueryBuilder.getDeleteAllQuery(PureDomain.class), "Delete Query 빌드 대상이 아닙니다.");
    }

    @Test
    @DisplayName("테이블 데이터 전체 삭제")
    void deleteAll() {
        String deleteAllQuery = deleteQueryBuilder.getDeleteAllQuery(Person.class);
        assertThat(deleteAllQuery).isEqualTo("DELETE FROM users;");
    }

    @Test
    @DisplayName("PK 기반 삭제쿼리 정상빌드 테스트")
    void deleteByPk() {
        String deleteByPkQuery = deleteQueryBuilder.getDeleteByPkQuery(Person.class, 1L);
        assertThat(deleteByPkQuery).isEqualTo("DELETE FROM users WHERE id=1;");
    }

}
