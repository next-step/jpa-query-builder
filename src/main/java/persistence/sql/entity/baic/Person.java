package persistence.sql.entity.baic;

import jakarta.persistence.*;

@Entity
public class Person {

    @Id
    private Long id;

    private String name;

    private Integer age;

}
