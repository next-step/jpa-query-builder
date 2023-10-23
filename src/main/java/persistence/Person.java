package persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Table(name = "users")
@Entity
public class Person {

    public Person() {}
    public Person(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

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

    public Person setEmail(String email) {
        this.email = email;
        return this;
    }

    public Person setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Person setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Person(" +
            "id: " + this.id +
            ", name: " + this.name +
            ", age: " + this.age +
            ", email: " + this.email +
            ", index: " + this.index +
            ")";
    }
}
