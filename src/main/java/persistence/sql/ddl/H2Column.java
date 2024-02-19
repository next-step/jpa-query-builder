package persistence.sql.ddl;

import jakarta.persistence.Id;
import persistence.sql.ddl.h2.ColumnName;
import persistence.sql.ddl.h2.DataType;
import persistence.sql.ddl.h2.Nullable;
import persistence.sql.ddl.h2.PKGenerationType;

import java.lang.reflect.Field;

public class H2Column {
    public static final String BLANK_STRING = "";
    private final Field field;

    public H2Column(final Field field) {
        this.field = field;
    }

    public String generateColumnSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(getColumnName());
        sb.append(" ");
        sb.append(getDataType());
        sb.append(" ");
        sb.append(getColumnNullable());
        if (isPK()) {
            sb.append(" ");
            sb.append(getGenerationType());
        }
        return sb.toString().trim();
    }

    private String getColumnName() {
        return new ColumnName(field).getColumnName();
    }

    private String getColumnNullable() {
        return Nullable.getSQL(field);
    }

    private String getDataType() {
        return DataType.getSQL(field);
    }

    private String getGenerationType() {
        return PKGenerationType.getSQL(field);
    }

    private boolean isPK() {
        return field.isAnnotationPresent(Id.class);
    }
}
