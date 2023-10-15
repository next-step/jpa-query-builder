package persistence.sql.ddl;

import jakarta.persistence.Id;


public class NoEntityPerson {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
