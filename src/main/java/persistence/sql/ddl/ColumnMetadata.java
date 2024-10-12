package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ColumnMetadata {

    private final ColumnName name;
    private final ColumnType columnType;
    private final List<ColumnOption> options;
    private final boolean primaryKey;

    private ColumnMetadata(ColumnName name, ColumnType columnType, List<ColumnOption> options, boolean primaryKey) {
        this.name = name;
        this.columnType = columnType;
        this.options = options;
        this.primaryKey = primaryKey;
    }

    public static ColumnMetadata from(Field field) {
        return new ColumnMetadata(
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
        return field.isAnnotationPresent(Column.class) && !field.getDeclaredAnnotation(Column.class).nullable();
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
        return name.getName();
    }

    public boolean isAutoIncrement() {
        return options.contains(ColumnOption.AUTO_INCREMENT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnMetadata column = (ColumnMetadata) o;
        return Objects.equals(name, column.name) && columnType == column.columnType && Objects.equals(options, column.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columnType, options);
    }
}
