package persistence.sql.ddl.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.node.FieldNode;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ConstraintPrimaryKeySupplier 테스트")
class ConstraintPrimaryKeySupplierTest {
    private final ConstraintPrimaryKeySupplier supplier = new ConstraintPrimaryKeySupplier((short) 1, CamelToSnakeConverter.getInstance());

    @Test
    @DisplayName("supported 함수는 필드에 ID 어노테이션이 있으면 true를 반환한다.")
    void testSupportedWithIdAnnotation() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[0]);

        assertThat(supplier.supported(fieldNode)).isTrue();
    }

    @Test
    @DisplayName("supported 함수는 필드에 ID 어노테이션이 없으면 false를 반환한다.")
    void testSupportedWithoutIdAnnotation() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[1]);

        assertThat(supplier.supported(fieldNode)).isFalse();
    }

    @Test
    @DisplayName("supply 함수는 Id 애노테이션이 붙은 필드명을 기반으로 PRIMARY KEY 쿼리를 반환한다.")
    void testSupplyWithIdentity() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[0]);

        String actual = supplier.supply(fieldNode);

        assertThat(actual).isEqualTo("PRIMARY KEY (exam_id)");
    }

    @Entity
    private static class ExampleClass {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long examId;

        private String name;
    }
}