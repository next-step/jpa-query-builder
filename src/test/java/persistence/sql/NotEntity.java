package persistence.sql;

import jakarta.persistence.Id;

public class NotEntity {
    @Id
    private Long id;

    private String name;

    private Integer age;
}
