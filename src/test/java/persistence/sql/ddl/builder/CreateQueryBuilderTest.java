package persistence.sql.ddl.builder;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;
import persistence.sql.dialect.h2.H2Dialect;
import persistence.sql.meta.EntityMeta;
import persistence.sql.meta.MetaFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateQueryBuilderTest {

    @Test
    @DisplayName("Entity, PK 애노테이션 검증 정상 통과")
    void existsAnnotation() {
        EntityMeta entityMeta = MetaFactory.get(Person.class);
        CreateQueryBuilder h2CreateQueryBuilder = CreateQueryBuilder.of(H2Dialect.getInstance(), entityMeta);
        assertDoesNotThrow(h2CreateQueryBuilder::build);
    }

    @Test
    @DisplayName("쿼리 정상 빌드 테스트")
    void getQuery() {
        EntityMeta entityMeta = MetaFactory.get(Person.class);
        CreateQueryBuilder h2CreateQueryBuilder = CreateQueryBuilder.of(H2Dialect.getInstance(), entityMeta);
        assertThat(h2CreateQueryBuilder.build()).isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR, old INT, email VARCHAR NOT NULL);");
    }

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        EntityMeta entityMeta = MetaFactory.get(PureDomain.class);
        assertThrows(IllegalArgumentException.class, () -> CreateQueryBuilder.of(H2Dialect.getInstance(), entityMeta), "Create Query 빌드 대상이 아닙니다.");
    }
}
