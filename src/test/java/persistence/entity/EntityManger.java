package persistence.entity;

import persistence.Person;

public interface EntityManger {
    Person find(Class<Person> clazz, Long id);

    Object persist(Object entity);

    void remove(Object entity);
}
