package builder.dml;

import jakarta.persistence.*;
import util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DMLBuilderData {

    private final static String PK_NOT_EXIST_MESSAGE = "PK 컬럼을 찾을 수 없습니다.";
    private final static String NOT_EXIST_ENTITY_ANNOTATION = "@Entity 어노테이션이 존재하지 않습니다.";
    private final static String GET_FIELD_VALUE_ERROR_MESSAGE = "필드 값을 가져오는 중 에러가 발생했습니다.";
    private final static String COMMA = ", ";
    private final static String EQUALS = "=";

    private final String tableName;
    private final List<DMLColumnData> columns;
    private Object id;

    private <T> DMLBuilderData(Class<T> clazz) {
        confirmEntityAnnotation(clazz);
        this.tableName = getTableName(clazz);
        this.columns = getEntityColumnData(clazz);
    }

    private DMLBuilderData(Object entityInstance) {
        confirmEntityAnnotation(entityInstance.getClass());
        this.tableName = getTableName(entityInstance.getClass());
        this.columns = getInstanceColumnData(entityInstance);
        this.id = getPkValue(this.columns);
    }

    private <T> DMLBuilderData(Class<T> clazz, Object id) {
        confirmEntityAnnotation(clazz);
        this.tableName = getTableName(clazz);
        this.columns = getEntityColumnData(clazz);
        this.id = id;
    }

    public static <T> DMLBuilderData createDMLBuilderData(Class<T> clazz) {
        return new DMLBuilderData(clazz);
    }

    public static DMLBuilderData createDMLBuilderData(Object entityInstance) {
        return new DMLBuilderData(entityInstance);
    }

    public static <T> DMLBuilderData createDMLBuilderData(Class<T> clazz, Object id) {
        return new DMLBuilderData(clazz, id);
    }

    public String getTableName() {
        return tableName;
    }

    public List<DMLColumnData> getColumns() {
        return columns;
    }

    public Object getId() {
        return id;
    }

    public String wrapString() {
        return (this.id instanceof String) ? StringUtil.wrapSingleQuote(this.id) : String.valueOf(this.id);
    }

    // 테이블 열 정의 생성
    public String getColumnDefinitions() {
        return this.columns.stream()
                .filter(column -> !column.isPrimaryKey())
                .map(column -> column.getColumnName() + EQUALS + column.getColumnValueByType())
                .collect(Collectors.joining(COMMA));
    }

    // 테이블 컬럼명 생성
    public String getColumnNames() {
        return this.columns.stream()
                .map(DMLColumnData::getColumnName)
                .collect(Collectors.joining(COMMA));
    }

    //테이블 컬럼 Value 값들 생성
    public String getColumnValues() {
        return this.columns.stream()
                .map(dmlColumnData -> {
                    Object value = dmlColumnData.getColumnValue();
                    if (dmlColumnData.getColumnType() == String.class) { //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
                        return StringUtil.wrapSingleQuote(value);
                    }
                    return String.valueOf(value);
                })
                .collect(Collectors.joining(COMMA));
    }

    //PkName를 가져온다.
    public String getPkName() {
        return this.columns.stream()
                .filter(DMLColumnData::isPrimaryKey)
                .map(DMLColumnData::getColumnName)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(PK_NOT_EXIST_MESSAGE));
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

    //Id 어노테이션을 primarykey로 가져온다.
    private void getEntityPrimaryKey(List<DMLColumnData> DMLColumnDataList, Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            DMLColumnDataList.add(DMLColumnData.creatInstancePkColumn(field.getName(), field.getType()));
        }
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

    //Entity 어노테이션 여부를 확인한다.
    private void confirmEntityAnnotation(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(NOT_EXIST_ENTITY_ANNOTATION);
        }
    }

    //Table 어노테이션 여부를 확인한다.
    private String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return table.name();
        }
        return entityClass.getSimpleName();
    }

    //PkValue를 가져온다.
    private Object getPkValue(List<DMLColumnData> DMLColumnDataList) {
        return DMLColumnDataList.stream()
                .filter(DMLColumnData::isPrimaryKey)
                .findFirst()
                .map(DMLColumnData::getColumnValue)
                .orElseThrow(() -> new IllegalArgumentException(PK_NOT_EXIST_MESSAGE));
    }

}
