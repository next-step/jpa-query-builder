package persistence.sql.ddl.fixture;

import jakarta.persistence.*;

@Entity
public class IdentityStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Transient
    private String invalid;

    protected IdentityStrategy() {
    }

    public IdentityStrategy(Long id, String name, String invalid) {
        this.id = id;
        this.name = name;
        this.invalid = invalid;
    }
}
