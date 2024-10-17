package persistence.sql.ddl.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.exception.UnknownException;

public class H2Dialect implements Dialect {

    @Override
    public String getIdentityGenerationType(GenerationType type) {
        if (GenerationType.IDENTITY == type) {
            return "auto_increment";
        }

        throw new UnknownException("generation type : " + type.name());
    }

}