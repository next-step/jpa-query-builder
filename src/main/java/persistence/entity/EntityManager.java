package persistence;

public interface EntityManager {

     <T> T find(Class<T> clazz, Long Id);
    Person find(Class<Person> clazz, Long id);

    Object persist(Object entity);

    void remove(Object entity);
}
