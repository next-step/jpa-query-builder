package persistence;

import java.util.List;

public interface EntityManager {

    <T> T find(Class<T> clazz, Long id);

    <T> List<T> findAll(Class<T> clazz);

    void persist(Object entityInstance);

    void update(Object entityInstance);

    void remove(Object entityInstance);

}
