package persistence.sql.dml.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class H2SelectQueryBuilderTest {

    private static final H2SelectQueryBuilder selectQueryBuilder = H2SelectQueryBuilder.getInstance();

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> selectQueryBuilder.getSelectAllQuery(PureDomain.class), "Select Query 빌드 대상이 아닙니다.");
    }

    @Test
    @DisplayName("쿼리 정상 빌드 테스트")
    void getQuery() {
        String query = selectQueryBuilder.getSelectAllQuery(Person.class);
        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users;");
    }
}
