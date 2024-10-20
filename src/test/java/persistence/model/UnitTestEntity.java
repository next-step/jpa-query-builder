package persistence.model;

import jakarta.persistence.*;

@Table(name = "users")
@Entity
public class UnitTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name", nullable = false)
    private String name;

    @Column(name = "login_id", length = 100)
    private String email;

    private Integer age;

    public UnitTestEntity(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public UnitTestEntity() {
    }
}
