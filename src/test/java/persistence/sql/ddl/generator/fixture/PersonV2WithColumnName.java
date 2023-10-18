package persistence.sql.ddl.generator.fixture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PersonV2WithColumnName {

    @Id
    private Long id;

    @Column(name = "nick_name")
    private String name;

    @Column(name = "old")
    private Integer age;

    @Column(name = "email_contact")
    private String email;

}
