package builder.ddl.h2;

import builder.ddl.DDLBuilder;
import builder.ddl.DDLColumnData;
import builder.ddl.DDLQueryBuilder;
import builder.ddl.DDLType;
import builder.ddl.h2.builder.H2CreateQueryBuilder;
import builder.ddl.h2.builder.H2DropQueryBuilder;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class H2DDLBuilder implements DDLBuilder {

    private final static String ID_ANNOTATION_OVER_ONE = "@Id 어노테이션은 한개를 초과할수 없습니다.";
    private final static String NOT_EXIST_ENTITY_ANNOTATION = "@Entity 어노테이션이 존재하지 않습니다.";

    EnumMap<DDLType, DDLQueryBuilder> ddlEnumMap = new EnumMap<>(DDLType.class);

    public H2DDLBuilder() {
        ddlEnumMap.put(DDLType.CREATE, new H2CreateQueryBuilder());
        ddlEnumMap.put(DDLType.DROP, new H2DropQueryBuilder());
    }

    @Override
    public <T> String queryBuilder(DDLType ddlType, Class<T> clazz) {
        confirmEntityAnnotation(clazz);
        return this.ddlEnumMap.get(ddlType).buildQuery(getTableName(clazz), getDDLColumnData(clazz));
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
}
