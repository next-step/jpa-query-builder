package persistence;

import persistence.entity.Person;

public interface EntityManager {

    <T> void createTable(Class<T> tClass);

    void dropTable(Class<Person> personClass);

    <T> T find(Class<T> clazz, Long Id);

   <T> T persist(T entity);

    void remove(Object entity);

}
