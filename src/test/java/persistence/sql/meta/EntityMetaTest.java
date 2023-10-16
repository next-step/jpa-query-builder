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
        assertThat(EntityMeta.isEntity(Person.class)).isTrue();
    }

    @Test
    @DisplayName("POJO 클래스")
    void pojoDomain() {
        assertThat(EntityMeta.isEntity(PureDomain.class)).isFalse();
    }

    @Test
    @DisplayName("Table Name 조회테스트")
    void getTableName() {
        Table tableAnnotation = Person.class.getDeclaredAnnotation(Table.class);
        assertThat(EntityMeta.getTableName(Person.class)).isEqualTo(tableAnnotation.name());
    }
}