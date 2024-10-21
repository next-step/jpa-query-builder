package persistence.sql.ddl;

import persistence.sql.ddl.metadata.Column;
import persistence.sql.ddl.metadata.ColumnMetadata;
import persistence.sql.ddl.metadata.ColumnOption;
import persistence.sql.ddl.metadata.EntityMetadata;

import java.util.stream.Collectors;

public class QueryBuilder {

    private static final String JOIN_DELIMITER = ", ";

    public String buildCreateDdl(Class<?> clazz) {
        EntityMetadata entityMetadata = EntityMetadata.from(clazz);
        return "create table " +
                entityMetadata.getTableName() +
                " (" +
                getDefinitions(entityMetadata) +
                ");";
    }

    public String buildDropDdl(Class<?> clazz) {
        EntityMetadata entityMetadata = EntityMetadata.from(clazz);
        return "drop table " + entityMetadata.getTableName() + ";";
    }

    private String getDefinitions(EntityMetadata entityMetadata) {
        return generateColumnDefinitions(entityMetadata.getColumnMetadata()) + ", primary key (" + generatePrimaryKeyNames(entityMetadata) + ")";
    }

    private String generatePrimaryKeyNames(EntityMetadata entityMetadata) {
        return String.join(JOIN_DELIMITER, entityMetadata.getPrimaryKeyNames());
    }

    private String generateColumnDefinitions(ColumnMetadata columnMetadata) {
        return columnMetadata.getColumns().stream()
                .map(this::generateColumnDefinition)
                .collect(Collectors.joining(JOIN_DELIMITER));
    }

    private String generateColumnDefinition(Column column) {
        String options = column.options().stream()
                .map(ColumnOption::getOption)
                .collect(Collectors.joining(" "));

        if (!options.isEmpty()) {
            return column.getName() + " " + column.getSqlType() + " " + options;
        }

        return column.getName() + " " + column.getSqlType();
    }
}
