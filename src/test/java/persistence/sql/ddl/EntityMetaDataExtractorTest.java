package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.utils.Table;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMetaDataExtractorTest {
    EntityMetaDataExtractor entityMetaDataExtractor = new EntityMetaDataExtractor(Person.class);

    @Test
    void getTableName() {
        Table table = entityMetaDataExtractor.getTable();
        String name = table.getName();
        assertThat(name).isEqualTo("person");
    }
}