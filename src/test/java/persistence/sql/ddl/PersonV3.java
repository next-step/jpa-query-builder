package persistence.sql.ddl;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class PersonV3 {

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

    public PersonV3() {
    }

    public PersonV3(final Long id, final String name, final Integer age, final String email, final int index) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.index = index;
    }

}
