package persistence.sql.usecase;

import java.lang.reflect.Field;
import persistence.sql.vo.DatabaseField;

public class GetFieldValueUseCase {

    public Object execute(Object object, DatabaseField databaseField) {
        if(object == null) {
            throw new NullPointerException("object should not be null");
        }
        return getFieldValue(object, databaseField.getOriginalFieldName());
    }

    private Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            throw new RuntimeException("field 값 조회 실패 " + fieldName);
        }
    }
}
