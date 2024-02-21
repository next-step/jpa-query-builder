package persistence.sql.ddl.h2.meta;

import jakarta.persistence.Id;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;
import persistence.sql.meta.ColumnName;

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
            sb.append(" ");
            sb.append("PRIMARY KEY");
        }
        return sb.toString().trim();
    }

    private String getColumnName() {
        return new ColumnName(field).getColumnName();
    }

    private String getGenerationType() {
        return dialect.getPKGenerationType(field);
    }

    private boolean isPK() {
        return field.isAnnotationPresent(Id.class);
    }
}
