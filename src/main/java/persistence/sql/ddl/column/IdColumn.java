package persistence.sql.ddl.column;

import jakarta.persistence.GeneratedValue;

import java.lang.reflect.Field;

public class IdColumn implements EntityColumn {

    private static final String ID_DEFINITION = "PRIMARY KEY";

    private final Column column;
    private final GenerationTypeStrategy generatedValueStrategy;

    private IdColumn(Column column, GenerationTypeStrategy generatedValueStrategy) {
        this.column = column;
        this.generatedValueStrategy = generatedValueStrategy;
    }

    public static IdColumn from(Field field) {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);

        Column column = Column.from(field);
        GenerationTypeStrategy generationTypeStrategy = GenerationTypeStrategy.from(generatedValue);

        return new IdColumn(column, generationTypeStrategy);
    }

    @Override
    public boolean hasId() {
        return true;
    }

    @Override
    public String defineColumn() {
        return column.getName() +
                BLANK +
                column.getType() +
                column.getLengthDefinition() +
                BLANK +
                generatedValueStrategy.getMySqlStrategyDDL() +
                ID_DEFINITION +
                BLANK +
                column.getNullableDefinition();
    }

    @Override
    public String getName() {
        return column.getName();
    }
}
