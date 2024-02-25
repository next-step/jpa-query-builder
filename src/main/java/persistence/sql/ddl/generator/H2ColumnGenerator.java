package persistence.sql.ddl.generator;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.meta.column.Column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class H2ColumnGenerator implements ColumnGenerator {
    private final Dialect dialect;
    private final List<Field> fields;

    public H2ColumnGenerator(Dialect dialect, Field[] fields) {
        this.dialect = dialect;
        this.fields = Arrays.stream(fields).collect(Collectors.toList());
    }

    @Override
    public String generateColumns() {
        StringBuilder sb = new StringBuilder();
        this.fields.stream()
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .forEach(field -> {
                    sb.append("    ");
                    sb.append(this.generateColumn(field));
                    sb.append(",\n");
                });
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String generateColumn(Field field) {
        StringBuilder sb = new StringBuilder();
        Column column = new Column(field);

        sb.append(column.getColumnName());
        sb.append(" ");
        sb.append(dialect.getColumnDataType(column.getColumnType()));
        sb.append(" ");
        sb.append(dialect.getColumnNullableType(column.getNullable()));
        if (isPK(field)) {
            sb.append(" ");
            sb.append(dialect.getPKGenerationType(field));
            sb.append(" ");
            sb.append("PRIMARY KEY");
        }
        return sb.toString().trim();
    }

    private boolean isPK(Field field) {
        return field.isAnnotationPresent(Id.class);
    }
}
