package persistence.person;

import jakarta.persistence.Id;

public class PersonOne {
    @Id
    private Long id;

    private String name;

    private Integer age;
}
