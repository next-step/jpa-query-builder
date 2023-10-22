package persistence.entitiy;

public interface EntityManager {

    <T> T findById(Class<T> clazz, String Id);

    <T> T persist(T entity);

    void remove(Object entity);
}
