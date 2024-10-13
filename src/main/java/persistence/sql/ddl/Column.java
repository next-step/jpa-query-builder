package persistence.sql.ddl;

import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class Column {

    private static final Logger logger = LoggerFactory.getLogger(Column.class);
    private String name;
    private ColumnType columnType;
    private boolean primary;

    public Column(Field field) {
        this.name = field.getName();
        this.columnType = ColumnType.of(field.getType());
        this.primary = field.isAnnotationPresent(Id.class);
    }

    public String getName() {
        return name;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public boolean isPrimary() {
        return primary;
    }
}
