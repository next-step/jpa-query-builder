package persistence.sql.ddl.builder;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;
import persistence.sql.meta.EntityMeta;
import persistence.sql.meta.MetaFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DropQueryBuilderTest {

    @Test
    @DisplayName("Entity 애노테이션 검증 통과")
    void existsAnnotation() {
        EntityMeta entityMeta = MetaFactory.get(Person.class);
        DropQueryBuilder dropQueryBuilder = DropQueryBuilder.of(entityMeta);
        assertDoesNotThrow(dropQueryBuilder::build);
    }

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        EntityMeta entityMeta = MetaFactory.get(PureDomain.class);
        assertThrows(IllegalArgumentException.class, () -> DropQueryBuilder.of(entityMeta), "Drop Query 빌드 대상이 아닙니다.");
    }

    @Test
    @DisplayName("쿼리 정상빌드 테스트")
    void getQuery() {
        EntityMeta entityMeta = MetaFactory.get(Person.class);
        DropQueryBuilder dropQueryBuilder = DropQueryBuilder.of(entityMeta);
        assertThat(dropQueryBuilder.build()).isEqualTo("DROP TABLE users;");
    }

}
