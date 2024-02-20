package persistence.sql.mapping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Table {

    private final String name;

    private final Map<String, Column> columns;

    private PrimaryKey primaryKey;

    public Table(String name) {
        this.name = name;
        this.columns = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return this.columns.values().stream().map(Column::clone).collect(Collectors.toList());
    }

    public void setPrimaryKey(final PrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public PrimaryKey getPrimaryKey() {
        return this.primaryKey;
    }

    public boolean hasPrimaryKey() {
        return this.getPrimaryKey() != null;
    }

    public void addColumn(final Field field, final ColumnTypeMapper columnTypeMapper) {
        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }

        final Column column = new Column(field, columnTypeMapper);

        final Id idAnnotation = field.getAnnotation(Id.class);

        if (idAnnotation != null) {
            column.setPk(true);
            this.setPrimaryKey(new PrimaryKey());
            this.getPrimaryKey().addColumn(column);

            final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue != null) {
                column.setStrategy(generatedValue.strategy());
            }
        }

        this.columns.put(column.getName(), column);
    }
}
