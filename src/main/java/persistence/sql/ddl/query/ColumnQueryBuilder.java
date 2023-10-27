package persistence.sql.ddl.query;

import persistence.sql.ddl.constraint.Constraint;
import persistence.sql.ddl.constraint.IdentityConstraint;
import persistence.sql.ddl.constraint.NotNullConstraint;
import persistence.sql.ddl.constraint.PrimaryKeyConstraint;
import persistence.sql.ddl.utils.ColumnType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnQueryBuilder {

    private final List<Constraint> constraints;

    public ColumnQueryBuilder(List<Constraint> list) {
        this.constraints = list;
    }


    public List<String> generateDdlQueryRows(final List<? extends ColumnType> columnTypes) {
        return columnTypes.stream()
                .map(columnType -> String.format("%s %s %s",
                        columnType.getName(),
                        columnType.getDataType().getName() + columnType.getLength(),
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