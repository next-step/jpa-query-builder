package persistence.entity;

import jakarta.persistence.*;


@Entity
public class Person1 {

    @Id
    private Long id;

    private String name;

    private Integer age;
}
