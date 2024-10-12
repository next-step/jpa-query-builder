package persistence.sql.ddl.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

public class NormalEntity {
    @Id
    private Long id;

    private String name;

    private String address;

    @Transient
    private String fake;
}
