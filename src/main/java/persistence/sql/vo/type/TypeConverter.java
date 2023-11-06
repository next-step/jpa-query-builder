package persistence.sql.vo.type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeConverter {
    public static DatabaseType convert(Field field) {
        Class<?> type = field.getType();
        if(type == Long.class || type == long.class) {
            return BigInt.getInstance();
        } else if(type == Integer.class || type == int.class) {
            return Int.getInstance();
        } else if(type == String.class) {
            return new VarChar(field);
        }
        throw new UnsupportedOperationException("타입 변환에 실패했습니다 " + field.getName() + " " + field.getType().getTypeName());
    }
}
