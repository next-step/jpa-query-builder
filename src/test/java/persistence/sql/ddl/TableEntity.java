package persistence.sql.ddl;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "table")
public class TableEntity {
    @Id
    private Long id;
}
