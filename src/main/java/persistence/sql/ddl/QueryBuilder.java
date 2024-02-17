package persistence.sql.ddl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    public String buildDDL(final Class<?> clazz) {
        return "CRETAE TABLE "
            + getTableName(clazz)
            + "  ("
            + getTableColumnDefinition(clazz)
            + ")";
    }

    protected String getColumnDefinitionStatementFromField(Field field) {
        return field.getName() + " " + DataType.of(field) + " " + getColumnConstraints(field);
    }

    protected String getColumnConstraints(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            // TODO: Column Annotation 없이도 컬럼으로 지정해주는 방안 모색
            return "";
        }

        List<String> constraints = new ArrayList<>();

        Column column = field.getAnnotation(Column.class);

        if (column.unique()) {
            constraints.add("UNIQUE");
        }

        if (column.nullable()) {
            constraints.add("NOT NULL");
        }

        return String.join(" ", constraints);
    }

    protected String getTableColumnDefinition(final Class<?> clazz) {
        // TODO: implementation
        return "";
    }

    protected String getTableName(final Class<?> clazz) {
        return clazz.getSimpleName();
    }
}
