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

    private String getDefinitions(EntityMetadata entityMetadata) {
        return generateColumnDefinitions(entityMetadata.getColumnMetadata()) + ", primary key (" + getPrimaryKeyNames(entityMetadata) + ")";
    }

    private String getPrimaryKeyNames(EntityMetadata entityMetadata) {
        return entityMetadata.getPrimaryKeys().stream()
                .map(Column::getName)
                .collect(Collectors.joining(JOIN_DELIMITER));
    }

    private String generateColumnDefinitions(ColumnMetadata columnMetadata) {
        return columnMetadata.getColumns().stream()
                .map(this::generateColumnDefinition)
                .collect(Collectors.joining(JOIN_DELIMITER));
    }

    private String generateColumnDefinition(Column column) {
        StringBuilder columnDefinition = new StringBuilder();
        columnDefinition.append(column.getName())
                .append(" ")
                .append(column.getSqlType());

        String options = column.options().stream()
                .map(ColumnOption::getOption)
                .collect(Collectors.joining(" "));

        if (!options.isEmpty()) {
            columnDefinition.append(" ").append(options);
        }

        return columnDefinition.toString();
    }
}
