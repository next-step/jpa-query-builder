package persistence.sql.ddl.query.builder;

import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.query.EntityMappingTable;

import java.util.stream.Collectors;

public class CreateQueryBuilder {

    private static final String CREATE_SQL = "CREATE TABLE %s(\n%s\n);";
    private static final String DELIMITER = ",\n";

    private final EntityMappingTable entityMappingTable;

    public CreateQueryBuilder(final EntityMappingTable entityMappingTable) {
        this.entityMappingTable = entityMappingTable;
    }

    public String toSql(final TypeMapper typeMapper) {
        String columns = entityMappingTable.getDomainTypes()
                .stream()
                .map(domainType -> new ColumnBuilder(domainType, typeMapper).build())
                .collect(Collectors.joining(DELIMITER));

        return String.format(CREATE_SQL, entityMappingTable.getTableName(), columns);
    }


}
