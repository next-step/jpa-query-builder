package persistence.entity;

import domain.Person;

public interface EntityManager {
    // <T> T find(Class<T> clazz, Long Id); 제네릭을 사용해보셔도 됩니다.
    <T> T find(Class<T> clazz, Long Id);

    Object persist(Object entity);

    <T> T remove(Class<T> clazz, Long Id);

    <T> T update(Class<T> clazz, Long Id);
}
