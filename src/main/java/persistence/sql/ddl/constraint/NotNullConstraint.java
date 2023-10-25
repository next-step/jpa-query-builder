package persistence.sql.ddl.constraint;

import persistence.sql.ddl.utils.ColumnType;

public class NotNullConstraint implements Constraint {

    @Override
    public boolean check(ColumnType columnType) {
        if(columnType.isId()) {
            return false;
        }
        return !columnType.isNullable();
    }

    @Override
    public String generate() {
        return "not null";
    }
}
