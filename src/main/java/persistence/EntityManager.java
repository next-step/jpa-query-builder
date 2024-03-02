package persistence;

import domain.Person;

public interface EntityManager {

    // TODO: ObjectMapper 부터 제네릭으로 수정하고 <T> T find(Class<T> clazz, Long Id); 으로 바꾸기
    Person find(Class<Person> clazz, Long id);

    Object persist(Object entity);

    void remove(Object entity);
}
