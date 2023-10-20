package persistence.sql.dbms.mapper.name;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FakeVeryVeryLongNameEntity {
    @Id
    private Long id;
}
