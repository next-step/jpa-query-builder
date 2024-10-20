package persistence.sql.entity;

import persistence.sql.ddl.Person;

public interface EntityManager {

    <T> T find(Class<T> clazz, Long id);

    Object persist(Object entity);

    void remove(Object Entity);
}
