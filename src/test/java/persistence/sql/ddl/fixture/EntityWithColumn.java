package persistence.sql.ddl.fixture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EntityWithColumn {

    @Id
    private Long id;

    @Column(name = "my_column")
    private String withColumn;

    private String withoutColumn;
}
