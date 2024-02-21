package persistence.sql.ddl.h2;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;
import persistence.sql.ddl.h2.meta.H2Column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnGenerator {
    private final Dialect dialect;
    private final List<Field> fields;

    public ColumnGenerator(Dialect dialect, Field[] fields) {
        this.dialect = dialect;
        this.fields = Arrays.stream(fields).collect(Collectors.toList());
    }

    public String generateSQL() {
        return this.generateColumns();
    }

    private String generateColumns() {
        StringBuilder sb = new StringBuilder();
        this.fields.stream()
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .forEach(field -> {
                    sb.append("    ");
                    sb.append(new H2Column(field).generateColumnSQL());
                    sb.append(",\n");
                });
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
