package hibernate.ddl.strategy;

import hibernate.entity.column.EntityColumn;

public interface ColumnOptionGenerateStrategy {

    boolean acceptable(EntityColumn entityColumn);

    String generateColumnOption();
}
