package persistence.sql.fixture;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class PersonWithTransientAnnotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(nullable = false, length = 100)
    private String email;

    @Transient
    private Integer index;

    public PersonWithTransientAnnotation(Long id, String name, Integer age, String email, Integer index) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.index = index;
    }

    public PersonWithTransientAnnotation(String name, Integer age, String email, Integer index) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.index = index;
    }

    public PersonWithTransientAnnotation(String email) {
        this.email = email;
    }

    protected PersonWithTransientAnnotation() {

    }
}
