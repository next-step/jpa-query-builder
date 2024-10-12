package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Column(
        ColumnName name,
        ColumnType columnType,
        List<ColumnOption> options,
        boolean primaryKey
) {

    public static Column from(Field field) {
        return new Column(
                ColumnName.from(field),
                ColumnType.of(field.getType()),
                extractOptions(field),
                field.isAnnotationPresent(Id.class)
        );
    }

    private static List<ColumnOption> extractOptions(Field field) {
        List<ColumnOption> options = new ArrayList<>();
        if (hasNotNullOption(field)) {
            options.add(ColumnOption.NOT_NULL);
        }

        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GenerationType generationType = field.getDeclaredAnnotation(GeneratedValue.class).strategy();
            if (generationType == GenerationType.IDENTITY) {
                options.add(ColumnOption.AUTO_INCREMENT);
            }
        }

        return options;
    }

    private static boolean hasNotNullOption(Field field) {
        return field.isAnnotationPresent(Id.class) || notNull(field);
    }

    private static boolean notNull(Field field) {
        return field.isAnnotationPresent(jakarta.persistence.Column.class) && !field.getDeclaredAnnotation(jakarta.persistence.Column.class).nullable();
    }

    public List<ColumnOption> getOptions() {
        return options;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public String getSqlType() {
        return columnType.getSqlType();
    }

    public String getName() {
        return name.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return primaryKey == column.primaryKey && Objects.equals(name, column.name) && columnType == column.columnType && Objects.equals(options, column.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columnType, options, primaryKey);
    }
}
