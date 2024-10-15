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
    private final static String DROP_QUERY     = "DROP TABLE {tableName};";
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

        String columnInfo = getColumn(entityClass)
                .stream()
                .map(columnData -> {
                                        String info = columnData.getColumnName() + " " + columnData.getColumnDataType();
                                        if (columnData.isNotNull()) info += NOT_NULL;
                                        if (columnData.isAutoIncrement()) info += AUTO_INCREMENT;
                                        if (columnData.isPrimeKey()) info += PRIMARY_KEY;
                                        return info;
                                    })
                .collect(Collectors.joining(", "));

        return CREATE_QUERY
                .replace(TABLE_NAME, getTableName(entityClass))
                .replace(COLUMN_INFO, columnInfo);
    }

    // table 이름 가져오기
    private String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return table.name();
        }
        return entityClass.getSimpleName();
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
        String columnName = field.getName();
        boolean isNullable = true;

        if ( field.isAnnotationPresent(Column.class) ) {
            Column column = field.getAnnotation(Column.class);
            columnName  = column.name().isEmpty() ? columnName : column.name();
            isNullable = column.nullable();

        }

        if ( field.isAnnotationPresent(Id.class) ) {
            columnDatas.add(new ColumnData(columnName, field.getType(), true, true, isGenerateValueIdentity(field)));
        } else {
            columnDatas.add(new ColumnData(columnName, field.getType(), false, !isNullable, isGenerateValueIdentity(field)));
        }
    }

    // GeneratedType 체크
    private boolean isGenerateValueIdentity(Field field) {
        if ( !field.isAnnotationPresent(GeneratedValue.class) ) {
            return false;
        }

        return field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
    }

    @Override
    public String drop(Class<?> entityClass) {
        if ( !entityClass.isAnnotationPresent(Entity.class) ) {
            throw new IllegalArgumentException(ErrorCode.NOT_EXIST_ENTITY_ANNOTATION.getErrorMsg());
        }

        return DROP_QUERY.replace(TABLE_NAME, getTableName(entityClass));
    }
}
