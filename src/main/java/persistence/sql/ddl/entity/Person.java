package persistence.sql.ddl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

}