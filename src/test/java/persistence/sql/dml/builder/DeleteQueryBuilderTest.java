package persistence.sql.dml.builder;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.MockEntity;
import persistence.mock.PureDomain;
import persistence.sql.meta.MetaFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteQueryBuilderTest {

    @Test
    @DisplayName("Entity 애노테이션 미존재")
    void doNotHaveEntityAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> DeleteQueryBuilder.of(MetaFactory.get(PureDomain.class)), "Delete Query 빌드 대상이 아닙니다.");
    }

    @Test
    @DisplayName("테이블 데이터 전체 삭제")
    void deleteAll() {
        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.of(MetaFactory.get(Person.class));
        assertThat(deleteQueryBuilder.buildDeleteAllQuery()).isEqualTo("DELETE FROM users;");
    }

    @Test
    @DisplayName("PK 기반 삭제쿼리 정상빌드 테스트")
    void deleteByPk() {
        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.of(MetaFactory.get(Person.class));
        assertThat(deleteQueryBuilder.buildDeleteByPkQuery(1L)).isEqualTo("DELETE FROM users WHERE id=1;");
    }

    @Test
    @DisplayName("엔티티 인스턴스를 활용한 PK 기반 삭제쿼리 정상빌드 테스트")
    void deleteEntityInstance() {
        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.of(MetaFactory.get(MockEntity.class));
        MockEntity 테스트 = new MockEntity(2L, "테스트");
        String deleteQuery = deleteQueryBuilder.buildDeleteQuery(테스트);
        assertThat(deleteQuery).isEqualTo("DELETE FROM mockentity WHERE id=2;");
    }

}
