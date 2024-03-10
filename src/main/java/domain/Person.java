package domain;

import jakarta.persistence.*;

import java.util.Objects;

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

    private Person(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public static Person of(String name, Integer age, String email) {
        return new Person(null, name, age, email);
    }

    public static Person of(Long id, String name, Integer age, String email) {
        return new Person(id, name, age, email);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Person person = (Person) object;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(age, person.age) && Objects.equals(email, person.email) && Objects.equals(index, person.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, index);
    }
}
