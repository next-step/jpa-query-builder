package persistence.sql.ddl;

public class ColumnBuilder {
    public ColumnBuilder() {
    }

    public String buildColumnToCreate(Column column) {
        ConstraintBuilder constraintBuilder = new ConstraintBuilder();

        return new StringBuilder()
                .append(column.getName() + " " + column.getType())
                .append(constraintBuilder.buildNullable(column.getConstraint()))
                .append(constraintBuilder.bulidGeneratedType(column.getConstraint()))
                .append(constraintBuilder.buildPrimaryKey(column.getConstraint()))
                .toString();
    }
}
