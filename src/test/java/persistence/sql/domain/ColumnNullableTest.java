package persistence.sql.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnNullableTest {

    @Test
    void should_return_proper_nullable_instance() {
        assertAll(
                ()-> assertTrue(ColumnNullable.getInstance(TestPerson.class.getDeclaredField("name")).isNullable()),
                ()-> assertFalse(ColumnNullable.getInstance(TestPerson.class.getDeclaredField("id")).isNullable()),
                ()-> assertFalse(ColumnNullable.getInstance(TestPerson.class.getDeclaredField("address")).isNullable())
        );
    }

    private class TestPerson {
        @Id
        private Long id;
        @Column
        private String name;
        @Column(nullable = false)
        private String address;
    }

}