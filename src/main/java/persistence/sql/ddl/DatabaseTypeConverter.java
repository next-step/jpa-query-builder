package persistence.sql.ddl;

import persistence.sql.domain.DatabaseColumn;

public interface DatabaseTypeConverter {

    String convert(DatabaseColumn column);
}
