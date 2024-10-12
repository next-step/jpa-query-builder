package persistence.sql.ddl.query;

import persistence.sql.ddl.TableInfo;

public class DropQueryBuilder implements QueryBuilder {

    @Override
    public String build(Class<?> entityClazz) {
        TableInfo tableInfo = getSchemaInformation(entityClazz);
        return "DROP TABLE " + tableInfo.tableName() + " if exists;";
    }
}
