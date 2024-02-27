package persistence.sql.dml.clause;

import jakarta.persistence.Id;
import persistence.sql.meta.column.ColumnName;
import persistence.sql.meta.pk.GenerationType;

import java.lang.reflect.Field;
import java.util.Arrays;

public class PKClause {

    private final GenerationType generationType;
    private final ColumnName columnName;

    public PKClause(Class<?> clazz) {
        Field pkField = findPKField(clazz);
        this.generationType = GenerationType.of(clazz);
        this.columnName = new ColumnName(pkField);
    }

    private Field findPKField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Id 어노테이션은 반드시 존재해야합니다."));
    }

    public String getName() {
        return columnName.getName();
    }
}
