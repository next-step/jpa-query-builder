package persistence.sql.ddl.loader;

import persistence.sql.ddl.ClassComponentType;

import java.util.List;

public interface ClassComponentLoader<R> {

    List<R> invoke(Class<?> clazz);

    ClassComponentType type();
}
