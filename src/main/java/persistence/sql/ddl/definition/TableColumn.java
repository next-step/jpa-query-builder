package persistence.sql.ddl.definition;

import jakarta.persistence.Transient;
import persistence.sql.ddl.Queryable;

import java.lang.reflect.Field;

public class TableColumn implements Queryable {

    private final boolean isTransient;
    private final ColumnDefinition columnDefinition;

    public TableColumn(Field field) {

        this.isTransient = field.isAnnotationPresent(Transient.class);
        this.columnDefinition = new ColumnDefinition(field);
    }

    @Override
    public void apply(StringBuilder query) {
        if (isTransient) return;

        query.append(columnDefinition.name()).append(" ").append(columnDefinition.type());

        if (columnDefinition.shouldNotNull()) {
            query.append(" NOT NULL");
        }

        query.append(", ");
    }
}
