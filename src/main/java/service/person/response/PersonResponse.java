package service.person.response;

import entity.Person;

public class PersonResponse {

    private Long id;
    private String name;
    private Integer age;
    private String email;

    public PersonResponse(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public static PersonResponse of(Person person) {
        return new PersonResponse(person.getId(), person.getName(), person.getAge(), person.getEmail());
    }
}
