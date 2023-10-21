package persistence.sql.ddl.generator;


import persistence.sql.dialect.ColumnType;
import persistence.sql.schema.EntityClassMappingMeta;

public class CreateDDLQueryGenerator {

    private final ColumnType columnType;

    public static final String CREATE_TABLE_FORMAT = "CREATE TABLE %s (%s);";

    public CreateDDLQueryGenerator(ColumnType columnType) {
        this.columnType = columnType;
    }

    public String create(Class<?> entityClazz) {
        final EntityClassMappingMeta entityClassMappingMeta = EntityClassMappingMeta.of(entityClazz, columnType);

        return String.format(CREATE_TABLE_FORMAT, entityClassMappingMeta.tableClause(), entityClassMappingMeta.fieldClause());
    }
}
