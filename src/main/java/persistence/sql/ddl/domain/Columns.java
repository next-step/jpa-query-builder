package persistence.sql.ddl.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Columns {

    private static final String COMMA = ", ";

    private final List<Column> columns;

    public Columns(List<Column> columns) {
        this.columns = columns;
    }

    public String generateColumns() {
        return columns.stream()
                .filter(Column::isNotTransient)
                .map(Column::generateColumn)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(COMMA));
    }
}
