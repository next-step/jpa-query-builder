package persistence.sql.ddl.query;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.PrimaryKeyGenerationStrategy;
import persistence.sql.ddl.definition.PrimaryKey;

public class AutoKeyGenerationStrategy implements PrimaryKeyGenerationStrategy {

    @Override
    public String generatePrimaryKeySQL(PrimaryKey pk) {
        return "";
    }

    @Override
    public boolean supports(PrimaryKey pk) {
        return pk.generationType().equals(GenerationType.AUTO);
    }
}
