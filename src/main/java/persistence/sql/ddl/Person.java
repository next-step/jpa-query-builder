package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    @Transient
    private Integer age;

}
