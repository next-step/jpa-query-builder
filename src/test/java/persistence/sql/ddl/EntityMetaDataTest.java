package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.utils.TableType;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMetaDataTest {
    EntityMetaData entityMetaData = new EntityMetaData(Person.class);

    @Test
    void getTableName() {
        TableType tableType = entityMetaData.getTable();
        String name = tableType.getName();
        assertThat(name).isEqualTo("users");
    }
}