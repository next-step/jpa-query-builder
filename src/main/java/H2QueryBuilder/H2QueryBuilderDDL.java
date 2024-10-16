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
    private final static String CREATE_QUERY   = "CREATE TABLE %s (%s);";  // %s를 사용하여 테이블 이름과 컬럼 정보를 대체
    private final static String DROP_QUERY     = "DROP TABLE %s;";
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
                .map(tableColumnAttribute -> {
                                        String info = tableColumnAttribute.getColumnName() + " " + tableColumnAttribute.getColumnDataType();
                                        if (tableColumnAttribute.isNotNull()) info += NOT_NULL;
                                        if (tableColumnAttribute.isAutoIncrement()) info += AUTO_INCREMENT;
                                        if (tableColumnAttribute.isPrimeKey()) info += PRIMARY_KEY;
                                        return info;
                                    })
                .collect(Collectors.joining(", "));

        return String.format(CREATE_QUERY, getTableName(entityClass), columnInfo);
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
    private List<TableColumnAttribute> getColumn(Class<?> entityClass) {
        List<TableColumnAttribute> tableColumnAttributes = new ArrayList<>();

        Field[] fields = entityClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> generateTableColumnData(tableColumnAttributes, field));

        return tableColumnAttributes;
    }

    // column 조립
    private void generateTableColumnData(List<TableColumnAttribute> tableColumnAttributes, Field field) {
        String columnName = field.getName();
        boolean isNullable = true;

        if ( field.isAnnotationPresent(Column.class) ) {
            Column column = field.getAnnotation(Column.class);
            columnName  = column.name().isEmpty() ? columnName : column.name();
            isNullable = column.nullable();

        }

        if ( field.isAnnotationPresent(Id.class) ) {
            tableColumnAttributes.add(new TableColumnAttribute(columnName, field.getType(), true, true, isGenerateValueIdentity(field)));
        } else {
            tableColumnAttributes.add(new TableColumnAttribute(columnName, field.getType(), false, !isNullable, isGenerateValueIdentity(field)));
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

        return String.format(DROP_QUERY, getTableName(entityClass));
    }
}
