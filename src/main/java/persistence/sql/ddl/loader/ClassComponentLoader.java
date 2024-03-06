package persistence.sql.ddl.loader;

import persistence.sql.ddl.ClassComponentType;

public interface ClassComponentLoader<R> {

    R invoke(Class<?> clazz);

    ClassComponentType type();
}
