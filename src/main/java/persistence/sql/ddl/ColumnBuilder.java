package persistence.sql.ddl;

public class ColumnBuilder {
    public ColumnBuilder() {
    }

    public String buildColumnToCreate(Column column) {
        return column.getName() + " " + column.getType();
    }

    public String buildPKColumnToCreate(Column column) {
        ConstraintBuilder constraintBuilder = new ConstraintBuilder();

        return constraintBuilder.buildPrimaryKey(column.getConstraint());
    }
}
