package persistence;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Columns {
    private static final String DELIMITER = ",";
    private final List<ColumnNode> columns;

    public Columns(List<ColumnNode> columns) {
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

    public String primaryKey() {
        return columns.stream()
                .filter(ColumnNode::unique)
                .findFirst()
                .orElseThrow(RuntimeException::new).name();
    }

    public Optional<ColumnNode> getColumn(String name) {
        return columns.stream()
                .filter(it -> it.sameName(name))
                .findFirst();
    }

    private String idBuild() {
        return new Id(primaryKey())
                .expression();
    }

    private String normalColumnBuild() {
        return IntStream.range(0, size())
                .mapToObj(this::findColumn)
                .filter(column -> !column.unique())
                .map(ColumnNode::expression)
                .collect(Collectors.joining(","));
    }

    private String primaryKeyBuild() {
        return new PrimaryKey(primaryKey())
                .expression();
    }

    private ColumnNode findColumn(int index) {
        return columns.get(index);
    }


    private String addDelimiter() {
        return DELIMITER;
    }

    public int size() {
        return columns.size();
    }

    private void checkDuplicateUnique(List<ColumnNode> columns) {
        boolean isOver = columns.stream()
                .filter(ColumnNode::unique)
                .count() > 1;

        if (isOver) {
            throw new DuplicateFormatFlagsException("Multiple columns with unique flag found.");
        }
    }

    private void checkDuplicateName(List<ColumnNode> columns) {
        Set<String> names = new HashSet<>();

        columns.stream()
                .filter(column -> !names.add(column.name()))
                .findFirst()
                .ifPresent(column -> {
                    throw new DuplicateFormatFlagsException("Duplicate column name: " + column.name());
                });
    }
}
