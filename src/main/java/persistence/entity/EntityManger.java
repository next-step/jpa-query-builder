package persistence.entity;

import persistence.Person;

public interface EntityManger {
    // TODO: RowMapper 하고 제너릭으로
    Person find(Class<Person> clazz, Long id);

    Object persist(Object entity);

    void remove(Object entity);
}
