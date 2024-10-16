package persistence.sql.ddl.impl;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.common.util.CamelToSnakeConverter;
import persistence.sql.node.FieldNode;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ColumnNameSupplier 테스트")
class ColumnNameSupplierTest {
    private final ColumnNameSupplier supplier = new ColumnNameSupplier((short) 1, CamelToSnakeConverter.getInstance());

    @Test
    @DisplayName("supported 함수는 필드가 null이면 false를 반환한다.")
    void testSupported() {
        assertThat(supplier.supported(null)).isFalse();
    }

    @Test
    @DisplayName("supported 함수는 필드가 null이 아니면 true를 반환한다.")
    void testSupportedWithField() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[0]);

        assertThat(supplier.supported(fieldNode)).isTrue();
    }

    @Test
    @DisplayName("supply 함수는 Column 어노테이션이 없거나 name이 비어있으면 필드명을 반환한다.")
    void testSupplyWithNoColumnAnnotation() {
        FieldNode fieldNode1 = new FieldNode(ExampleClass.class.getDeclaredFields()[1]);
        FieldNode fieldNode2 = new FieldNode(ExampleClass.class.getDeclaredFields()[2]);

        String actual1 = supplier.supply(fieldNode1);
        String actual2 = supplier.supply(fieldNode2);

        assertThat(actual1).isEqualTo("name");
        assertThat(actual2).isEqualTo("empty_column");
    }

    @Test
    @DisplayName("supply 함수는 Column 어노테이션의 name이 있으면 name을 반환한다.")
    void testSupplyWithColumnAnnotation() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[3]);

        String actual = supplier.supply(fieldNode);

        assertThat(actual).isEqualTo("test_age");
    }

    @Entity
    private static class ExampleClass {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column
        private String emptyColumn;

        @Column(name = "test_age")
        private int age;
    }
}