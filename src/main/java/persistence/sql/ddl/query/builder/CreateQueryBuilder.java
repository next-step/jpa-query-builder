package persistence.sql.ddl.query.builder;

import persistence.sql.ddl.dialect.database.ConstraintsMapper;
import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.query.EntityMappingTable;
import persistence.sql.ddl.query.model.DomainType;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {

    private static final String CREATE_SQL = "CREATE TABLE %s(\n%s\n);";
    private static final String DELIMITER = ",\n";

    private final String tableName;
    private final List<ColumnBuilder> columnBuilders;

    private CreateQueryBuilder(final String tableName,
                               final List<ColumnBuilder> columnBuilders) {
        this.tableName = tableName;
        this.columnBuilders = columnBuilders;
    }

    public static CreateQueryBuilder of(final EntityMappingTable entityMappingTable,
                                        final TypeMapper typeMapper,
                                        final ConstraintsMapper constantTypeMapper) {
        List<ColumnBuilder> columnBuilders = entityMappingTable.getDomainTypes()
                .stream()
                .filter(DomainType::isNotTransient)
                .map(domainType -> new ColumnBuilder(domainType, typeMapper, constantTypeMapper))
                .collect(Collectors.toList());

        return new CreateQueryBuilder(entityMappingTable.getTableName(), columnBuilders);
    }

    public String toSql() {
        String columns = columnBuilders.stream()
                .map(ColumnBuilder::build)
                .collect(Collectors.joining(DELIMITER));

        return String.format(CREATE_SQL, tableName, columns);
    }


}
