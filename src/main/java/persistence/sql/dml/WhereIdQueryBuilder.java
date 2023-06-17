package persistence.sql.dml;

import jakarta.persistence.Id;
import persistence.sql.exception.IdNameNotFoundException;
import persistence.sql.view.ColumnValue;

import java.util.Arrays;

public class WhereIdQueryBuilder<T> {
    private final Class<T> clazz;

    public WhereIdQueryBuilder(Class<T> clazz) {this.clazz = clazz;}

    public String build(Object id) {
        return new StringBuilder()
                .append(" WHERE ")
                .append(getIdName())
                .append(" = ")
                .append(ColumnValue.render(id))
                .toString();
    }

    private String getIdName() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findAny()
                .orElseThrow(() -> new IdNameNotFoundException())
                .getName();
    }
}
