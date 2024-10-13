package persistence.sql.ddl;

import persistence.sql.ddl.definition.PrimaryKey;

public interface PrimaryKeyGenerationStrategy {
    String generatePrimaryKeySQL(PrimaryKey pk);

    boolean supports(PrimaryKey pk);
}
