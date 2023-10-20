package persistence.sql.cls;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MultipleId {
    @Id
    private Long id;
    @Id
    private String uuid;
}
