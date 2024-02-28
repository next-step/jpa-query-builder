package persistence.sql.ddl.column;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.List;

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
        return this.columns.stream().map(Column::name).toList();
    }
}
