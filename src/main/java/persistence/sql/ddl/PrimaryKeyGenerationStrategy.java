package persistence.sql.ddl;

import persistence.sql.ddl.definition.TableId;

public interface PrimaryKeyGenerationStrategy {
    String generatePrimaryKeySQL(TableId pk);

    boolean supports(TableId pk);
}
