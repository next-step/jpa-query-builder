package persistence.sql.ddl.fixtures;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EntityWithAutoIdStrategy {
    @Id
    private Long id;
}
