package hibernate.strategy;

import hibernate.entity.column.EntityColumn;

public class PrimaryKetOptionGenerateStrategy implements ColumnOptionGenerateStrategy {

    private static final String PRIMARY_KEY_COLUMN_OPTION = "primary key";

    @Override
    public boolean acceptable(final EntityColumn entityColumn) {
        return entityColumn.isId();
    }

    @Override
    public String generateColumnOption() {
        return PRIMARY_KEY_COLUMN_OPTION;
    }
}
