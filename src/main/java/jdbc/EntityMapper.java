package jdbc;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityMapper {

    private final static String FAILED_GET_COLUMN = "컬럼 데이터를 가져오는데 실패했습니다.";
    private final static String FAILED_ACCESS_FIELD = "필드에 접근을 실패했습니다.";
    private final static String FAILED_CREATE_INSTANCE = "인스턴스를 생성하는데 실패하였습니다.";

    //입력 받은 Entity 에 맞게 자동으로 매핑한다.
    public static <T> T mapRow(ResultSet rs, Class<T> entityClass) {
        try {
            // 해당 클래스의 인스턴스 생성
            T entityInstance = entityClass.getDeclaredConstructor().newInstance();
            Field[] fields = entityClass.getDeclaredFields();

            for (Field field : fields) {
                confirmAnnotationSetColumnFieldName(field, rs, entityInstance);
            }
            return entityInstance;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(FAILED_CREATE_INSTANCE);
        }
    }

    // 인스턴스의 어노테이션을 검증하여 컬럼 데이터를 세팅해준다.
    private static <T> void confirmAnnotationSetColumnFieldName(Field field, ResultSet rs, T entityInstance) {
        if (field.isAnnotationPresent(Transient.class)) return;
        field.setAccessible(true);
        try {
            String columnName = field.getName();
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnName = column.name().isEmpty() ? columnName : column.name();
            }
            Object value = rs.getObject(columnName);
            field.set(entityInstance, value);
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_GET_COLUMN);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FAILED_ACCESS_FIELD);
        }
    }

}