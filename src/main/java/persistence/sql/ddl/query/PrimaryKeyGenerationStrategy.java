package persistence.sql.ddl.query;

import persistence.sql.ddl.PrimaryKey;

public interface PrimaryKeyGenerationStrategy {
    String generatePrimaryKeySQL(PrimaryKey pk);

    Boolean supports(PrimaryKey pk);
}
