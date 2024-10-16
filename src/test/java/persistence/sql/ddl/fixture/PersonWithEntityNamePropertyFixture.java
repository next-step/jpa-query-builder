package persistence.sql.ddl.fixture;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "users")
public class PersonWithEntityNamePropertyFixture {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
