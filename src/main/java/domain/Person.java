package domain;


import jakarta.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old", length = 3)
    private Integer age;

    @Column(nullable = false)
    private String email;
}
