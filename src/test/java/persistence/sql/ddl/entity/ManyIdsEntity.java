package persistence.sql.ddl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ManyIdsEntity {
    @Id
    private Long id;

    @Id
    private Long id2;
}
