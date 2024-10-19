package test_entity;

import jakarta.persistence.*;

@Entity
@Table(name = "person_ai")
public class PersonWithAI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long age;

    public PersonWithAI() {

    }

    public PersonWithAI(Long id, Long age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public PersonWithAI(Long age, String name) {
        this.age = age;
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
