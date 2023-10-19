package persistence.sql.ddl.utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ColumnQueryBuilder {


    public static List<String> generateDdlQueryRows(List<Column> columns) {
        return columns.stream()
                .map(column -> column.getName() + " " + column.getTypeName() + generateLength(column.getLength()))
                .collect(Collectors.toList());
    }

    private static String generateLength(Integer length) {
        return Optional.ofNullable(length)
                .map(e -> "(" + e + ")")
                .orElse("");
    }
}