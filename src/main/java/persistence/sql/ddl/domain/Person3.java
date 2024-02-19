package persistence.sql.ddl.domain;


@Table(name = "users")
@Entity
public class Person3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "old")
    private Integer age;
    @Column(nullable = false)
    private String email;
    @Column(name = "nick_name")
    private String name;
    @Transient
    private Integer index;
}