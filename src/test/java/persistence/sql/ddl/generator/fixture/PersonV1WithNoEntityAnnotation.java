package persistence.sql.ddl.generator.fixture;

import jakarta.persistence.Id;

public class PersonV1WithNoEntityAnnotation {

    @Id
    private Long id;

    private String name;

    private Integer age;
}
