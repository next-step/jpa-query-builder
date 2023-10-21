package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class ReflectionUtil {

    private ReflectionUtil() {
    }

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object getValueFrom(Field field, Object object) {
        field.setAccessible(true);

        Object value;
        try {
            value = field.get(object);
        } catch (Exception e) {
            log.error("Entity의 필드를 읽어오는데 실패함", e);
            throw new RuntimeException(e);
        }
        return value;
    }

}
