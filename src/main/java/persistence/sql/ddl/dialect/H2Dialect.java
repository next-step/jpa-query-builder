package persistence.sql.ddl.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.exception.UnknownException;

public class H2Dialect implements Dialect {

    @Override
    public String identityGenerateType(GenerationType type) {
        if (GenerationType.AUTO == type) {
            return "auto_increment";
        }

        throw new UnknownException("generation type : " + type.name());
    }

}
