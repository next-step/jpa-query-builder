package persistence.sql.ddl;

import static persistence.sql.ddl.common.StringConstants.COLUMN_DEFINITION_DELIMITER;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.AbstractQueryBuilder;
import persistence.sql.ddl.common.StringConstants;
import persistence.sql.ddl.constraints.strategy.ConstraintsStrategy;
import persistence.sql.ddl.constraints.strategy.DefaultConstraintsStrategy;
import persistence.sql.ddl.type.DataTypeMapping;
import persistence.sql.ddl.type.impl.DefaultDataTypeMapping;

public class CreateQueryBuilder extends AbstractQueryBuilder {
    private final TableQueryBuilder tableQueryBuilder;

    private final DataTypeMapping dataTypeMapping;

    private final ConstraintsStrategy constraintsStrategy;

    public CreateQueryBuilder(TableQueryBuilder tableQueryBuilder) {
        this(
            tableQueryBuilder,
            new DefaultDataTypeMapping(),
            new DefaultConstraintsStrategy()
        );
    }

    public CreateQueryBuilder(
        TableQueryBuilder tableQueryBuilder,
        DataTypeMapping dataTypeMapping,
        ConstraintsStrategy constraintsStrategy
    ) {
        this.tableQueryBuilder = tableQueryBuilder;
        this.dataTypeMapping = dataTypeMapping;
        this.constraintsStrategy = constraintsStrategy;
    }

    public String getCreateTableQuery(final Class<?> entityClass) {
        return String.format(
            "CREATE TABLE %s (%s)",
            tableQueryBuilder.getTableNameFrom(entityClass),
            getColumnDefinitionsFrom(entityClass)
        );
    }

    public String getColumnDefinitionsFrom(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .map(this::getColumnDefinitionFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    public String getColumnDefinitionFrom(Field field) {
        return Stream.of(
                getColumnNameFrom(field),
                dataTypeMapping.getDataTypeDefinitionFrom(field),
                constraintsStrategy.getConstraintsFrom(field)
            )
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining(StringConstants.SPACE));
    }
}
