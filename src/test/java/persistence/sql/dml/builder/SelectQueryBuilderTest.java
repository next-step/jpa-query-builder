package persistence.sql.dml.builder;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;
import persistence.sql.meta.MetaFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SelectQueryBuilderTest {

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () ->SelectQueryBuilder.of(MetaFactory.get(PureDomain.class)), "Select Query 빌드 대상이 아닙니다.");
    }

    @Test
    @DisplayName("쿼리 정상 빌드 테스트")
    void getQuery() {
        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.of(MetaFactory.get(Person.class));
        assertThat(selectQueryBuilder.buildSelectAllQuery()).isEqualTo("SELECT id, nick_name, old, email FROM users;");
    }

    @Test
    @DisplayName("PK 기반 조회쿼리 정상빌드 테스트")
    void getSelectByPkQuery() {
        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.of(MetaFactory.get(Person.class));
        assertThat(selectQueryBuilder.buildSelectByPkQuery(1L)).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id=1;");
    }
}
