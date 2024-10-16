package persistence.sql.ddl.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.node.FieldNode;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ColumnGeneratedValueSupplier 테스트")
class ColumnGeneratedValueSupplierTest {
    private final ColumnGeneratedValueSupplier supplier = new ColumnGeneratedValueSupplier((short) 1);

    @Test
    @DisplayName("supported 함수는 필드에 GeneratedValue 어노테이션이 있으면 true를 반환한다.")
    void testSupportedWithGeneratedValueAnnotation() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[0]);

        assertThat(supplier.supported(fieldNode)).isTrue();
    }

    @Test
    @DisplayName("supported 함수는 필드에 GeneratedValue 어노테이션이 없으면 false를 반환한다.")
    void testSupportedWithoutGeneratedValueAnnotation() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[1]);

        assertThat(supplier.supported(fieldNode)).isFalse();
    }

    @Test
    @DisplayName("supply 함수는 GeneratedValue 어노테이션의 strategy가 IDENTITY이거나 AUTO면 AUTO_INCREMENT를 반환한다.")
    void testSupplyWithIdentityOrAuto() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[0]);

        String actual = supplier.supply(fieldNode);

        assertThat(actual).isEqualTo("AUTO_INCREMENT");
    }

    @Test
    @DisplayName("supply 함수는 GeneratedValue 어노테이션의 strategy가 IDENTITY나 AUTO가 아니면 빈 문자열을 반환한다.")
    void testSupplyWithOtherStrategy() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[1]);

        String actual = supplier.supply(fieldNode);

        assertThat(actual).isEmpty();
    }



    @Entity
    private static class ExampleClass {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
    }
}