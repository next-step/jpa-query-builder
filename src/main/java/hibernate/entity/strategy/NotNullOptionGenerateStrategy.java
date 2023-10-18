package hibernate.entity.strategy;

import hibernate.entity.EntityColumn;

public class NotNullOptionGenerateStrategy implements ColumnOptionGenerateStrategy {

    private static final String NOT_NULL_COLUMN_OPTION = "not null";

    @Override
    public boolean acceptable(EntityColumn entityColumn) {
        return !entityColumn.isNullable();
    }

    @Override
    public String generateColumnOption() {
        return NOT_NULL_COLUMN_OPTION;
    }
}
