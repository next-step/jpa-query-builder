package persistence.sql.ddl.definition;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.Queryable;
import persistence.sql.ddl.query.AutoKeyGenerationStrategy;
import persistence.sql.ddl.query.IdentityKeyGenerationStrategy;
import persistence.sql.ddl.PrimaryKeyGenerationStrategy;

import java.lang.reflect.Field;
import java.util.List;

public class PrimaryKey implements Queryable {

    private final GenerationType generationType;
    private final ColumnDefinition columnDefinition;
    private final PrimaryKeyGenerationStrategy strategy;

    public PrimaryKey(Field field) {
        List<PrimaryKeyGenerationStrategy> pkGenerationStrategies = List.of(
                new AutoKeyGenerationStrategy(),
                new IdentityKeyGenerationStrategy()
        );
        this.columnDefinition = new ColumnDefinition(field);
        this.generationType = determineGenerationType(field);
        this.strategy = pkGenerationStrategies.stream()
                .filter(pkStrategy -> pkStrategy.supports(this))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unsupported primary key generation strategy"));
    }

    private static GenerationType determineGenerationType(Field field) {
        final boolean hasGeneratedValueAnnotation = field.isAnnotationPresent(GeneratedValue.class);
        final GenerationType generatedType = GenerationType.AUTO;

        if (hasGeneratedValueAnnotation) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            return generatedValue.strategy();
        }

        return generatedType;
    }

    public String name() {
        return columnDefinition.name();
    }

    public GenerationType generationType() {
        return generationType;
    }

    @Override
    public void apply(StringBuilder query) {
        query.append(columnDefinition.name()).append(" ").append(columnDefinition.type());

        if (columnDefinition.shouldNotNull()) {
            query.append(" NOT NULL");
        }

        query.append(" ").append(strategy.generatePrimaryKeySQL(this)).append(", ");
    }
}
