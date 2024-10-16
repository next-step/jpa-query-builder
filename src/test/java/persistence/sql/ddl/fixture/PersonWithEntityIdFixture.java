package persistence.sql.ddl.fixture;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PersonWithEntityIdFixture {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
