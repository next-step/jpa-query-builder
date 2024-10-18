package service.person;

import entity.Person;
import persistence.EntityManager;
import service.person.request.PersonRequest;
import service.person.response.PersonResponse;

import java.util.List;

public class PersonService {

    private final EntityManager entityManager;

    public PersonService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //Person을 저장한다.
    public void save(PersonRequest personRequest) {
        entityManager.persist(personRequest.toEntity());
    }

    //Person을 가져온다.
    public List<PersonResponse> findAll() {
        List<Person> personList = entityManager.findAll(Person.class);
        return personList.stream().map(PersonResponse::of).toList();
    }

    //id로 Person을 가져온다.
    public PersonResponse findById(Long id) {
        Person person = entityManager.find(Person.class, id);
        return PersonResponse.of(person);
    }

    //id로 Person을 삭제한다.
    public void deleteById(Long id) {
        Person person = entityManager.find(Person.class, id);
        entityManager.remove(person);
    }
}
