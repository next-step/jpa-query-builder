package persistence.sql.dml.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class H2InsertQueryBuilderTest {

    private static final H2InsertQueryBuilder insertQueryBuilder = H2InsertQueryBuilder.getInstance();

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> insertQueryBuilder.getQuery(PureDomain.class), "Insert Query 빌드 대상이 아닙니다.");
    }

    @Test
    @DisplayName("쿼리 정상 빌드 테스트")
    void getQuery() {
        Person 홍길동 = new Person("홍길동", 20, "aaa@bbb.com", 1);
        String query = insertQueryBuilder.getQuery(홍길동);
        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('홍길동', 20, 'aaa@bbb.com');");
    }

}
