package hibernate.entity;

public interface EntityManager {

    <T> T find(Class<T> clazz, Object id);
}
