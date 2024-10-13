package persistence.sql.ddl.fixtures;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestEntityWithDifferentColumnLengths {

    @Id
    private Long id;

    private String column1;

    @Column
    private String column2;

    @Column(length = 100)
    private String column3;
}
