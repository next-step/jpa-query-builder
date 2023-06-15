package domain;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old", length = 3)
    private Integer age;

    @Column(nullable = false, length = 320)
    private String email;

    @Transient
    private Integer index;

    public Long getId() {
        return id;
    }

    public Person setId(Long id) {
        this.id = id;
        return this;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Person setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Person setEmail(String email) {
        this.email = email;
        return this;
    }

    public Person setIndex(Integer index) {
        this.index = index;
        return this;
    }
}
