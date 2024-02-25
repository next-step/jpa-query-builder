package persistence.sql.ddl.translator;

import persistence.sql.ddl.ClassComponentType;

import java.util.List;

public interface ClassComponentTranslator<T, R> {

    List<R> invoke(List<T> loadedClassComponent);

    ClassComponentType type();
}
