package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.field.TableField;

public class DropQueryBuilder {

    private static final String DDL_FORMAT = "DROP TABLE IF EXISTS %s;";

    private final TableField tableField;

    public DropQueryBuilder(Class<?> klass) {

        if (!klass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity가 존재하지 않습니다");
        }

        this.tableField = new TableField(klass);
    }

    public String toSQL() {
        return String.format(DDL_FORMAT, tableField.toSQL());
    }
}
