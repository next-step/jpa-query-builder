package entity;

import persistence.ddl.Entity;
import persistence.ddl.Id;

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
