package persistence.sql.ddl.dialect.h2;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.query.EntityMappingTable;
import persistence.sql.ddl.query.builder.CreateQueryBuilder;
import persistence.sql.ddl.query.builder.DropQueryBuilder;

public class H2Dialect implements Dialect {
    private final TypeMapper typeMapper;

    public H2Dialect() {
        this.typeMapper = H2TypeMapper.newInstance();
    }

    @Override
    public String createTable(Class<?> clazz) {
        final EntityMappingTable entityMappingTable = EntityMappingTable.from(clazz);

        final CreateQueryBuilder queryBuilder = new CreateQueryBuilder(entityMappingTable);
        return queryBuilder.toSql(typeMapper);
    }

    @Override
    public String dropTable(Class<?> clazz) {
        final EntityMappingTable entityMappingTable = EntityMappingTable.from(clazz);

        final DropQueryBuilder queryBuilder = new DropQueryBuilder(entityMappingTable);
        return queryBuilder.toSql();
    }
}
