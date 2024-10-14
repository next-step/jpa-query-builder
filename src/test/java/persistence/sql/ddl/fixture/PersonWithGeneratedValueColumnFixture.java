package persistence.sql.ddl.fixture;

import jakarta.persistence.*;

@Entity
public class PersonWithGeneratedValueColumnFixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(nullable = false)
    private String email;

}
