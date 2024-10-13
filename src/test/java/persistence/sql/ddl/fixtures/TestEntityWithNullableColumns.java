package persistence.sql.ddl.fixtures;

import jakarta.persistence.*;

@Entity
public class TestEntityWithNullableColumns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nullableColumn1;

    @Column(nullable = true)
    private String nullableColumn2;

    @Column(nullable = false)
    private String nonNullableColumn;

}
