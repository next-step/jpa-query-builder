package persistence.sql.ddl.impl.fixture;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ExampleClassOne {
    @Id
    private Long id;

    private String name;

    private Integer age;
}
