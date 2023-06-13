package persistence;

import java.util.DuplicateFormatFlagsException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        normalColumnBuild();
        primaryKeyBuild();

        return stringBuilder.toString();
    }

    public int size() {
        return columns.size();
    }

    private void primaryKeyBuild() {
        stringBuilder.append(new PrimaryKey(uniqueKey()).expression());
    }

    private String uniqueKey() {
        return columns.stream().filter(Column::unique).findFirst().orElseThrow(RuntimeException::new).name();
    }

    private void normalColumnBuild() {
        for (int i = 0; i < size(); i++) {
            stringBuilder.append(findColumn(i).expression());

            addDelimiter();
        }
    }

    private void addDelimiter() {
        stringBuilder.append(DELIMITER);
    }

    private void checkDuplicateUnique(List<Column> columns) {
        if (columns.stream().filter(Column::unique).count() > 1) {
            throw new DuplicateFormatFlagsException("Multiple columns with unique flag found.");
        }
    }

    private void checkDuplicateName(List<Column> columns) {
        Set<String> names = new HashSet<>();
        columns.stream().filter(column -> !names.add(column.name())).findFirst().ifPresent(column -> {
            throw new DuplicateFormatFlagsException("Duplicate column name: " + column.name());
        });
    }

    private Column findColumn(int index) {
        return columns.get(index);
    }
}
