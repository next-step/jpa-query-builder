package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.exception.IncorrectIdColumnException;
import persistence.sql.ddl.exception.NotEntityException;
import persistence.sql.ddl.exception.NotFoundColumnException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public record EntityTable(String tableName, List<EntityColumn> allColumns, EntityIdColumn idColumn,
                          List<EntityColumn> columns) {
    public static <T> EntityTable from(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NotEntityException();
        }

        Field[] declaredFields = clazz.getDeclaredFields();

        return new EntityTable(
            getTableName(clazz),
            getAllColumns(declaredFields),
            getFieldByIdColumn(declaredFields),
            getColumns(declaredFields)
        );
    }

    private static <T> String getTableName(Class<T> clazz) {
        jakarta.persistence.Table table = clazz.getAnnotation(jakarta.persistence.Table.class);

        if (table == null) {
            return clazz.getSimpleName();
        }

        return table.name();
    }

    private static List<EntityColumn> getAllColumns(Field[] fields) {
        return Arrays.stream(fields)
            .filter(it -> !it.isAnnotationPresent(Transient.class))
            .map(EntityColumn::from)
            .toList();
    }

    private static EntityIdColumn getFieldByIdColumn(Field[] fields) {
        List<Field> ids = Arrays.stream(fields)
            .filter(it -> it.isAnnotationPresent(Id.class))
            .toList();

        if (ids.size() != 1) {
            throw new IncorrectIdColumnException();
        }

        return EntityIdColumn.from(ids.get(0));
    }


    private static List<EntityColumn> getColumns(Field[] fields) {
        return Arrays.stream(fields)
            .filter(it -> !it.isAnnotationPresent(Id.class) && !it.isAnnotationPresent(Transient.class))
            .map(EntityColumn::from)
            .toList();
    }

    public List<String> getColumnNames() {
        return columns.stream().map(EntityColumn::name)
            .toList();
    }

    public List<String> getAllColumnNames() {
        return allColumns.stream().map(EntityColumn::name)
            .toList();
    }

    public String getNameOfIdColumn() {
        return idColumn.name();
    }

    public Field getFieldByIdColumn() {
        return idColumn.getField();
    }

    public Field getFieldByColumnName(String columnName) {
        return allColumns.stream().filter(it -> it.isEqualName(columnName))
            .findFirst().orElseThrow(NotFoundColumnException::new)
            .field();
    }
}
