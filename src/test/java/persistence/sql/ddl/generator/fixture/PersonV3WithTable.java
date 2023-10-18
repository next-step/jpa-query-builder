package persistence.sql.ddl.generator.fixture;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "users")
@Entity
public class PersonV3WithTable {

    @Id
    private Long id;

    private String name;

    private Integer age;

    private String email;
}
