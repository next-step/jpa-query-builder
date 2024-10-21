package persistence.sql.ddl.fixture;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "my_table")
@Entity
public class EntityWithTable {
    @Id
    private Long id;
    private String name;
}
