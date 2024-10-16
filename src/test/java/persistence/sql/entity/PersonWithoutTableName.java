package persistence.sql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class PersonWithoutTableName {
    @Id
    private Long id;
    private String name;
    private Integer age;
}

