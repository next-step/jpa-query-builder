package persistence.sql.ddl.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityMetaDataExtractor;
import persistence.sql.ddl.Person;

import java.util.List;

class ColumnQueryBuilderTest {

    @BeforeEach
    void setUp() {

    }
    @Test
    void query() {
        EntityMetaDataExtractor entityMetaDataExtractor = new EntityMetaDataExtractor(Person.class);
        List<String> strings = ColumnQueryBuilder.generateDdlQueryRows(entityMetaDataExtractor.getColumns());
        System.out.println(strings);
    }
}