package persistence.sql.translator;

import persistence.sql.ClassComponentType;

public interface ClassComponentTranslator<T, R> {

    R invoke(T loadedClassComponent);

    ClassComponentType type();
}
