package persistence.study.sql.ddl;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class Person3 {

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

    protected Person3() {

    }

    public Person3(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}