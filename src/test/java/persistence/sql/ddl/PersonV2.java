package persistence.sql.ddl;

import jakarta.persistence.*;

@Entity
public class PersonV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(nullable = false)
    private String email;

    public PersonV2() {
    }

    public PersonV2(final Long id, final String name, final Integer age, final String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

}
