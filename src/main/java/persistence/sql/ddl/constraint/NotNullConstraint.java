package persistence.sql.ddl.constraint;

import persistence.sql.ddl.utils.ColumnType;

public class NotNullConstraint implements Constraint {

    @Override
    public boolean check(ColumnType columnType) {
        return !columnType.isNullable();
    }

    @Override
    public String generate() {
        return "not null";
    }
}
