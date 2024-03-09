package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import persistence.sql.model.ColumnFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


public class DMLHelper {
    private static final String INSERT_QUERY_FORMAT = "insert into %s (%s) values (%s);";
    private static final String FIND_ALL_QUERY_FORMAT = "select * from %s;";
    private static final String FIND_QUERY_FORMAT = "select * from %s %s;";
    private static final String DELETE_QUERY_FORMAT = "delete from %s %s;";

    public String getInsertQuery(Class<?> clazz, Object object) {
        return String.format(INSERT_QUERY_FORMAT, getTableName(clazz), columnsClause(clazz), valueClause(object));
    }

    public String getFindAllQuery(Class<?> clazz) {
        return String.format(FIND_ALL_QUERY_FORMAT, getTableName(clazz));
    }

    public String getFindByIdQuery(Class<?> clazz, Object id) {
        try {
            var idField = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> {
                        var column = ColumnFactory.createColumn(field);
                        return column.isPresent() && column.get().isPK();
                    })
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("기본키가 존재하지 않습니다."));

            var object = clazz.getDeclaredConstructor().newInstance();
            idField.setAccessible(true);
            idField.set(object, id);

            return String.format(FIND_QUERY_FORMAT, getTableName(clazz), whereClause(object));
        } catch (Exception e) {
            throw new IllegalArgumentException("생성 불가 한 class 입니다.");
        }
    }

    public String getDeleteQuery(Class<?> clazz, Object object) {
        return String.format(
                DELETE_QUERY_FORMAT,
                getTableName(clazz),
                whereClause(object)
        );
    }

    private String whereClause(Object object) {
        var clazz = object.getClass();

        return "where " + Arrays.stream(clazz.getDeclaredFields())
                .map(field -> {
                        var columnValue = getColumnValue(field, object);
                        var column = ColumnFactory.createColumn(field);
                        if (columnValue.isEmpty() || column.isEmpty()) return null;
                        return String.format("%s = %s", column.get().getName(), columnValue.get());
                    }
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" and "));
    }

    private String getTableName(Class<?> clazz) {
        var annotations = clazz.getAnnotations();
        return Arrays.stream(annotations)
                .filter(annotation -> annotation.annotationType().equals(Table.class))
                .map(table -> ((Table) table).name())
                .findFirst()
                .orElse(clazz.getSimpleName());
    }

    private String columnsClause(Class<?> clazz) {
        var fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
            .filter(this::isInsertQueryTarget)
            .map(ColumnFactory::createColumn)
            .filter(Optional::isPresent)
            .map(column -> column.get().getName())
            .collect(Collectors.joining(", "));
    }

    private String valueClause(Object object) {
        var clazz = object.getClass();
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(this::isInsertQueryTarget)
            .map(field -> getColumnValue(field, object))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.joining(", "));
    }
    private boolean isInsertQueryTarget(Field field) {
        var isId = false;
        var isGeneratedValue = false;
        var isColumn = false;

        for (Annotation annotation : field.getAnnotations()) {
            if (annotation instanceof Column) {
                isColumn = true;
            } else if (annotation instanceof Id) {
                isId = true;
            } else if (annotation instanceof GeneratedValue) {
                isGeneratedValue = true;
            }
        }

        return isColumn || (isId && !isGeneratedValue);
    }

    private Optional<String> getColumnValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            var column = ColumnFactory.createColumn(field);
            if (column.isPresent()) {
                return Optional.ofNullable(column.get().getQueryValue(field.get(object)));
            }
            return Optional.empty();
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("접근할 수 없는 필드입니다.");
        }
    }
}
