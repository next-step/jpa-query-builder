package persistence.sql.query;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import persistence.exception.NotExistException;
import persistence.sql.dialect.Dialect;

public interface QueryBuilder {

    static final String WHERE = "where";
    static final String AND = "and";

    String build(Class<?> clazz, Dialect dialect);

    default String columnsClause(List<String> columnNames) {
        StringBuilder builder = new StringBuilder();
        return builder.append(String.join(", ", columnNames))
                .toString();
    }

    default String whereClause(Class<?> clazz) {
        StringBuilder builder = new StringBuilder();
        builder.append( " " )
                .append( WHERE )
                .append( Arrays.stream(clazz.getDeclaredFields())
                        .map(this::columnConditionClause)
                        .collect(Collectors.joining(AND, " ", " "))
                );
        return builder.toString();
    }

    private String columnConditionClause(Field field) {
        String fieldName = field.getName();
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(fieldName);
            return fieldClause(fieldName, fieldValue);
        } catch (Exception ex) {
            throw new NotExistException("field name '" + fieldName + "'", ex);
        }
    }

    @NotNull
    private String fieldClause(String fieldName, Object fieldValue) {
        if (fieldValue == null) {
            return "";
        }
        return fieldName + " = ?";
    }

}
