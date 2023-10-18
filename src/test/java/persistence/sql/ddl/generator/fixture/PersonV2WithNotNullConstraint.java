package persistence.sql.ddl.generator.fixture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PersonV2WithNotNullConstraint {

    @Id
    private Long id;

    private String name;

    private Integer age;

    @Column(nullable = false)
    private String email;
}
