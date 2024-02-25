package persistence.sql.ddl.query.builder;

import persistence.sql.dialect.database.ConstraintsMapper;
import persistence.sql.dialect.database.TypeMapper;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainType;
import persistence.sql.entity.model.DomainTypes;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        List<ColumnBuilder> columnBuilders = getColumnBuilders(
                entityMappingTable.getDomainTypes(),
                typeMapper,
                constantTypeMapper);
        return new CreateQueryBuilder(entityMappingTable.getTableName(), columnBuilders);
    }

    private static List<ColumnBuilder> getColumnBuilders(final DomainTypes domainTypes,
                                                         final TypeMapper typeMapper,
                                                         final ConstraintsMapper constantTypeMapper) {
        Spliterator<DomainType> spliterator = domainTypes.spliterator();
        return StreamSupport.stream(spliterator, false)
                .filter(DomainType::isNotTransient)
                .map(domainType -> new ColumnBuilder(domainType, typeMapper, constantTypeMapper))
                .collect(Collectors.toList());
    }

    public String toSql() {
        String columns = columnBuilders.stream()
                .map(ColumnBuilder::build)
                .collect(Collectors.joining(DELIMITER));

        return String.format(CREATE_SQL, tableName, columns);
    }

}
