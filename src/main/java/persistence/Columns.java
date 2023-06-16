package persistence;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Columns {
    private static final String DELIMITER = ",";
    private final List<Column> columns;

    public Columns(List<Column> columns) {
        checkDuplicateName(columns);
        checkDuplicateUnique(columns);

        this.columns = columns;
    }

    public String expression() {
        return idBuild() +
                addDelimiter() +
                normalColumnBuild() +
                addDelimiter() +
                primaryKeyBuild();
    }

    private String idBuild() {
        return new Id(primaryKey())
                .expression();
    }

    private String normalColumnBuild() {
        return IntStream.range(0, size())
                .mapToObj(this::findColumn)
                .filter(column -> !column.unique())
                .map(Column::expression)
                .collect(Collectors.joining(","));
    }

    private String primaryKeyBuild() {
        return new PrimaryKey(primaryKey())
                .expression();
    }


    private String primaryKey() {
        return columns.stream()
                .filter(Column::unique)
                .findFirst()
                .orElseThrow(RuntimeException::new).name();
    }

    private Column findColumn(int index) {
        return columns.get(index);
    }

    public Optional<Column> getColumn(String name) {
        return columns.stream()
                .filter(it -> it.sameName(name))
                .findFirst();
    }

    private String addDelimiter() {
        return DELIMITER;
    }

    public int size() {
        return columns.size();
    }

    private void checkDuplicateUnique(List<Column> columns) {
        boolean isOver = columns.stream()
                .filter(Column::unique)
                .count() > 1;

        if (isOver) {
            throw new DuplicateFormatFlagsException("Multiple columns with unique flag found.");
        }
    }

    private void checkDuplicateName(List<Column> columns) {
        Set<String> names = new HashSet<>();

        columns.stream()
                .filter(column -> !names.add(column.name()))
                .findFirst()
                .ifPresent(column -> {
                    throw new DuplicateFormatFlagsException("Duplicate column name: " + column.name());
                });
    }
}
