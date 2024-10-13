package persistence.sql.ddl.impl;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.node.FieldNode;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ColumnOptionSupplier 테스트")
class ColumnOptionSupplierTest {
    private final ColumnOptionSupplier supplier = new ColumnOptionSupplier((short) 1);

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
    @DisplayName("supply 함수는 Column, ID 애노테이션 둘 다 없을 경우 빈 문자열을 반환한다.")
    void testSupplyWithNoAnnotation() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[1]);

        String actual = supplier.supply(fieldNode);

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("supply 함수는 Column 애노테이션의 nullable이 false 이거나 필드가 PK일 경우 NOT NULL을 반환한다.")
    void testSupplyWithNotNull() {
        FieldNode fieldNode1 = new FieldNode(ExampleClass.class.getDeclaredFields()[0]);
        FieldNode fieldNode2 = new FieldNode(ExampleClass.class.getDeclaredFields()[2]);

        String actual1 = supplier.supply(fieldNode1);
        String actual2 = supplier.supply(fieldNode2);

        assertThat(actual1).isEqualTo("NOT NULL");
        assertThat(actual2).isEqualTo("NOT NULL");
    }

    @Test
    @DisplayName("supply 함수는 Column 애노테이션의 unique가 true일 경우 UNIQUE를 반환한다.")
    void testSupplyWithUnique() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[3]);

        String actual = supplier.supply(fieldNode);

        assertThat(actual).isEqualTo("UNIQUE");
    }

    @Test
    @DisplayName("supply 함수는 Column 애노테이션의 nullable이 false 이고 unique가 true일 경우 NOT NULL UNIQUE를 반환한다.")
    void testSupplyWithNotNullUnique() {
        FieldNode fieldNode = new FieldNode(ExampleClass.class.getDeclaredFields()[4]);

        String actual = supplier.supply(fieldNode);

        assertThat(actual).isEqualTo("NOT NULL UNIQUE");
    }


    @Entity
    private static class ExampleClass {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(nullable = false)
        private String email;

        @Column(unique = true)
        private String uniqueColumn;

        @Column(nullable = false, unique = true)
        private String notNullUniqueColumn;
    }
}