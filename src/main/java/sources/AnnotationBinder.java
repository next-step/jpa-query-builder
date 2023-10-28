package sources;

import exception.AnnotationException;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class AnnotationBinder {

    public AnnotationBinder() {
    }

    //엔티티 어노테이션 바인더(필수)
    public String entityBinder(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class) ) {
            throw new AnnotationException( "Type '" + entityClass.getName()
                    + "@Entity 가 아닙니다." );
        }

        if (entityClass.isAnnotationPresent(Table.class) ) {
            Table table = entityClass.getDeclaredAnnotation(Table.class);
            return table.name();
        }

        return entityClass.getSimpleName();
    }

    //엔티티의 아이디를 찾는 바인더(필수)
    public String entityIdBinder(Field field) {
        if(!field.isAnnotationPresent(Id.class)) {
            throw new AnnotationException( "Type '" + field.getName()
                    + "@id 가 아닙니다." );
        }

        return field.getName();
    }

    public String entityIdOptionBinder(Field field) {
        // 요구사항2. generatedValue 어노테이션이 있을 경우에 처리한다.
        if(field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue declaredAnnotation = field.getDeclaredAnnotation(GeneratedValue.class);
            return registerGenerators(declaredAnnotation.strategy());
            // not null auto_increment
        }
        return " int ";
    }

    // 컬럼 어노테이션이 설정되어 있으면 컬럼 어노테이션의 이름 사용, 아니면 필드 이름 사용
    public ColumnMetaData columnBinder(Class<?> entity, Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        if(field.isAnnotationPresent(Column.class)) {
            Column column = field.getDeclaredAnnotation(Column.class);
            String name = column.name().isEmpty() ? field.getName() : column.name();
            int length = column.length();
            boolean nullable = column.nullable();
            String type = field.getType().getSimpleName();
            return new ColumnMetaData()
                    .name(name)
                    .fieldName(field.getName())
                    .type(type)
                    .length(length)
                    .nullable(nullable)
                    .build();
        }
        return new ColumnMetaData()
                .name(field.getName())
                .fieldName(field.getName())
                .type(field.getType().getSimpleName());
    }

    private String registerGenerators(GenerationType type) {
        switch (type) {
            case IDENTITY:
                return " LONG AUTO_INCREMENT PRIMARY KEY";
            case AUTO:
                return " LONG AUTO_INCREMENT PRIMARY KEY";
            case UUID:
                return "";
            default:
                return "";
        }
    }
}
