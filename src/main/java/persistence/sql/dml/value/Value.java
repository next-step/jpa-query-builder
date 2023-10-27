package persistence.sql.dml.value;

import persistence.sql.ddl.utils.ReflectionUtil;

import java.lang.reflect.Method;

public class Value {
    private Object value;

    public Value(final Object object, final String fieldName) {
        this.value = generateValue(object, fieldName);
    }

    private Object generateValue(final Object object, final String fieldName) {
        String getter = ReflectionUtil.generateGetterMethodName(fieldName);
        try {
            Method method = object.getClass().getMethod(getter);
            return method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(fieldName + "에 " + getter + "가 존재하지 않습니다.");
        }
    }

    public Object getValue() {
        return this.value;
    }
}
