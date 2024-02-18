package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PersonV1 {

    @Id
    private Long id;

    private String name;

    private Integer age;

    public PersonV1() {
    }

    public PersonV1(final Long id, final String name, final Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
