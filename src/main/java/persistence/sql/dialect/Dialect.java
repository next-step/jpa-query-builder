package persistence.sql.dialect;

import jakarta.persistence.GenerationType;

public interface Dialect {

    String getColumnType(int type);
    String getIdentifierGenerationType(GenerationType type);

}
