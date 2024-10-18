package builder.dml.h2;

import builder.dml.DMLBuilder;
import builder.dml.DMLColumnData;
import builder.dml.DMLQueryBuilder;
import builder.dml.DMLType;
import builder.dml.h2.builder.*;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class H2DMLBuilder implements DMLBuilder {

    private final static String PK_NOT_EXIST_MESSAGE = "PK 컬럼을 찾을 수 없습니다.";
    private final static String NOT_EXIST_ENTITY_ANNOTATION = "@Entity 어노테이션이 존재하지 않습니다.";
    private final static String GET_FIELD_VALUE_ERROR_MESSAGE = "필드 값을 가져오는 중 에러가 발생했습니다.";

    EnumMap<DMLType, DMLQueryBuilder> dmlEnumMap = new EnumMap<>(DMLType.class);

    public H2DMLBuilder() {
        dmlEnumMap.put(DMLType.SELECT_ALL, new H2SelectAllQueryBuilder());
        dmlEnumMap.put(DMLType.SELECT_BY_ID, new H2SelectByIdQueryBuilder());
        dmlEnumMap.put(DMLType.INSERT, new H2InsertQueryBuilder());
        dmlEnumMap.put(DMLType.UPDATE, new H2UpdateQueryBuilder());
        dmlEnumMap.put(DMLType.DELETE, new H2DeleteQueryBuilder());
    }

    @Override
    public <T> String queryBuilder(DMLType dmlType, Class<T> clazz) {
        confirmEntityAnnotation(clazz);
        return dmlEnumMap.get(dmlType).buildQuery(getTableName(clazz), getEntityColumnData(clazz));
    }

    @Override
    public String queryBuilder(DMLType dmlType, Object entityInstance) {
        List<DMLColumnData> dmlColumnData = getInstanceColumnData(entityInstance);
        return dmlEnumMap.get(dmlType).buildQuery(getTableName(entityInstance.getClass()), dmlColumnData, getPkValue(dmlColumnData));
    }

    @Override
    public <T> String queryBuilder(DMLType dmlType, Class<T> clazz, Object Id) {
        confirmEntityAnnotation(clazz);
        return dmlEnumMap.get(dmlType).buildQuery(getTableName(clazz), getEntityColumnData(clazz), Id);
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

    //PkValue를 가져온다.
    public static String getPkName(List<DMLColumnData> DMLColumnDataList) {
        return DMLColumnDataList.stream()
                .filter(DMLColumnData::isPrimaryKey)
                .map(DMLColumnData::getColumnName)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(PK_NOT_EXIST_MESSAGE));
    }
}
