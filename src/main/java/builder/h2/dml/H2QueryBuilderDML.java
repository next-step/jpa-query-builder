package builder.h2.dml;

import builder.QueryBuilder;
import builder.QueryBuilderDML;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class H2QueryBuilderDML extends QueryBuilder implements QueryBuilderDML {

    private final static String PK_NOT_EXIST_MESSAGE = "PK 컬럼을 찾을 수 없습니다.";
    private final static String GET_FIELD_VALUE_ERROR_MESSAGE = "필드 값을 가져오는 중 에러가 발생했습니다.";

    private final static String INSERT_QUERY = "INSERT INTO {tableName} ({columnNames}) VALUES ({values});";
    private final static String FIND_ALL_QUERY = "SELECT {columnNames} FROM {tableName};";
    private final static String FIND_BY_ID_QUERY = "SELECT {columnNames} FROM {tableName} WHERE {entityPkName} = {values};";
    private final static String UPDATE_BY_ID_QUERY = "UPDATE {tableName} SET {columnDefinitions} WHERE {entityPkName} = {values};";
    private final static String DELETE_BY_ID_QUERY = "DELETE FROM {tableName} WHERE {entityPkName} = {values};";
    private final static String DELETE_QUERY = "DELETE FROM {tableName};";
    private final static String COMMA = ", ";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_NAMES = "{columnNames}";
    private final static String VALUES = "{values}";
    private final static String ENTITY_PK_NAME = "{entityPkName}";
    private final static String COLUMN_DEFINITIONS = "{columnDefinitions}";
    private final static String EQUALS = "=";

    //insert 쿼리를 생성한다. Insert 쿼리는 인스턴스의 데이터를 받아야함
    @Override
    public <T> String buildInsertQuery(T entityInstance) {
        confirmEntityAnnotation(entityInstance.getClass());
        return insertQuery(getTableName(entityInstance.getClass()), getInstanceColumnData(entityInstance));
    }

    //findAll 쿼리를 생성한다.
    @Override
    public String buildFindAllQuery(Class<?> entityClass) {
        confirmEntityAnnotation(entityClass);
        return findAllQuery(getTableName(entityClass), getEntityColumnData(entityClass));
    }

    //findById 쿼리를 생성한다.
    @Override
    public String buildFindByIdQuery(Class<?> entityClass, Object id) {
        confirmEntityAnnotation(entityClass);
        return findByIdQuery(getTableName(entityClass), getEntityColumnData(entityClass), id);
    }

    //Object를 받아 findById 쿼리를 생성한다.
    @Override
    public String buildFindObjectQuery(Object entityInstance) {
        confirmEntityAnnotation(entityInstance.getClass());
        List<DMLColumnData> dmlColumnData = getInstanceColumnData(entityInstance);
        return findByIdQuery(getTableName(entityInstance.getClass()), dmlColumnData, getPkValue(dmlColumnData));
    }

    //Object를 받아 UpdateById 쿼리를 생성한다.
    @Override
    public String buildUpdateQuery(Object entityInstance) {
        confirmEntityAnnotation(entityInstance.getClass());
        List<DMLColumnData> dmlColumnData = getInstanceColumnData(entityInstance);
        return updateByIdQuery(getTableName(entityInstance.getClass()), dmlColumnData, getPkValue(dmlColumnData));
    }

    //deleteById 쿼리를 생성한다.
    @Override
    public String buildDeleteByIdQuery(Class<?> entityClass, Object id) {
        confirmEntityAnnotation(entityClass);
        return deleteByIdQuery(getTableName(entityClass), getEntityColumnData(entityClass), id);
    }

    //Object를 받아 deleteById 쿼리를 생성한다.
    @Override
    public String buildDeleteObjectQuery(Object entityInstance) {
        confirmEntityAnnotation(entityInstance.getClass());
        List<DMLColumnData> dmlColumnData = getInstanceColumnData(entityInstance);
        return deleteByIdQuery(getTableName(entityInstance.getClass()), dmlColumnData, getPkValue(dmlColumnData));
    }

    //deleteAll 쿼리를 생성한다.
    @Override
    public String buildDeleteQuery(Class<?> entityClass) {
        confirmEntityAnnotation(entityClass);
        return deleteQuery(getTableName(entityClass));
    }

    //insert쿼리문을 생성한다.
    private String insertQuery(String tableName, List<DMLColumnData> columns) {
        //컬럼명을 가져온다.
        String columnNames = columns.stream()
                .map(DMLColumnData::getColumnName)
                .collect(Collectors.joining(COMMA));

        //Insert 할 Value 값들을 가져온다.
        String columnValues = columns.stream()
                .map(dmlColumnData -> {
                    Object value = dmlColumnData.getColumnValue();
                    if (dmlColumnData.getColumnType() == String.class) { //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
                        return StringUtil.wrapSingleQuote(value);
                    }
                    return String.valueOf(value);
                })
                .collect(Collectors.joining(COMMA));

        return INSERT_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_NAMES, columnNames)
                .replace(VALUES, columnValues);
    }

    //findAll 쿼리문을 생성한다.
    private String findAllQuery(String tableName, List<DMLColumnData> columns) {
        //컬럼명을 가져온다.
        String columnNames = columns.stream()
                .map(DMLColumnData::getColumnName)
                .collect(Collectors.joining(COMMA));

        return FIND_ALL_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_NAMES, columnNames);
    }

    //findAll 쿼리문을 생성한다.
    private String findByIdQuery(String tableName, List<DMLColumnData> columns, Object id) {
        //컬럼명을 가져온다.
        String columnNames = columns.stream()
                .map(DMLColumnData::getColumnName)
                .collect(Collectors.joining(COMMA));

        //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
        if (id instanceof String) {
            id = StringUtil.wrapSingleQuote(id);
        }

        return FIND_BY_ID_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_NAMES, columnNames)
                .replace(ENTITY_PK_NAME, (String) getPkName(columns))
                .replace(VALUES, String.valueOf(id));
    }

    //update 쿼리를 생성한다.
    public String updateByIdQuery(String tableName, List<DMLColumnData> columns, Object id) {
        // 테이블 열 정의 생성
        String columnDefinitions = columns.stream()
                .filter(column -> !column.isPrimaryKey())
                .map(column -> column.getColumnName() + EQUALS + column.getColumnValueByType())
                .collect(Collectors.joining(COMMA));

        if (id instanceof String) //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
            id = StringUtil.wrapSingleQuote(id);

        // 최종 SQL 쿼리 생성
        return UPDATE_BY_ID_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_DEFINITIONS, columnDefinitions)
                .replace(ENTITY_PK_NAME, (String) getPkName(columns))
                .replace(VALUES, String.valueOf(id));
    }

    //delete 쿼리문을 생성한다.
    private String deleteByIdQuery(String tableName, List<DMLColumnData> columns, Object id) {
        //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
        if (id instanceof String) {
            id = StringUtil.wrapSingleQuote(id);
        }

        return DELETE_BY_ID_QUERY.replace(TABLE_NAME, tableName)
                .replace(ENTITY_PK_NAME, (String) getPkName(columns))
                .replace(VALUES, String.valueOf(id));
    }

    //delete 쿼리문을 생성한다.
    private String deleteQuery(String tableName) {
        return DELETE_QUERY.replace(TABLE_NAME, tableName);
    }

    //Id 어노테이션을 primarykey로 가져온다.
    private void getEntityPrimaryKey(List<DMLColumnData> DMLColumnDataList, Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            DMLColumnDataList.add(DMLColumnData.creatInstancePkColumn(field.getName(), field.getType()));
        }
    }

    //PkValue를 가져온다.
    private Object getPkName(List<DMLColumnData> DMLColumnDataList) {
        return DMLColumnDataList.stream()
                .filter(DMLColumnData::isPrimaryKey)
                .map(DMLColumnData::getColumnName)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(PK_NOT_EXIST_MESSAGE));
    }

    //PkValue를 가져온다.
    private Object getPkValue(List<DMLColumnData> DMLColumnDataList) {
        return DMLColumnDataList.stream()
                .filter(DMLColumnData::isPrimaryKey)
                .findFirst()
                .map(DMLColumnData::getColumnValue)
                .orElseThrow(() -> new IllegalArgumentException(PK_NOT_EXIST_MESSAGE));
    }

    //Id 어노테이션을 primarykey로 가져온다.
    private <T> void getInstancePrimaryKey(List<DMLColumnData> DMLColumnDataList, Field field, T entityInstance) {
        try {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                DMLColumnDataList.add(DMLColumnData.creatEntityPkColumn(field.getName(), field.getType(), field.get(entityInstance)));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(GET_FIELD_VALUE_ERROR_MESSAGE + field.getName(), e);
        }
    }

    //Entity Class의 컬럼명과 컬럼데이터타입을 가져온다.
    private List<DMLColumnData> getEntityColumnData(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        List<DMLColumnData> DMLColumnDataList = new ArrayList<>();
        for (Field field : fields) {
            getEntityPrimaryKey(DMLColumnDataList, field);
            createDMLEntityColumnData(DMLColumnDataList, field);
        }
        return DMLColumnDataList;
    }

    //Entity 인스턴스의 컬럼명과 컬럼데이터타입, 컬럼데이터를 가져온다.
    private <T> List<DMLColumnData> getInstanceColumnData(T entityInstance) {
        Field[] fields = entityInstance.getClass().getDeclaredFields();
        List<DMLColumnData> DMLColumnDataList = new ArrayList<>();
        for (Field field : fields) {
            getInstancePrimaryKey(DMLColumnDataList, field, entityInstance);
            createDMLInstanceColumnData(DMLColumnDataList, field, entityInstance);
        }
        return DMLColumnDataList;
    }

    //Entity 내부 필드를 확인하여 필드명을 가져온다.
    private void createDMLEntityColumnData(List<DMLColumnData> DMLColumnDataList, Field field) {
        if (field.isAnnotationPresent(Transient.class) || field.isAnnotationPresent(Id.class))
            return; // @Transient인 경우 검증하지 않음

        String columnName = field.getName();

        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name().isEmpty() ? columnName : column.name();
        }

        DMLColumnDataList.add(DMLColumnData.createEntityColumn(columnName));
    }

    //인스턴스 내부 데이터를 확인하여 컬럼 데이터를 가져온다.
    private <T> void createDMLInstanceColumnData(List<DMLColumnData> DMLColumnDataList, Field field, T entityInstance) {
        if (field.isAnnotationPresent(Transient.class) || field.isAnnotationPresent(Id.class))
            return; // @Transient인 경우 검증하지 않음

        String columnName = field.getName();

        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name().isEmpty() ? columnName : column.name();
        }

        field.setAccessible(true);

        try {
            DMLColumnDataList.add(DMLColumnData.creatInstanceColumn(columnName, field.getType(), field.get(entityInstance)));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(GET_FIELD_VALUE_ERROR_MESSAGE + field.getName(), e);
        }
    }
}
