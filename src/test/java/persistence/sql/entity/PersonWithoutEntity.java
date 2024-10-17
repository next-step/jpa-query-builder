package persistence.sql.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
public class PersonWithoutEntity {
    @Id
    private Long id;
    private String name;
    private Integer age;
}

