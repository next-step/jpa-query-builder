package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.ddl.exception.NoIdentifierException;
import persistence.sql.ddl.mapping.PrimaryKey;
import persistence.sql.ddl.mapping.TableColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SchemaGenerator {

    private final Class<?> entityClass;
    private final List<TableColumn> tableColumns = new ArrayList<>();
    private PrimaryKey primaryKey;

    public SchemaGenerator(Class<?> entityClass) {
        this.entityClass = entityClass;
        init(entityClass);
    }

    private void init(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            mappingColumns(field);
        }
    }

    private void mappingColumns(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }

        if (field.isAnnotationPresent(Id.class)) {
            primaryKey = new PrimaryKey(field);
        }

        tableColumns.add(new TableColumn(field));
    }

    public String generateDropTableDdlString() {
        String tableName = entityClass.getName();
        if (entityClass.isAnnotationPresent(Table.class)) {
            tableName = entityClass.getAnnotation(Table.class).name();
        }
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }

    public String generateCreateTableDdlString() {
        if (!hasPrimaryKey()) {
            throw new NoIdentifierException(entityClass.getName());
        }

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("CREATE TABLE %s (", createTableNameDdlString()));
        builder.append(createColumnDdlString());
        builder.append(primaryKey.createDdlString());
        builder.append(")");

        return builder.toString();
    }

    private boolean hasPrimaryKey() {
        return primaryKey != null;
    }

    private String createTableNameDdlString() {
        if (!entityClass.isAnnotationPresent(Table.class)) {
            return entityClass.getSimpleName();
        }

        Table annotation = entityClass.getAnnotation(Table.class);
        String name = annotation.name();

        if (name.isEmpty()) {
            return entityClass.getSimpleName();
        }

        return name;
    }

    private String createColumnDdlString() {
        return tableColumns.stream()
            .map(TableColumn::createDdlString)
            .collect(Collectors.joining(", "));
    }

}
