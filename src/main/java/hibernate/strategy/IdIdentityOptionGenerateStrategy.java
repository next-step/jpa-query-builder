package hibernate.strategy;

import hibernate.entity.column.EntityColumn;
import jakarta.persistence.GenerationType;

public class IdIdentityOptionGenerateStrategy implements ColumnOptionGenerateStrategy {

    private static final String AUTO_INCREMENT_COLUMN_OPTION = "auto_increment";

    @Override
    public boolean acceptable(final EntityColumn entityColumn) {
        return entityColumn.isId() && entityColumn.getGenerationType() == GenerationType.IDENTITY;
    }

    @Override
    public String generateColumnOption() {
        return AUTO_INCREMENT_COLUMN_OPTION;
    }
}
