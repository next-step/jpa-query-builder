package persistence;

import jakarta.persistence.Id;

public class NotEntity {

    @Id
    private final Long id;

    public NotEntity(Long id) {
        this.id = id;
    }
}
