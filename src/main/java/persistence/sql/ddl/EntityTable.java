package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.ddl.exception.IncorrectIdColumnException;
import persistence.sql.ddl.exception.NotEntityException;
import persistence.sql.ddl.exception.NotFoundColumnException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public record EntityTable(Class<?> clazz, String tableName, List<EntityColumn> allColumns, EntityIdColumn idColumn,
                          List<EntityColumn> columns) {
    public static EntityTable from(Object entity) {
        return from(entity.getClass());
    }

    public static EntityTable from(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NotEntityException();
        }

        Field[] declaredFields = clazz.getDeclaredFields();

        return new EntityTable(
            clazz,
            getTableName(clazz),
            getAllColumns(declaredFields),
            getFieldByIdColumn(declaredFields),
            getColumns(declaredFields)
        );
    }

    private static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);

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

        return EntityIdColumn.from(ids.getFirst());
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

    public Object newInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmptyId(Object entity) {
        return getId(entity) == null;
    }

    public Object getId(Object entity) {
        return idColumn.getValue(entity);
    }

    public void applyId(Object entity, Object id) {
        idColumn.applyId(entity, id);
    }
}
