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

    public Person(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", index=" + index +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

}
