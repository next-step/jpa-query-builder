package persistence.sql.ddl.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person1 {

    @Id
    private Long id;
    private Integer age;
    private String name;

}