package persistence.sql.ddl.constraint;

import persistence.sql.mapper.ColumnType;

public interface Constraint {


    boolean check(ColumnType columnType);

    String generate();

}
