package persistence.sql.cls;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MultipleIdClass {
    @Id
    private Long id;
    @Id
    private String uuid;
}
