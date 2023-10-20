package persistence.person;

import jakarta.persistence.Id;

public class NonExistentEntityPerson {
    @Id
    private Long id;

    private String name;

    private Integer age;

    public NonExistentEntityPerson(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
