package persistence.sql.dml;

import java.util.List;

public interface DmlGenerator {

    String insert(Object entity);

    <T> List<T> findAll(Class<T> entity);
}
