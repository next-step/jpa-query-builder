package persistence.sql.ddl.generator.example;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Table(name = "users")
@Entity
public class PersonV3WithTable {

    @Id
    private Long id;

    private String name;

    private Integer age;

    private String email;
}
