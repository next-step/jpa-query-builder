package persistence.sql.ddl.translator;

import persistence.sql.ddl.ClassComponentType;

public interface ClassComponentTranslator<T, R> {

    R invoke(T loadedClassComponent);

    ClassComponentType type();
}
