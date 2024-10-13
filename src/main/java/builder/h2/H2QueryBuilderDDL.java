package builder.h2;

import builder.QueryBuilderDDL;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class H2QueryBuilderDDL implements QueryBuilderDDL {

    private final static String NOT_EXIST_ENTITY_ANNOTATION = "@Entity 어노테이션이 존재하지 않습니다.";
    private final static String ID_ANNOTATION_OVER_ONE = "@Id 어노테이션은 한개를 초과할수 없습니다.";
    private final static String CREATE_QUERY = "CREATE TABLE {tableName} ({columnDefinitions});";
    private final static String DROP_QUERY = "DROP TABLE {tableName};";
    private final static String PRIMARY_KEY = " PRIMARY KEY";
    private final static String NOT_NULL = " NOT NULL";
    private final static String AUTO_INCREMENT = " AUTO_INCREMENT";
    private final static String COMMA = ", ";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_DEFINITIONS = "{columnDefinitions}";

    //create 쿼리를 생성한다.
    @Override
    public String buildCreateQuery(Class<?> entityClass) {
        confirmEntityAnnotation(entityClass);
        return createTableQuery(confirmTableAnnotation(entityClass), getColumnData(entityClass));
    }

    //drop 쿼리를 생성한다.
    @Override
    public String buildDropQuery(Class<?> entityClass) {
        confirmEntityAnnotation(entityClass);
        return dropTableQuery(confirmTableAnnotation(entityClass));
    }

    //데이터타입에 따른 컬럼 데이터타입을 가져온다.
    @Override
    public String getDataType(String dataType) {
        return H2DataType.findH2DataTypeByDataType(dataType);
    }

    //create 쿼리를 생성한다.
    public String createTableQuery(String tableName, List<ColumnData> columns) {
        // 테이블 열 정의 생성
        String columnDefinitions = columns.stream()
                .map(column -> {
                    String definition = column.getColumnName() + " " + column.getColumnDataType();
                    // primary key인 경우 "PRIMARY KEY" 추가
                    if (!column.isCheckNull()) definition += NOT_NULL; //false면 NOT_NULL 조건 추가
                    if (column.isAutoIncrement()) definition += AUTO_INCREMENT; //true면 AutoIncrement 추가
                    if (column.isPrimaryKey()) definition += PRIMARY_KEY; //PK면 PK조건 추가
                    return definition;
                })
                .collect(Collectors.joining(COMMA));

        // 최종 SQL 쿼리 생성
        return CREATE_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_DEFINITIONS, columnDefinitions);
    }

    public String dropTableQuery(String tableName) {
        return DROP_QUERY.replace(TABLE_NAME, tableName);
    }

    //Entity 어노테이션 여부를 확인한다.
    private void confirmEntityAnnotation(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(NOT_EXIST_ENTITY_ANNOTATION);
        }
    }

    //변수들의 정보를 가져온다.
    private List<ColumnData> getColumnData(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        List<ColumnData> columnData = new ArrayList<>();

        for (Field field : fields) {
            confirmAnnotation(columnData, field);
        }

        return columnData;
    }

    private void confirmAnnotation(List<ColumnData> columnData, Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            confirmIdOverTwo(columnData);
            confirmIdAnnotation(columnData, field);
        } else {
            confirmColumnAnnotation(columnData, field);
        }
    }

    private void confirmIdOverTwo(List<ColumnData> columnData) {
        boolean hasPrimaryKey = columnData.stream()
                .anyMatch(ColumnData::isPrimaryKey); // primaryKey가 true인 컬럼이 하나라도 있는지 확인

        if (hasPrimaryKey) {
            throw new IllegalArgumentException(ID_ANNOTATION_OVER_ONE);
        }
    }

    //Id 어노테이션을 primarykey로 가져온다.
    private void confirmIdAnnotation(List<ColumnData> columnData, Field field) {
        columnData.add(new ColumnData(field.getName(), getDataType(field.getType().getName()), true, false, confirmGeneratedValueAnnotation(field)));
    }

    //GeneratedValue 어노테이션 전략을 확인한다.
    public boolean confirmGeneratedValueAnnotation(Field field) {
        boolean autoIncrement = false;
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            GenerationType generationType = generatedValue.strategy();
            if (generationType == GenerationType.IDENTITY) {
                autoIncrement = true;
            }
        }
        return autoIncrement;
    }

    //Column 어노테이션 여부를 확인하여 변수의 컬럼타입을 가져온다.
    private void confirmColumnAnnotation(List<ColumnData> columnData, Field field) {
        String columnName = field.getName();
        boolean checkNull = true;
        if (field.isAnnotationPresent(Transient.class)) return; //Transient 어노테이션 존재시 테이블에 추가 X
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                columnName = column.name();
            }
            checkNull = column.nullable();
        }
        columnData.add(new ColumnData(
                columnName,
                getDataType(field.getType().getName()),
                false,
                checkNull,
                false)
        );
    }

    //Table 어노테이션 여부를 확인한다.
    private String confirmTableAnnotation(Class<?> entityClass) {
        String tableName = entityClass.getSimpleName();
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            tableName = table.name();
        }
        return tableName;
    }

}
