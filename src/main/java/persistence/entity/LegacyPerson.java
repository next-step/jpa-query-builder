package persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LegacyPerson {

    @Id
    private Long id;

    private String name;

    private Integer age;

}