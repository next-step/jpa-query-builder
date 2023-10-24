package persistence.sql.ddl.constraint;

import persistence.sql.ddl.utils.ColumnType;
import persistence.sql.ddl.utils.ColumnType2;

public class PrimaryKeyConstraint implements Constraint {
    @Override
    public boolean check(final ColumnType columnType) {
        return columnType.isPrimaryKey();
    }

    @Override
    public String generate() {
        return "primary key";
    }
}
