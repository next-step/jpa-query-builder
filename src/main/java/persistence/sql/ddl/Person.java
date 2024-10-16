package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

    public Person() {
    }

    public Person(Long id, Integer age, String name) {
        this.age = age;
        this.id = id;
        this.name = name;
    }
}
