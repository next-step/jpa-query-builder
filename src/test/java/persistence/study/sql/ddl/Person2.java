package persistence.study.sql.ddl;

import jakarta.persistence.*;

@Entity
public class Person2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(nullable = false)
    private String email;

    protected Person2() {

    }

    public Person2(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
