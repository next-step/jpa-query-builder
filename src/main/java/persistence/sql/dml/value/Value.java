package persistence.sql.dml.value;

import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.utils.ReflectionUtil;

import java.lang.reflect.Method;

public class Value {
    private Object value;

    public Value() {

    }

    public Object getValue(Object object, String fieldName) {
        String getter = ReflectionUtil.generateGetterMethodName(fieldName);
        try {
            Method method = object.getClass().getMethod(getter);
            Object invoke = method.invoke(object);
            return invoke;
        } catch (Exception e) {
            throw new RuntimeException(fieldName + "에 " + getter + "가 존재하지 않습니다.");
        }
    }
}
