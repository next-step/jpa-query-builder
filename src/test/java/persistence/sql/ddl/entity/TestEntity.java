package persistence.sql.ddl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class TestEntity {
    private String name;

    @Column(name = "zip_address", nullable = false, length = 10)
    private String address;

    private Integer defaultId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identityId;

    @Column(nullable = false)
    private Integer home;
}
