package persistence.sql.meta;

import domain.Person;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.mock.PureDomain;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMetaTest {

    @Test
    @DisplayName("Entity 도메인 클래스")
    void entityDomain() {
        EntityMeta entityMeta = EntityMeta.of(Person.class);
        assertThat(entityMeta.isEntity()).isTrue();
    }

    @Test
    @DisplayName("POJO 클래스")
    void pojoDomain() {
        EntityMeta entityMeta = EntityMeta.of(PureDomain.class);
        assertThat(entityMeta.isEntity()).isFalse();
    }

    @Test
    @DisplayName("Table Name 조회테스트")
    void getTableName() {
        EntityMeta entityMeta = EntityMeta.of(Person.class);
        Table tableAnnotation = Person.class.getDeclaredAnnotation(Table.class);
        assertThat(entityMeta.getTableName()).isEqualTo(tableAnnotation.name());
    }
}
