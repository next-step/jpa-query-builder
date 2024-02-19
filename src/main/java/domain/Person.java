package domain;

import persistence.sql.ddl.Entity;
import persistence.sql.ddl.Id;

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
