package persistence.study.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person1 {

    @Id
    private Long id;

    private String name;

    private Integer age;

    protected Person1() {

    }

    public Person1(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
