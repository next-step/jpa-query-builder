package persistence;

import java.util.List;

public interface EntityManager {

    <T> T find(Class<T> clazz, Long id);

    <T> List<T> findAll(Class<T> clazz);

    Object persist(Object entityInstance);

    Object update(Object entityInstance);

    void remove(Object entityInstance);

}
