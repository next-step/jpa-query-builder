package persistence.sql.ddl;

import jakarta.persistence.Id;
import persistence.sql.ddl.exception.NoIdentifierException;
import persistence.sql.ddl.mapping.TableColumn;
import persistence.sql.ddl.mapping.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SchemaCreator {

    private final Class<?> entityClass;
    private final List<TableColumn> tableColumns = new ArrayList<>();
    private PrimaryKey primaryKey;

    public SchemaCreator(Class<?> entityClass) {
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
        if (field.isAnnotationPresent(Id.class)) {
            primaryKey = new PrimaryKey(field);
        }
        tableColumns.add(new TableColumn(field));
    }

    public String create() {
        if (!hasPrimaryKey()) {
            throw new NoIdentifierException(entityClass.getName());
        }

        StringBuilder builder = new StringBuilder(String.format("CREATE TABLE %s (", entityClass.getSimpleName()));
        builder.append(createColumnDdlString());
        builder.append(primaryKey.createDdlString());
        builder.append(")");

        return builder.toString();
    }

    private boolean hasPrimaryKey() {
        return primaryKey != null;
    }

    private String createColumnDdlString() {
        return tableColumns.stream()
            .map(TableColumn::createDdlString)
            .collect(Collectors.joining(", "));
    }

}
