package persistence.entitiy;

public interface EntityManager {

    <T> T findById(Class<T> clazz, String Id);

    Object persist(Object entity);

    void remove(Object entity);
}
