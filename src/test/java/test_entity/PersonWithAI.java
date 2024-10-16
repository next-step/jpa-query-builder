package test_entity;

import jakarta.persistence.*;

@Entity
@Table(name = "person_ai")
public class PersonWithAI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    public PersonWithAI() {
    }

    public PersonWithAI(Long id, int age, String name) {
        this.age = age;
        this.id = id;
        this.name = name;
    }
}
