package domain;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(nullable = false)
    private String email;

    @Transient
    private Integer index;

    public Person(Long id, String name, Integer age, String email, Integer index) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.index = index;
    }

    public Person() {

    }

    public Person id(Long id) {
        this.id = id;
        return this;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public Person age(Integer age) {
        this.age = age;
        return this;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }

    public Person index(Integer index) {
        this.index = index;
        return this;
    }

    public Person build() {
        return new Person(id, name, age, email, index);
    }
}
