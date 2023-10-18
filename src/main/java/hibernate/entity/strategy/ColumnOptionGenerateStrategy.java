package hibernate.entity.strategy;

import hibernate.entity.EntityColumn;

public interface ColumnOptionGenerateStrategy {

    boolean acceptable(EntityColumn entityColumn);

    String generateColumnOption();
}
