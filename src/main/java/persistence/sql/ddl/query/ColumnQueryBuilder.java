package persistence.sql.ddl.query;

import persistence.sql.ddl.constraint.Constraint;
import persistence.sql.mapper.ColumnType;

import java.util.List;
import java.util.stream.Collectors;

public class ColumnQueryBuilder {

    private final List<Constraint> constraints;

    public ColumnQueryBuilder(List<Constraint> constraints) {
        this.constraints = constraints;
    }


    public List<String> generateDdlQueryRows(final List<? extends ColumnType> columnTypes) {
        return columnTypes.stream()
                .map(columnType -> String.format("%s %s %s",
                                columnType.getName(),
                                columnType.getDataType() + columnType.getLength(),
                                generateConstraint(columnType))
                        .trim())
                .collect(Collectors.toList());
    }

    private String generateConstraint(ColumnType columnType) {
        return this.constraints.stream()
                .filter(constraint -> constraint.check(columnType))
                .map(Constraint::generate)
                .collect(Collectors.joining(" "));
    }
}