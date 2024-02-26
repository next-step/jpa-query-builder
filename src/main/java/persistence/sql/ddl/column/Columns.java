package persistence.sql.ddl.column;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.List;

import static persistence.sql.common.SqlConstant.COMMA;

public class Columns {
    private final List<Column> columns;
    public Columns(List<Field> fields) {
        this.columns = fields.stream()
                .filter(filter -> !filter.isAnnotationPresent(Transient.class))
                .map(Column::new).toList();
    }

    public List<String> getQueries() {
        return this.columns.stream().map(Column::getQuery).toList();
    }

    public List<String> getNames() {
        return this.columns.stream().map(Column::getName).toList();
    }

    public static String getConcatedQueries(List<String> queries) {
        return queries.stream()
                .reduce((s1, s2) -> s1 + COMMA + s2)
                .orElse("");
    }
}
