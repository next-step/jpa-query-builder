package H2QueryBuilder;

import common.ErrorCode;
import jakarta.persistence.*;
import repository.QueryBuilderDDL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class H2QueryBuilderDDL implements QueryBuilderDDL {
    private final static String CREATE_QUERY   = "CREATE TABLE {tableName} ({columnInfo});";
    private final static String PRIMARY_KEY    = " PRIMARY KEY";
    private final static String NOT_NULL       = " NOT NULL";
    private final static String AUTO_INCREMENT = " AUTO_INCREMENT";
    private final static String TABLE_NAME     = "{tableName}";
    private final static String COLUMN_INFO    = "{columnInfo}";

    @Override
    public String create(Class<?> entityClass) {
        return generateCreateTableQuery(entityClass);
    }

    private String generateCreateTableQuery(Class<?> entityClass) {
        if ( !entityClass.isAnnotationPresent(Entity.class) ) {
            throw new IllegalArgumentException(ErrorCode.NOT_EXIST_ENTITY_ANNOTATION.getErrorMsg());
        }
        StringBuilder sb = new StringBuilder();

        String columnInfo = getColumn(entityClass)
                .stream()
                .map(columnData -> {
                                        sb.append(columnData.getColumnName()).append(" ").append(columnData.getColumnDataType());
                                        if (columnData.isNotNull()) sb.append(NOT_NULL);
                                        if (columnData.isAutoIncrement()) sb.append(AUTO_INCREMENT);
                                        if (columnData.isPrimeKey()) sb.append(PRIMARY_KEY);
                                        return sb.toString();
                                    })
                .collect(Collectors.joining(", "));

        return CREATE_QUERY
                .replace(TABLE_NAME, entityClass.getSimpleName())
                .replace(COLUMN_INFO, columnInfo);
    }

    //조립된 컬럼 가져오기
    private List<ColumnData> getColumn(Class<?> entityClass) {
        List<ColumnData> columnDatas = new ArrayList<>();

        Field[] fields = entityClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> generateTableColumnData(columnDatas, field));

        return columnDatas;
    }

    // column 조립
    private void generateTableColumnData(List<ColumnData> columnDatas, Field field) {
        if ( field.isAnnotationPresent(Id.class) ) {
            columnDatas.add(new ColumnData(field.getName(), field.getType(), isGenerateValueIdentity(field), false, true));
        }

        if ( field.isAnnotationPresent(Column.class) ) {
            String columnName = field.getName();
            boolean isNullable;

            Column column = field.getAnnotation(Column.class);
            columnName  = column.name().isEmpty() ? columnName : column.name();
            isNullable = column.nullable();
            columnDatas.add(new ColumnData(columnName, field.getType(), false, !isNullable, false));
        }
    }

    // GeneratedType 체크
    private boolean isGenerateValueIdentity(Field field) {
        if ( !field.isAnnotationPresent(GeneratedValue.class) ) {
            return false;
        }

        return field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
    }
}
