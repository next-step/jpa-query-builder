package persistence.sql.dml.entity;

import jakarta.persistence.*;

@Table(name = "users")
public class NotEntityPerson {
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

    protected NotEntityPerson() {

    }

    public NotEntityPerson(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

}
