package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import persistence.domain.Person;

class EntityTableMetadataTest {

    @Test
    void getTableName() {
        EntityTableMetadata entityTableMetadata = new EntityTableMetadata(Person.class);
        entityTableMetadata.getTableName();
        assertThat(entityTableMetadata.getTableName()).isEqualTo("users");
    }

    @Test
    void getColumnDefinitions() {
        EntityTableMetadata entityTableMetadata = new EntityTableMetadata(Person.class);
        Map<ColumnName, ColumnType> columnDefinitions = entityTableMetadata.getColumnDefinitions();

        columnDefinitions.keySet().forEach(columnName -> {
            assertThat(columnName.getColumnName()).isIn("id", "nick_name", "old", "email");
        });
    }

}
