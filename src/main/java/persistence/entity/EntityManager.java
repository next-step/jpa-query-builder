package persistence.entity;

public interface EntityManager {
    // <T> T find(Class<T> clazz, Long Id); 제네릭을 사용해보셔도 됩니다.
    <T> Object find(Class<T> clazz, Long Id);

    Object persist(Object entity);

    void remove(Class<?> clazz, Long Id);

    void update(Object entity);
}
