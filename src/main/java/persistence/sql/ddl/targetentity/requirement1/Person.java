package persistence.sql.ddl.targetentity.requirement1;

import jakarta.persistence.*;

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;
}
