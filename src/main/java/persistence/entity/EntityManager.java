package persistence.entity;

import example.entity.Person;

public interface EntityManager {
    Person find(Class<Person> clazz, Long id);

    Object persist(Object entity);

    void remove(Object entity);
}
