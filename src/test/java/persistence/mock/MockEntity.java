package persistence.mock;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MockEntity {

    protected MockEntity() {
    }

    public MockEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private Long id;

    private String name;
}
