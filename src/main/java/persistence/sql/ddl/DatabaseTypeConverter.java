package persistence.sql.ddl;

import persistence.sql.ddl.domain.DatabaseColumn;

public interface DatabaseTypeConverter {

    String convert(DatabaseColumn column);
}
