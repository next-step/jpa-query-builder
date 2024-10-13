package persistence.sql.ddl.fixtures;

import jakarta.persistence.*;

@Entity
public class TestEntityWithTransientColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String normalColumn;

    @Transient
    private String transientColumn;

}
