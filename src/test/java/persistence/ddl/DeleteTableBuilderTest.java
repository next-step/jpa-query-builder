package persistence.ddl;

import domain.Person;
import org.junit.jupiter.api.Test;
import persistence.EntityReflectionManager;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteTableBuilderTest {

    @Test
    void drop_table() {
        EntityReflectionManager entityScanner = new EntityReflectionManager(Person.class);

        DeleteTableBuilder actual = new DeleteTableBuilder(entityScanner.table());

        assertThat(actual.query()).isEqualTo("drop table if exists Person cascade");
    }

}
