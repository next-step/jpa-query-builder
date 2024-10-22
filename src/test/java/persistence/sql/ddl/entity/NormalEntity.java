package persistence.sql.ddl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class NormalEntity {
    @Id
    private Long id;

    private String name;

    private String address;

    @Transient
    private String fake;

    public NormalEntity() {
    }

    public NormalEntity(Long id, String name, String address, String fake) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fake = fake;
    }
}
