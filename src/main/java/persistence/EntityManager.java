package persistence;

import domain.Person;

public interface EntityManager {

    // <T> T find(Class<T> clazz, Long Id); 제네릭을 사용해보셔도 됩니다.
    Person find(Class<Person> clazz, Long id);

    Object persist(Object entity);

    void remove(Object entity);
}
