package persistence.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Table(name = "users")
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

    @Transient
    private Integer index;
}