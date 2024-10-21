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

    public Person() {}

    private Person(Long id, String name, Integer age, String email, Integer index) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.email = email;
        this.index = index;
    }

    public static Person of(Long id, String name, Integer age, String email, Integer index) {
        return new Person(id, name, age, email, index);
    }
}
