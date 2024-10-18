package persistence.sql.ddl;

import java.util.List;
import java.util.stream.Collectors;

class ColumnDefinitions {
    private final List<ColumnDefinition> columnDefinitions;

    ColumnDefinitions(final List<ColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    String generate() {
        return columnDefinitions.stream()
                .map(this::getColumnDefinition)
                .collect(Collectors.joining(",\n"));
    }

    private String getColumnDefinition(final ColumnDefinition definition) {
        return QueryTemplate.COLUMN_DEFINITION.format(
                definition.name(),
                definition.type(),
                definition.identity(),
                definition.nullable(),
                definition.primaryKey());
    }
}
