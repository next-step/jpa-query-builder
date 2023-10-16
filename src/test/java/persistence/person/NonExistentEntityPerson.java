package persistence.person;

import jakarta.persistence.Id;

public class NonExistentEntityPerson {
    @Id
    private Long id;

    private String name;

    private Integer age;
}
