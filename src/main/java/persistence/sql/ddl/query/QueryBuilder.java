package persistence.sql.ddl.query;

import persistence.sql.ddl.SchemaExtractor;
import persistence.sql.ddl.TableInfo;

public interface QueryBuilder {
    SchemaExtractor schemaExtractor = new SchemaExtractor();

    default TableInfo getSchemaInformation(Class<?> entityClazz) {
        return schemaExtractor.extract(entityClazz);
    }

    String build(Class<?> entityClazz);
}
