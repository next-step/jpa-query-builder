package persistence.sql.meta;

import jakarta.persistence.Entity;
import java.util.List;
import org.h2.util.StringUtils;

public class Table {

    private final Class<?> clazz;
    private final Columns columns;

    private Table(Class<?> clazz, Columns columns) {
        this.clazz = clazz;
        this.columns = columns;
    }

    public static Table from(Class<?> clazz) {
        Columns columns = Columns.from(clazz.getDeclaredFields());
        validate(clazz, columns);

        return new Table(clazz, columns);
    }

    private static void validate(Class<?> clazz, Columns columns) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("엔티티 객체가 아닙니다.");
        }

        long idFieldCount = columns.getIdCount();

        if (idFieldCount != 1) {
            throw new IllegalArgumentException("Id 필드는 필수로 1개를 가져야 합니다.");
        }
    }

    public List<Column> getColumns() {
        return columns.getColumns();
    }

    public String getTableName() {
        jakarta.persistence.Table table = clazz.getAnnotation(jakarta.persistence.Table.class);
        if (table == null || StringUtils.isNullOrEmpty(table.name())) {
            return clazz.getSimpleName();
        }
        return table.name();
    }
}
