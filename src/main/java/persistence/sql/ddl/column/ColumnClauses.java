package persistence.sql.ddl.column;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.List;

public class ColumnClauses {
    private final List<ColumnClause> columnClauses;
    public ColumnClauses(List<Field> fields) {
        this.columnClauses = getColumnClauses(fields);
    }

    private static List<ColumnClause> getColumnClauses(List<Field> fields) {
        return fields.stream()
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnClause::new).toList();
    }

    public List<String> getQueries() {
        return this.columnClauses.stream().map(ColumnClause::getQuery).toList();
    }


    public List<String> getNames() {
        return this.columnClauses.stream().map(ColumnClause::name).toList();
    }

    public int columnSize() {
        return this.columnClauses.size();
    }
}
