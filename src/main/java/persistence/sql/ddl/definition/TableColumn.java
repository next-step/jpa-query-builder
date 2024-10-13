package persistence.sql.ddl.definition;

import persistence.sql.ddl.Dialect;
import persistence.sql.ddl.Queryable;

import java.lang.reflect.Field;

public class TableColumn implements Queryable {
    private final ColumnDefinition columnDefinition;

    public TableColumn(Field field) {
        this.columnDefinition = new ColumnDefinition(field);
    }

    @Override
    public void apply(StringBuilder query, Dialect dialect) {
        final String type = dialect.translateType(columnDefinition.type(), columnDefinition);
        query.append(columnDefinition.name()).append(" ").append(type);

        if (columnDefinition.shouldNotNull()) {
            query.append(" NOT NULL");
        }

        query.append(", ");
    }
}
