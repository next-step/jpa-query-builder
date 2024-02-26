package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person1 {

    @Id
    private Long id;

    private String name;

    private Integer age;

    public Person1() {

    }

    public Person1(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
