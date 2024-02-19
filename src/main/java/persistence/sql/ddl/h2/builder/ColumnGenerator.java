package persistence.sql.ddl.h2.builder;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.h2.meta.H2Column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ColumnGenerator {
    private final List<Field> fields;

    public ColumnGenerator(Field[] fields) {
        this.fields = Arrays.stream(fields).toList();
    }

    public String generateSQL() {
        return this.generateColumns() + this.generatePK();
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
        return sb.toString();
    }

    public String generatePK() {
        Field pkField = this.fields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("PK must exists"));
        return String.format("    primary key (%s)", pkField.getName());
    }
}
