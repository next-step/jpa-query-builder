package persistence;

public interface EntityManager {

    <T> void createTable(Class<T> tClass);

    <T> T find(Class<T> clazz, Long Id);

   <T> T persist(T entity);

    void remove(Object entity);

}
