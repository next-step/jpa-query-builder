package builder.h2.ddl;

import builder.QueryBuilder;
import builder.QueryBuilderDDL;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class H2QueryBuilderDDL extends QueryBuilder implements QueryBuilderDDL {

    private final static String ID_ANNOTATION_OVER_ONE = "@Id 어노테이션은 한개를 초과할수 없습니다.";
    private final static String CREATE_QUERY = "CREATE TABLE {tableName} ({columnDefinitions});";
    private final static String DROP_QUERY = "DROP TABLE {tableName};";
    private final static String PRIMARY_KEY = " PRIMARY KEY";
    private final static String NOT_NULL = " NOT NULL";
    private final static String AUTO_INCREMENT = " AUTO_INCREMENT";
    private final static String COMMA = ", ";
    private final static String BLANK = " ";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_DEFINITIONS = "{columnDefinitions}";

    //create 쿼리를 생성한다.
    @Override
    public String buildCreateQuery(Class<?> entityClass) {
        confirmEntityAnnotation(entityClass);
        return createTableQuery(getTableName(entityClass), getDDLColumnData(entityClass));
    }

    //drop 쿼리를 생성한다.
    @Override
    public String buildDropQuery(Class<?> entityClass) {
        confirmEntityAnnotation(entityClass);
        return dropTableQuery(getTableName(entityClass));
    }

    //create 쿼리를 생성한다.
    public String createTableQuery(String tableName, List<DDLColumnData> columns) {
        // 테이블 열 정의 생성
        String columnDefinitions = columns.stream()
                .map(column -> {
                    String definition = column.columnName() + BLANK + column.columnDataType();
                    // primary key인 경우 "PRIMARY KEY" 추가
                    if (column.isNotNull()) definition += NOT_NULL; //false면 NOT_NULL 조건 추가
                    if (column.isAutoIncrement()) definition += AUTO_INCREMENT; //true면 AutoIncrement 추가
                    if (column.isPrimaryKey()) definition += PRIMARY_KEY; //PK면 PK조건 추가
                    return definition;
                })
                .collect(Collectors.joining(COMMA));

        // 최종 SQL 쿼리 생성
        return CREATE_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_DEFINITIONS, columnDefinitions);
    }

    //Drop 쿼리 생성
    public String dropTableQuery(String tableName) {
        return DROP_QUERY.replace(TABLE_NAME, tableName);
    }

    //변수들의 정보를 가져온다.
    private List<DDLColumnData> getDDLColumnData(Class<?> entityClass) {
        List<DDLColumnData> columnData = Arrays.stream(entityClass.getDeclaredFields())
                .map(this::createTableDDLColumnData)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        confirmIdAnnotationOverTwo(columnData);
        return columnData;
    }

    //테이블에 생성될 필드(컬럼)들을 생성한다.
    private DDLColumnData createTableDDLColumnData(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return getPrimaryKey(field);
        }
        return getColumnAnnotationData(field);
    }

    //Id 어노테이션을 primarykey로 가져온다.
    private DDLColumnData getPrimaryKey(Field field) {
        return DDLColumnData.createPk(
                field.getName(),
                field.getType(),
                confirmGeneratedValueAnnotation(field)
        );
    }

    //Column 어노테이션 여부를 확인하여 변수의 컬럼타입을 가져온다.
    private DDLColumnData getColumnAnnotationData(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return null;
        }
        String columnName = field.getName();
        boolean isNullable = true;

        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name().isEmpty() ? columnName : column.name();
            return DDLColumnData.createColumn(
                    columnName,
                    field.getType(),
                    !column.nullable());
        }

        return DDLColumnData.createColumn(
                columnName,
                field.getType(),
                !isNullable
        );
    }

    // Entity에 @Id가 2개 이상은 아닐지 확인한다.
    private void confirmIdAnnotationOverTwo(List<DDLColumnData> DDLColumnDataList) {
        long primaryKeyCount = DDLColumnDataList.stream()
                .filter(DDLColumnData::isPrimaryKey)
                .count();

        if (primaryKeyCount >= 2) {
            throw new IllegalArgumentException(ID_ANNOTATION_OVER_ONE); // 2개 이상이면 예외 발생
        }
    }

    //GeneratedValue 어노테이션 전략을 확인한다.
    private boolean confirmGeneratedValueAnnotation(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return false;
        }
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        return generatedValue.strategy() == GenerationType.IDENTITY;
    }

}
