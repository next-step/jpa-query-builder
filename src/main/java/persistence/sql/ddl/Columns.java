package persistence.sql.ddl;

import java.util.List;
import java.util.stream.Collectors;

public class Columns {
    private final List<Column> columns;

    public Columns(List<Column> columns) {
        this.columns = columns;
    }

    public String buildColumnList() {
        return columns.stream()
                .filter(x -> !x.isTransient())
                .map(this::buildColumnToCreate)
                .collect(Collectors.joining(", "));
    }

    private String buildColumnToCreate(Column column) {
        return new StringBuilder()
                .append(column.getName() + " " + column.getType())
                .append(column.getConstraint().buildNullable())
                .append(column.getConstraint().bulidGeneratedType())
                .append(column.getConstraint().buildPrimaryKey())
                .toString();
    }
}
