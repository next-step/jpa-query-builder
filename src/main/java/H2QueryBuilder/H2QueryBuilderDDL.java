package H2QueryBuilder;

import common.ErrorCode;
import jakarta.persistence.*;
import repository.QueryBuilderDDL;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class H2QueryBuilderDDL implements QueryBuilderDDL {
    private final static String CREATE_QUERY = "CREATE TABLE %s (%s);";  // %s를 사용하여 테이블 이름과 컬럼 정보를 대체
    private final static String DROP_QUERY = "DROP TABLE %s;";
    private final static String PRIMARY_KEY = " PRIMARY KEY";
    private final static String NOT_NULL = " NOT NULL";
    private final static String AUTO_INCREMENT = " AUTO_INCREMENT";

    @Override
    public String create(Class<?> entityClass) {
        return generateCreateTableQuery(entityClass);
    }

    private String generateCreateTableQuery(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
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
        TableColumnAttribute tableColumnAttribute = new TableColumnAttribute();

        Field[] fields = entityClass.getDeclaredFields();
        Arrays.stream(fields).forEach(tableColumnAttribute::generateTableColumnMeta);

        return tableColumnAttribute.getTableAttributes();
    }

    // GeneratedType 체크
    private boolean isGenerateValueIdentity(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return false;
        }

        return field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
    }

    @Override
    public String drop(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(ErrorCode.NOT_EXIST_ENTITY_ANNOTATION.getErrorMsg());
        }

        return String.format(DROP_QUERY, getTableName(entityClass));
    }
}
