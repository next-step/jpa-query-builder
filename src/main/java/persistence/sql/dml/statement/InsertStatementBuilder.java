package persistence.sql.dml.statement;

import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.dialect.ColumnType;
import persistence.sql.schema.ColumnMeta;
import persistence.sql.schema.EntityClassMappingMeta;
import persistence.sql.schema.EntityObjectMappingMeta;

public class InsertStatementBuilder {

    private static final String INSERT_FORMAT = "INSERT INTO %s (%s) values (%s)";

    private final ColumnType columnType;

    public InsertStatementBuilder(ColumnType columnType) {
        this.columnType = columnType;
    }

    public String insert(Object object) {
        final EntityClassMappingMeta entityClassMappingMeta = EntityClassMappingMeta.of(object.getClass(), columnType);
        final EntityObjectMappingMeta entityObjectMappingMeta = EntityObjectMappingMeta.of(object, entityClassMappingMeta);

        return String.format(INSERT_FORMAT,
            entityClassMappingMeta.tableClause(),
            columnClause(entityObjectMappingMeta),
            valueClause(entityObjectMappingMeta)
        );
    }

    private String columnClause(EntityObjectMappingMeta entityObjectMappingMeta) {
        final List<String> columnNameList = entityObjectMappingMeta.getColumnMetaList().stream()
            .filter(columnMeta -> !columnMeta.isPrimaryKey())
            .map(ColumnMeta::getColumnName)
            .collect(Collectors.toList());

        return String.join(", ", columnNameList);
    }

    private String valueClause(EntityObjectMappingMeta entityObjectMappingMeta) {
        final List<String> formattedValueList = entityObjectMappingMeta.getMetaEntryList().stream()
            .filter(entry -> !entry.getKey().isPrimaryKey())
            .map(entry -> entry.getValue().getFormattedValue())
            .collect(Collectors.toList());

        return String.join(", ", formattedValueList);
    }
}
