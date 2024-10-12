package persistence.sql.ddl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Columns {
    private final List<Column> columns;

    private Columns(List<Column> columns) {
        validate(columns);
        this.columns = columns;
    }

    public static Columns from(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Column::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Columns::new));
    }

    private void validate(List<Column> columns) {
        boolean hasIdAnnotation = columns.stream()
                .anyMatch(Column::hasPrimaryKey);

        if (!hasIdAnnotation) {
            throw new IllegalArgumentException("@Id가 필수로 지정되어야 합니다");
        }
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Column getPrimaryKeyColumn() {
        return columns.stream()
                .filter(Column::hasPrimaryKey)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Primary key가 없습니다"));
    }
}
