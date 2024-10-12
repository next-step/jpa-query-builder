package persistence.sql.ddl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class TestEntity {
    private String name;

    @Column(name = "zip_address", nullable = false)
    private String address;

    private Integer defaultId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identityId;
}
