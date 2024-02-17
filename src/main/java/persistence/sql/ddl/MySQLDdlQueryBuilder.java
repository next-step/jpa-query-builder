package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;

import static persistence.sql.ddl.MySQLColumnType.CLOSE_BRACKET;
import static persistence.sql.ddl.MySQLColumnType.OPEN_BRACKET;

public class MySQLDdlQueryBuilder implements DdlQueryBuilder {

    private static final String COLUMN_SEPARATOR = ", ";
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String SPACE = " ";
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String END_STR = ";";
    private static final String NOT_NULL = "NOT NULL";
    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";
    private static final String EMPTY_SPACE = "";

    public String createQuery(Class<?> type) {
        if (!type.isAnnotationPresent(Entity.class)){
            throw new IllegalArgumentException("entity annotation is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(CREATE_TABLE)
                .append(addTableName(type))
                .append(OPEN_BRACKET);

        String columns = Arrays.stream(type.getDeclaredFields())
                .filter(field-> !field.isAnnotationPresent(Transient.class))
                .map(this::buildColumn)
                .reduce((columnA, columnB) -> String.join(COLUMN_SEPARATOR, columnA, columnB))
                .orElseThrow(IllegalStateException::new);

        return sb.append(columns)
                .append(CLOSE_BRACKET)
                .append(END_STR)
                .toString();
    }

    private String addTableName(Class<?> type) {
        Table table = type.getAnnotation(Table.class);
        if (table != null && table.name().length() > 0){
            return table.name();
        }
        return type.getSimpleName();
    }

    private String buildColumn(Field field) {
        Class<?> fieldType = field.getType();
        String fieldName = getFieldName(field);

        StringBuilder sb = new StringBuilder();
        sb.append(fieldName)
                .append(SPACE)
                .append(MySQLColumnType.convert(fieldType))
                .append(addPrimaryKeyConstraint(field))
                .append(addNullConstraint(field));

        return sb.toString();
    }

    private String getFieldName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null && column.name().length() > 0){
            return column.name();
        }
        return field.getName();
    }

    private String addPrimaryKeyConstraint(Field field) {
        StringBuilder sb = new StringBuilder();
        if (field.isAnnotationPresent(Id.class)){
            sb.append(SPACE)
              .append(PRIMARY_KEY);
        }
        if (field.isAnnotationPresent(GeneratedValue.class)){
            sb.append(addIncrementStrategy(field));
        }
        return sb.toString();
    }

    private String addIncrementStrategy(Field field) {
        GeneratedValue strategy = field.getAnnotation(GeneratedValue.class);
        if (strategy.strategy().equals(GenerationType.IDENTITY)){
           return SPACE + AUTO_INCREMENT;
        }
        return EMPTY_SPACE;
    }

    private String addNullConstraint(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if(annotation != null && !annotation.nullable()){
            return SPACE + NOT_NULL;
        }
        return EMPTY_SPACE;
    }
}
