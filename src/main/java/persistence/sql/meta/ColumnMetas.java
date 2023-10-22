package persistence.sql.meta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnMetas implements Iterable<ColumnMeta> {

    private final List<ColumnMeta> values;

    private ColumnMetas(List<ColumnMeta> values) {
        this.values = values;
    }

    public static ColumnMetas of(Field[] fields) {
        return new ColumnMetas(Arrays.stream(fields)
                .map(ColumnMeta::of)
                .collect(Collectors.toList()));
    }

    public ColumnMetas exceptTransient() {
        List<ColumnMeta> exceptTransient = values.stream()
                .filter(columnMeta -> !columnMeta.isTransient())
                .collect(Collectors.toList());
        return new ColumnMetas(exceptTransient);
    }

    @Override
    public Iterator<ColumnMeta> iterator() {
        return values.iterator();
    }

}
