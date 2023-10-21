package persistence.sql.dml.statement;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import persistence.sql.dialect.ColumnType;
import persistence.sql.schema.ColumnMeta;
import persistence.sql.schema.EntityClassMappingMeta;
import persistence.sql.schema.EntityObjectMappingMeta;

public class InsertStatementBuilder {

    private final ColumnType columnType;

    private static final String INSERT = "INSERT INTO";
    private static final String INSERT_FORMAT = "%s %s (%s) values (%s)";
    private static final String DELIMITER = ", ";

    public InsertStatementBuilder(ColumnType columnType) {
        this.columnType = columnType;
    }

    public String insert(Object object) {
        final EntityClassMappingMeta entityClassMappingMeta = EntityClassMappingMeta.of(object.getClass(), columnType);
        final EntityObjectMappingMeta entityObjectMappingMeta = EntityObjectMappingMeta.of(object, entityClassMappingMeta);

        return String.format(INSERT_FORMAT, INSERT, entityObjectMappingMeta.getTableName(), columnClause(entityObjectMappingMeta),
            valueClause(
                entityObjectMappingMeta));
    }

    private String columnClause(EntityObjectMappingMeta entityObjectMappingMeta) {
        return entityObjectMappingMeta.getColumnMetaSet().stream()
            .filter(isNotPrimaryKeyPredicate())
            .map(ColumnMeta::getColumnName)
            .collect(Collectors.joining(DELIMITER));
    }

    private String valueClause(EntityObjectMappingMeta entityObjectMappingMeta) {
        return entityObjectMappingMeta.getColumnMetaSet().stream()
            .filter(isNotPrimaryKeyPredicate())
            .map(entityObjectMappingMeta::getValueAsString)
            .collect(Collectors.joining(DELIMITER));
    }

    private static Predicate<ColumnMeta> isNotPrimaryKeyPredicate() {
        return columnMeta -> !columnMeta.isPrimaryKey();
    }
}
