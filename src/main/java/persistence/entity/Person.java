package persistence.entity;

import persistence.sql.Entity;
import persistence.sql.Id;

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

    public Person(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
