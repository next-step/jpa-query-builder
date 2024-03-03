package persistence.sql.ddl.column;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.List;

public class ColumnClauses {
    private final List<ColumnClause> columnClauses;
    public ColumnClauses(List<Field> fields) {
        this.columnClauses = fields.stream()
                .filter(filter -> !filter.isAnnotationPresent(Transient.class))
                .map(ColumnClause::new).toList();
    }

    public List<String> getQueries() {
        return this.columnClauses.stream().map(ColumnClause::getQuery).toList();
    }

    public List<String> getNames() {
        return this.columnClauses.stream().map(ColumnClause::name).toList();
    }
}
