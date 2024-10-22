package persistence.sql.ddl.fixture;

import jakarta.persistence.Id;

public class IncludeId {
    @Id
    private Long id;
    private String name;
}
