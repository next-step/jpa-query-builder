package persistence;


import java.util.*;

public class Columns {
    private static final String DELIMITER = ",";
    private final List<Column> columns;
    private final StringBuilder stringBuilder = new StringBuilder();

    public Columns(List<Column> columns) {
        checkDuplicateName(columns);
        checkDuplicateUnique(columns);

        this.columns = columns;
    }

    public String expression() {
        idBuild();
        normalColumnBuild();
        primaryKeyBuild();

        return stringBuilder.toString();
    }

    private void idBuild() {
        stringBuilder.append(new Id(primaryKey())
                .expression());

        addDelimiter();
    }

    private void normalColumnBuild() {
        for (int i = 0; i < size(); i++) {
            appendRow(i);
        }
    }

    private void primaryKeyBuild() {
        stringBuilder.append(new PrimaryKey(primaryKey())
                .expression());
    }


    private String primaryKey() {
        return columns.stream()
                .filter(Column::unique)
                .findFirst()
                .orElseThrow(RuntimeException::new).name();
    }

    private void appendRow(int index) {
        Column column = findColumn(index);
        if (column.unique()) {
            return;
        }

        stringBuilder.append(findColumn(index).expression());

        addDelimiter();
    }

    private Column findColumn(int index) {
        return columns.get(index);
    }

    public Optional<Column> getColumn(String name) {
        return columns.stream()
                .filter(it -> it.sameName(name))
                .findFirst();
    }

    private void addDelimiter() {
        stringBuilder.append(DELIMITER);
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
