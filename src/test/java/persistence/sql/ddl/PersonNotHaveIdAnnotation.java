package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class PersonNotHaveIdAnnotation {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.persistence.Column(name = "nick_name")
    private String name;

    @jakarta.persistence.Column(name = "old")
    private Integer age;

    @Column(nullable = false)
    private String email;
}
