package persistence.sql.ddl.generator.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
public class PersonV3WithTransient {

    @Id
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @Transient
    private Integer index;

}
