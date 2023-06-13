package persistence.ddl;

import domain.Person;
import org.junit.jupiter.api.Test;
import persistence.EntityScanner;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteTableBuilderTest {

    @Test
    void drop_table() {
        EntityScanner entityScanner = new EntityScanner(Person.class);

        DeleteTableBuilder actual = new DeleteTableBuilder(entityScanner.table());

        assertThat(actual.query()).isEqualTo("drop table if exists Person cascade");
    }

}
