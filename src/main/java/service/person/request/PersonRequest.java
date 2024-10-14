package service.person.request;

import entity.Person;

public class PersonRequest {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Integer index;

    public PersonRequest(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Person toEntity() {
        return new Person(id, name, age, email, index);
    }
}
