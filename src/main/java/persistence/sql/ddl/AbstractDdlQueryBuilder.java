package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;

import static persistence.sql.ddl.CommonConstant.END_STR;
import static persistence.sql.ddl.CommonConstant.SPACE;
import static persistence.sql.ddl.mysql.MySQLColumnType.CLOSE_BRACKET;
import static persistence.sql.ddl.mysql.MySQLColumnType.OPEN_BRACKET;

public abstract class AbstractDdlQueryBuilder implements DdlQueryBuilder {

    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String DROP_TABLE = "DROP TABLE";
    private static final String COLUMN_SEPARATOR = ", ";

    private final DatabaseTypeConverter databaseTypeConverter;

    protected AbstractDdlQueryBuilder(DatabaseTypeConverter databaseTypeConverter) {
        this.databaseTypeConverter = databaseTypeConverter;
    }

    protected abstract String addConstraints(Field field);

    @Override
    public String createQuery(Class<?> type) {
        validateEntityClass(type);

        StringBuilder sb = new StringBuilder();
        sb.append(CREATE_TABLE)
                .append(SPACE)
                .append(addTableName(type))
                .append(OPEN_BRACKET);

        String columns = Arrays.stream(type.getDeclaredFields())
                .filter(this::isMappingColumn)
                .map(this::buildColumn)
                .reduce(this::combineColumn)
                .orElseThrow(IllegalStateException::new);

        return sb.append(columns)
                .append(CLOSE_BRACKET)
                .append(END_STR)
                .toString();
    }

    private boolean isMappingColumn(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private String buildColumn(Field field) {
        String fieldName = getFieldName(field);
        Integer length = getFieldLength(field);

        return fieldName +
                SPACE +
                databaseTypeConverter.convert(field.getType(), length) +
                addConstraints(field);
    }

    private String combineColumn(String columnA, String columnB) {
        return String.join(COLUMN_SEPARATOR, columnA, columnB);
    }


    private String getFieldName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null && column.name().length() > 0) {
            return column.name();
        }
        return field.getName();
    }

    private Integer getFieldLength(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null && column.length() > 0) {
            return column.length();
        }
        return null;
    }


    @Override
    public String dropQuery(Class<?> type) {
        validateEntityClass(type);

        return DROP_TABLE +
                SPACE +
                addTableName(type) +
                END_STR;
    }

    private String addTableName(Class<?> type) {
        Table table = type.getAnnotation(Table.class);
        if (table != null && table.name().length() > 0) {
            return table.name();
        }
        return type.getSimpleName();
    }

    private void validateEntityClass(Class<?> type) {
        if (type.isAnnotationPresent(Entity.class)) {
            return;
        }
        throw new IllegalArgumentException("entity annotation is required");
    }

}
