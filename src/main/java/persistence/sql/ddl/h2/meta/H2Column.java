package persistence.sql.ddl.h2.meta;

import jakarta.persistence.Id;
import persistence.sql.Dialect;
import persistence.sql.ddl.h2.H2Dialect;

import java.lang.reflect.Field;

public class H2Column {
    private final Field field;
    private final Dialect dialect = new H2Dialect();

    public H2Column(final Field field) {
        this.field = field;
    }

    public String generateColumnSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(getColumnName());
        sb.append(" ");
        sb.append(dialect.getColumnDataType(field));
        sb.append(" ");
        sb.append(dialect.getColumnNullableType(field));
        if (isPK()) {
            sb.append(" ");
            sb.append(getGenerationType());
        }
        return sb.toString().trim();
    }

    private String getColumnName() {
        return new ColumnName(field).getColumnName();
    }

    private String getGenerationType() {
        return PKGenerationType.getSQL(field);
    }

    private boolean isPK() {
        return field.isAnnotationPresent(Id.class);
    }
}
