package persistence.sql.ddl.test;

import jakarta.persistence.Id;

public class NoExistEntity {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
