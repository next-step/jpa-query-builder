package persistence.sql.ddl.query;

import persistence.sql.ddl.query.model.DomainType;
import persistence.sql.ddl.query.model.DomainTypes;

import java.util.List;

public class EntityMappingTable {

    private final String tableName;
    private final DomainTypes domainTypes;
    private final Class<?> clazz;

    public EntityMappingTable(final String tableName,
                              final DomainTypes domainTypes,
                              final Class<?> clazz) {
        this.tableName = tableName;
        this.domainTypes = domainTypes;
        this.clazz = clazz;
    }

    public String getTableName() {
        return tableName;
    }

    public List<DomainType> getDomainTypes() {
        return domainTypes.getDomainTypes();
    }

    public static EntityMappingTable from(final Class<?> clazz) {
        return new EntityMappingTable(
                clazz.getSimpleName(),
                DomainTypes.from(clazz.getDeclaredFields()),
                clazz
        );
    }
}
