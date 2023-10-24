package persistence.sql.ddl.constraint;

import persistence.sql.ddl.utils.ColumnType;

public class PrimaryKeyConstraint implements Constraint {
    @Override
    public boolean check(final ColumnType columnType) {
        return columnType.isId();
    }

    @Override
    public String generate() {
        return "primary key";
    }
}
