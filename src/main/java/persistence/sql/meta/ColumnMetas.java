package persistence.sql.meta;

import persistence.sql.util.StringConstant;

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

    public ColumnMetas idColumns() {
        List<ColumnMeta> exceptTransient = values.stream()
                .filter(ColumnMeta::isId)
                .collect(Collectors.toList());
        return new ColumnMetas(exceptTransient);
    }

    public String getColumnsClause() {
        List<String> columnNames = values.stream()
                .map(ColumnMeta::getColumnName)
                .collect(Collectors.toList());
        return String.join(StringConstant.COLUMN_JOIN, columnNames);
    }

    @Override
    public Iterator<ColumnMeta> iterator() {
        return values.iterator();
    }

}
