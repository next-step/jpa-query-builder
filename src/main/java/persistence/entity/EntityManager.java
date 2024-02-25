package persistence.entity;

public interface EntityManager {

    <T> T find(Class<T> clazz, Long id);
}
