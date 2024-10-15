package persistence.sql.ddl.query;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.PrimaryKeyGenerationStrategy;
import persistence.sql.definition.TableId;

public class AutoKeyGenerationStrategy implements PrimaryKeyGenerationStrategy {

    @Override
    public String generatePrimaryKeySQL(TableId pk) {
        return "";
    }

    @Override
    public boolean supports(TableId pk) {
        return pk.generationType().equals(GenerationType.AUTO);
    }
}
