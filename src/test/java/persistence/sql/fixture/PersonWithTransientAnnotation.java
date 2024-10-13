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
}
