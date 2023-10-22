package persistence.sql.meta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnMetas {

    private final List<ColumnMeta> values;

    private ColumnMetas(List<ColumnMeta> values) {
        this.values = values;
    }

    public static ColumnMetas of(Field[] fields) {
        return new ColumnMetas(Arrays.stream(fields)
                .map(ColumnMeta::of)
                .collect(Collectors.toList()));
    }

    public List<String> sqlColumnNames() {
        return values.stream()
                .filter(columnMeta -> !columnMeta.isTransient())
                .map(ColumnMeta::getColumnName)
                .collect(Collectors.toList());
    }

}
