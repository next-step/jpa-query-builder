package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
class Requirement1 {
    @Id
    private Long id;

    private String name;

    private Integer age;
}
