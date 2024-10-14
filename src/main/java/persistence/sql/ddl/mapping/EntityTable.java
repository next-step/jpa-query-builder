package persistence.sql.ddl.mapping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class EntityTable {

    private final Table table;
    private final List<TableColumn> columns;
    private final TablePrimaryKey primaryKey;

    public static EntityTable from(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return new EntityTable(
                Table.from(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(notIdField())
                        .filter(notTransientField())
                        .map(TableColumn::new)
                        .toList(),
                TablePrimaryKey.from(fields)
        );
    }

    @NotNull
    private static Predicate<Field> notIdField() {
        return field -> !field.isAnnotationPresent(Id.class)
                && !field.isAnnotationPresent(GeneratedValue.class);
    }

    @NotNull
    private static Predicate<Field> notTransientField() {
        return field -> !field.isAnnotationPresent(Transient.class);
    }

    private EntityTable(Table table, List<TableColumn> columns, TablePrimaryKey primaryKey) {
        this.table = table;
        this.columns = columns;
        this.primaryKey = primaryKey;
    }

    public String tableName() {
        return table.name();
    }

    public List<TableColumn> columns() {
        return columns;
    }

    public TablePrimaryKey primaryKey() {
        return primaryKey;
    }

}
