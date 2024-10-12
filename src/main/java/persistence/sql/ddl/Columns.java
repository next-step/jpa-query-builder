package persistence.sql.ddl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Columns {
    private final List<ColumnMetadata> columns;

    private Columns(List<ColumnMetadata> columns) {
        validate(columns);
        this.columns = columns;
    }

    public static Columns from(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(ColumnMetadata::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Columns::new));
    }

    private void validate(List<ColumnMetadata> columns) {
        boolean hasIdAnnotation = columns.stream()
                .anyMatch(ColumnMetadata::isPrimaryKey);

        if (!hasIdAnnotation) {
            throw new IllegalArgumentException("@Id가 필수로 지정되어야 합니다");
        }
    }

    public List<ColumnMetadata> getColumns() {
        return columns;
    }

    public ColumnMetadata getPrimaryKeyColumn() {
        return columns.stream()
                .filter(ColumnMetadata::isPrimaryKey)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Primary key가 없습니다"));
    }
}
