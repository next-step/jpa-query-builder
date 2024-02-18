package persistence.sql.ddl.validator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("EntityValidator class의")
public class EntityValidatorTest {

    private EntityValidator entityValidator;

    @BeforeEach
    public void setup() {
        entityValidator = EntityValidator.getInstance();
    }

    @DisplayName("validate 메서드는")
    @Nested
    class Validate {

        @DisplayName("@Entity 어노테이션이 존재하지 않을 경우 예외가 발생한다.")
        @Test
        public void testValidate_WhenClassIsNotEntity() {
            assertThrows(IllegalArgumentException.class, () -> entityValidator.validate(NonEntityClass.class));
        }

        @DisplayName("@Id 어노테이션이 두개 이상 존재 할 경우 예외가 발생한다.")
        @Test
        public void testValidate_WhenClassHasMultipleIdFields() {
            assertThrows(IllegalArgumentException.class, () -> entityValidator.validate(MultipleIdsClass.class));
        }

        @DisplayName("정상적인 entity구조는 예외가 발생하지 않는다.")
        @Test
        public void testValidate_WhenClassIsProperEntity() {
            entityValidator.validate(ProperEntityClass.class);
        }
    }

    // Test classes for testing
    private static class NonEntityClass {
        @Id
        String id;
    }

    @Entity
    private static class MultipleIdsClass {
        @Id
        String id1;
        @Id
        String id2;
    }

    @Entity
    private static class ProperEntityClass {
        @Id
        String id;
    }
}
