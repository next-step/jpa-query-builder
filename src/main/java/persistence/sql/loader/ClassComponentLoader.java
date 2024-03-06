package persistence.sql.loader;

import persistence.sql.ClassComponentType;

public interface ClassComponentLoader<R> {

    R invoke(Class<?> clazz);

    ClassComponentType type();
}
