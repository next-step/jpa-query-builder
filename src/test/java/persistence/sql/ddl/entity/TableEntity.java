package persistence.sql.ddl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "table")
@Entity
public class TableEntity {
    @Id
    private Long id;
}
