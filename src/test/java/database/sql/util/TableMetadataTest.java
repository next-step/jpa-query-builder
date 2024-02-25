package database.sql.util;

import database.sql.dml.Person4;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableMetadataTest {
    private final TableMetadata tableMetadata = new TableMetadata(Person4.class);

    @Test
    void getTableName() {
        String tableName = tableMetadata.getTableName();
        assertThat(tableName).isEqualTo("users");
    }
}
