package persistence;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityScannerTest {

    @Test
    void table_추출() {
        EntityScanner entityScanner = new EntityScanner(Person.class);

        Table actual = entityScanner.table();

        assertThat(actual.expression()).isEqualTo("Person");
    }

    @Test
    void column_추출() {
        EntityScanner entityScanner = new EntityScanner(Person.class);

        Columns actual = entityScanner.columns();

        assertThat(actual.size()).isEqualTo(3);
    }
}
