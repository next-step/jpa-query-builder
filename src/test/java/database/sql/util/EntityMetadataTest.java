package database.sql.util;

import database.sql.dml.Person4;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMetadataTest {
    private final EntityMetadata metadata = new EntityMetadata(Person4.class);

    @Test
    void getTableName() {
        String tableName = metadata.getTableName();
        assertThat(tableName).isEqualTo("users");
    }

    @Test
    void getColumnNames() {
        List<String> columnNames = metadata.getColumnNames();
        assertThat(columnNames).containsExactly("id", "nick_name", "old", "email");
    }
}
