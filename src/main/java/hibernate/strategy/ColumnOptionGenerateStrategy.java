package hibernate.strategy;

import hibernate.entity.column.EntityColumn;

public interface ColumnOptionGenerateStrategy {

    boolean acceptable(EntityColumn entityColumn);

    String generateColumnOption();
}
