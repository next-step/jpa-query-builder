package persistence.sql.common;

import java.util.List;
import java.util.stream.Collectors;

import static persistence.sql.common.StringConstant.DELIMITER;

public class ColumnNames {
    private final List<ColumnName> columnNames;

    private ColumnNames(List<ColumnName> columnNames) {
        this.columnNames = columnNames;
    }

    public static ColumnNames from(ColumnFields columnFields) {
        return new ColumnNames(
                columnFields.stream()
                        .map(ColumnName::new)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(" (")
                .append(joining())
                .append(")")
                .toString();
    }

    private String joining() {
        return columnNames.stream()
                .map(ColumnName::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
