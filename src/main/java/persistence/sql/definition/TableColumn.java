package persistence.sql.definition;

import persistence.sql.Dialect;
import persistence.sql.Queryable;

import java.lang.reflect.Field;

public class TableColumn implements Queryable {
    private final ColumnDefinition columnDefinition;

    public TableColumn(Field field) {
        this.columnDefinition = new ColumnDefinition(field);
    }

    @Override
    public void applyToCreateQuery(StringBuilder query, Dialect dialect) {
        final String type = dialect.translateType(columnDefinition);
        query.append(columnDefinition.name()).append(" ").append(type);

        if (columnDefinition.shouldNotNull()) {
            query.append(" NOT NULL");
        }

        query.append(", ");
    }

    @Override
    public boolean hasValue(Object object) {
        final Object value = columnDefinition.valueAsString(object);
        return value != null;
    }

    @Override
    public String getValue(Object object) {
        final Object value = columnDefinition.valueAsString(object);
        if (value == null) {
            throw new IllegalStateException("Value is null");
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }

        return value.toString();
    }

    @Override
    public String name() {
        return columnDefinition.name();
    }

}
