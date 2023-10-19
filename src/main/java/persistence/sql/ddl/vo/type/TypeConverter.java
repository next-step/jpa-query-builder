package persistence.sql.ddl.vo.type;

import java.lang.reflect.Field;

public class TypeConverter {
    public static DatabaseType convert(Field field) {
        Class<?> type = field.getType();
        if(type == Long.class) {
            return BigInt.getInstance();
        } else if(type == Integer.class) {
            return Int.getInstance();
        } else if(type == String.class) {
            return new VarChar(field);
        }
        throw new UnsupportedOperationException("타입 변환에 실패했습니다" + field.getName() + " " + field.getType().getTypeName());
    }
}
