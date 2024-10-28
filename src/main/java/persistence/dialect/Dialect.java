package persistence.dialect;

import jakarta.persistence.GenerationType;

public interface Dialect {
    String getColumnType(int type);
    String getIdentifierGenerationType(GenerationType type);
}
