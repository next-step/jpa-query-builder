package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;

public abstract class DDLQueryBuilder implements QueryBuilderAdapter {

    static void appendColumnAttributes(Field field, StringBuilder sb) {
        if (isPrimaryKey(field)) {
            sb.append(" PRIMARY KEY");
        }

        if (isAutoIncrement(field)) {
            sb.append(" AUTO_INCREMENT");
        }

        if (isNotNullable(field)) {
            sb.append(" NOT NULL");
        }
    }

    String getColumnName(Field field) {
        // Column 어노테이션이 존재하고 name 속성이 있으면 사용, 없으면 필드 이름 사용
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }
        return field.getName();
    }

    static String getTableName(Class<?> field) {
        if (field.isAnnotationPresent(Table.class)) {
            Table table = field.getAnnotation(Table.class);
            return !table.name().isEmpty() ? table.name() : field.getSimpleName();
        }
        return field.getSimpleName();
    }

    private static boolean isAutoIncrement(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class) &&
                field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
    }

    private static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private static boolean isNotNullable(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && !column.nullable();
    }
}
