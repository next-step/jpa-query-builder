package persistence.sql.ddl.generator.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class PersonV1WithNoEntityAnnotation {

    @Id
    private Long id;

    private String name;

    private Integer age;
}
