package persistence.sql.ddl;

import jakarta.persistence.Column;

public class TestEntity {
    private String name;

    @Column(name = "zip_address", nullable = false)
    private String address;
}
