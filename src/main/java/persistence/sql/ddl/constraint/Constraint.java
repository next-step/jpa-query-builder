package persistence.sql.ddl.constraint;

import persistence.sql.ddl.utils.ColumnType;

public interface Constraint {


    boolean check(ColumnType columnType);

    String generate();

}
