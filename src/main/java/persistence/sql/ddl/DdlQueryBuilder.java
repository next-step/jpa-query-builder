package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.ddl.domain.DatabaseColumn;
import persistence.sql.ddl.domain.DatabasePrimaryColumn;
import persistence.sql.ddl.view.QueryResolver;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static persistence.sql.ddl.CommonConstant.END_STR;
import static persistence.sql.ddl.CommonConstant.SPACE;
import static persistence.sql.ddl.columntype.MySQLColumnType.CLOSE_BRACKET;
import static persistence.sql.ddl.columntype.MySQLColumnType.OPEN_BRACKET;

public class DdlQueryBuilder implements DdlQueryBuild {

    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String DROP_TABLE = "DROP TABLE";

    private final QueryResolver queryResolver;

    protected DdlQueryBuilder(QueryResolver queryResolver) {
        this.queryResolver = queryResolver;
    }

    @Override
    public String createQuery(Class<?> type) {
        validateEntityClass(type);

        StringBuilder sb = new StringBuilder();
        sb.append(CREATE_TABLE)
                .append(SPACE)
                .append(addTableName(type))
                .append(OPEN_BRACKET);

        List<DatabaseColumn> columns = Arrays.stream(type.getDeclaredFields())
                .filter(this::isMappingColumn)
                .map(this::buildColumn)
                .toList();

        return sb.append(queryResolver.toQuery(columns))
                .append(CLOSE_BRACKET)
                .append(END_STR)
                .toString();
    }

    private boolean isMappingColumn(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private DatabaseColumn buildColumn(Field field) {
        String fieldName = getFieldName(field);
        Integer length = getFieldLength(field);

        if (field.isAnnotationPresent(Id.class)) {
            GenerationType generationType = getGenerationType(field);
            return new DatabasePrimaryColumn(fieldName, field.getType(), length, generationType);
        }
        boolean nullable = isNullable(field);
        return new DatabaseColumn(fieldName, field.getType(), length, nullable);
    }

    private GenerationType getGenerationType(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return null;
        }
        return field.getAnnotation(GeneratedValue.class).strategy();
    }

    private boolean isNullable(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column == null || column.nullable();
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
