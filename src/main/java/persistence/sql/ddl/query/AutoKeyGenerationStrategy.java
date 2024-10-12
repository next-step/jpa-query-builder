package persistence.sql.ddl.query;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.PrimaryKey;

public class AutoKeyGenerationStrategy implements PrimaryKeyGenerationStrategy {

    @Override
    public String generatePrimaryKeySQL(PrimaryKey pk) {
        return "";
    }

    @Override
    public Boolean supports(PrimaryKey pk) {
        return pk.generationType().equals(GenerationType.AUTO);
    }
}
