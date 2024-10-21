package persistence.sql.ddl.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.ddl.EntityColumn;

public interface Dialect {
    String getColumnDefinition(int type, EntityColumn column);

    String getGenerationDefinition(GenerationType generationType);
}
