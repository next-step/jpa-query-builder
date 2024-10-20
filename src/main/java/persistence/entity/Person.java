package persistence.entity;

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

    public Person() {

    }

    public Person(String name, Integer age, String email, Integer index) {
        this(null, name, age, email, index);
    }

    public Person(Long id, String name, Integer age, String email) {
        this(id, name, age, email, 0);
    }

    private Person(Long id, String name, Integer age, String email, Integer index) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.index = index;
    }

    public String name() {
        return this.name;
    }

}
