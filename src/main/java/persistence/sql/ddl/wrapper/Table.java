package persistence.sql.ddl.wrapper;

import jakarta.persistence.Entity;
import java.util.Arrays;
import java.util.List;

public class Table {

    private final Class<?> clazz;
    private final List<Column> columns;

    private Table(Class<?> clazz, List<Column> columns) {
        this.clazz = clazz;
        this.columns = columns;
    }

    public static Table of(Class<?> clazz) {
        List<Column> columnList = Arrays.stream(clazz.getDeclaredFields())
            .map(Column::from).toList();

        validate(clazz, columnList);

        return new Table(clazz, Arrays.stream(clazz.getDeclaredFields())
            .map(Column::from).toList());
    }


    private static void validate(Class<?> clazz, List<Column> columnList) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("엔티티 객체가 아닙니다.");
        }

        long idFieldCount = columnList.stream().filter(Column::isIdAnnotation).count();

        if (idFieldCount > 1) {
            throw new IllegalArgumentException("Id 필드는 한개만 가져야 합니다.");
        }
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getTableName() {
        return clazz.getSimpleName();
    }
}
