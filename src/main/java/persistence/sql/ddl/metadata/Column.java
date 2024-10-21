package persistence.sql.ddl.metadata;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    public String getSqlType() {
        return columnType.getSqlType();
    }

    public String getName() {
        return name.value();
    }

    public List<String> getSqlOptions() {
        return options.stream()
                .map(ColumnOption::getOption)
                .toList();
    }

    public boolean hasOptions() {
        return !options.isEmpty();
    }
}
