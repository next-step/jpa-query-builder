package persistence.entity;

import java.lang.reflect.InvocationTargetException;

public class InstanceFactory<T> {
    public static final String NO_DEFAULT_CONSTRUCTOR_FAILED_MESSAGE = "기본 생성자가 없습니다.";
    private static final String INSTANCE_CREATION_FAILED_MESSAGE = "인스턴스 생성을 실패하였습니다.";

    private final Class<T> clazz;

    InstanceFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    T createInstance() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(NO_DEFAULT_CONSTRUCTOR_FAILED_MESSAGE);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(INSTANCE_CREATION_FAILED_MESSAGE);
        }
    }
}
