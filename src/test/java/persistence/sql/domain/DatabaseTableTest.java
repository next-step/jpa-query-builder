package persistence.sql.domain;

import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseTableTest {

    @Test
    void should_return_table_name_from_entity() {
        Person person = new Person("cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);

        assertThat(table.getName()).isEqualTo("users");
    }


    @Test
    void should_return_all_columns() {
        Person person = new Person("cs", 29, "katd216@gmail.com", 1);
        DatabaseTable table = new DatabaseTable(person);

        List<ColumnOperation> columns = table.getAllColumns();
        List<String> allFieldName = columns.stream().map(ColumnOperation::getJavaFieldName).collect(Collectors.toList());

        assertThat(allFieldName).containsExactlyInAnyOrder("id", "name", "age", "email");
    }
}
