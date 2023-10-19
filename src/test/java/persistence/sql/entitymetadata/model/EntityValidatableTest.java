package persistence.sql.entitymetadata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityValidatableTest {

    static class FakeNoEntityAnnotationEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    }

    @Entity
    static class FakeNoIdColumnEntity {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    }

    @DisplayName("@Entity 어노테이션이 없을 경우 유효성검증 예외 발생")
    @Test
    void validate_1() {
        assertThatThrownBy(() -> new EntityValidatable<>(FakeNoEntityAnnotationEntity.class) {
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Entity Class must be annotated with @Entity");
    }

    @DisplayName("@Id 어노테이션이 없을 경우 유효성검증")
    @Test
    void validate_2() {
        assertThatThrownBy(() -> new EntityValidatable<>(FakeNoIdColumnEntity.class) {
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Entity Class must not have @Id columns");
    }
}