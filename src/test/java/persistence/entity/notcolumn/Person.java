package persistence.entity.notcolumn;

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

    public Person(String name, int age, String email, int index) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.index = index;
    }

    public Person(Long id, String name, Integer age, String email, Integer index) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.index = index;
    }

    public Long getId() {
        return id;
    }
}
