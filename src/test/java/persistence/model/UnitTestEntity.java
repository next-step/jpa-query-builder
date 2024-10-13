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
}
