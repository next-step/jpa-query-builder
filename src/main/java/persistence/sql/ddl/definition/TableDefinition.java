package persistence.sql.ddl.definition;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;
import persistence.sql.ddl.Queryable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TableDefinition {

    private final String tableName;
    private final List<TableColumn> columns;
    private final PrimaryKey primaryKey;

    public TableDefinition(Class<?> entityClass) {
        validate(entityClass);

        final Field[] fields = entityClass.getDeclaredFields();
        final Field pkField = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Primary key not found"));

        this.tableName = determineTableName(entityClass);
        this.columns = createTableColumns(fields);
        this.primaryKey = new PrimaryKey(pkField);
    }

    @NotNull
    private static String determineTableName(Class<?> entityClass) {
        final String tableName = entityClass.getSimpleName();

        if (entityClass.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entityClass.getAnnotation(Table.class);
            if (!tableAnnotation.name().isEmpty()) {
                return tableAnnotation.name();
            }
        }

        if (entityClass.isAnnotationPresent(Entity.class)) {
            Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
            if (!entityAnnotation.name().isEmpty()) {
                return entityAnnotation.name();
            }
        }

        return tableName;
    }

    private static List<TableColumn> createTableColumns(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .map(TableColumn::new)
                .toList();
    }

    public void validate(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity must be annotated with @Entity");
        }

        validateHasId(clazz.getDeclaredFields());
    }

    private void validateHasId(Field[] fields) {
        List<Field> idFields = Arrays.stream(fields)
                .filter(field ->
                        field.isAnnotationPresent(Id.class)
                ).toList();

        if (idFields.size() != 1) {
            throw new IllegalArgumentException("Entity must have exactly one field annotated with @Id");
        }
    }

    public PrimaryKey primaryKey() {
        return primaryKey;
    }

    public String tableName() {
        return tableName;
    }

    public List<Queryable> queryableColumns() {
        return Stream.concat(
                Stream.of(primaryKey),
                columns.stream()
        ).toList();
    }
}
