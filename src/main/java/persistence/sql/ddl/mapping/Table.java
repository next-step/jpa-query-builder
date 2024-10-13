package persistence.sql.ddl.mapping;

import jakarta.persistence.Entity;

import java.util.Arrays;
import java.util.List;

public record Table(String name, List<Column> columns) {

    public static Table from(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("@Entity not exist. class = " + clazz.getName());
        }

        String name = clazz.getSimpleName();
        if (clazz.isAnnotationPresent(jakarta.persistence.Table.class)) {
            jakarta.persistence.Table annotation = clazz.getAnnotation(jakarta.persistence.Table.class);
            name = annotation.name();
        }

        return new Table(
                name,
                Arrays.stream(clazz.getDeclaredFields())
                        .map(Column::new)
                        .toList()
        );
    }

    public boolean hasPrimaryKey() {
        for (Column column : columns) {
            if (column.isIdentity()) {
                return true;
            }
        }
        return false;
    }

    public Column primaryColumn() {
        for (Column column : columns) {
            if (column.isIdentity()) {
                return column;
            }
        }
        throw new RuntimeException("primary key column not exist.");
    }

}
