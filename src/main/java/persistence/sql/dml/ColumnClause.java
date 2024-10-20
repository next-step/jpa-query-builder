package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import persistence.sql.ddl.ColumnName;

public class ColumnClause<T> {

    private final T entity;

    public ColumnClause(T entity) {
        this.entity = entity;
    }

    public String getClause() {
        StringBuilder clause = new StringBuilder();

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                continue;
            }

            clause
                .append(new ColumnName(field).getColumnName())
                .append(", ");
        }

        if (clause.length() > 1) {
            clause.setLength(clause.length() - 2);
        }

        return clause.toString();
    }

}
