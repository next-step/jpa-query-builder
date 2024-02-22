package persistence.sql.ddl.column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;


public class MySqlColumn implements Column {

    private static final String BLANK = " ";

    private final ColumnName name;
    private final ColumnType type;
    private final ColumnLength length;
    private final GenerationTypeStrategy generatedValueStrategy;
    private final boolean nullable;

    private MySqlColumn(ColumnName name, ColumnType type, ColumnLength length, GenerationTypeStrategy generatedValueStrategy, boolean nullable) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.generatedValueStrategy = generatedValueStrategy;
        this.nullable = nullable;
    }

    public static MySqlColumn from(Field field) {
        jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);

        if (field.isAnnotationPresent(Transient.class)) {
            return null;
        }

        ColumnName name = ColumnName.from(field);
        ColumnType type = ColumnType.findColumnType(field.getType());
        GenerationTypeStrategy generatedValueStrategy = GenerationTypeStrategy.from(generatedValue);
        ColumnLength length = ColumnLength.from(field);
        boolean nullable = column != null && column.nullable();

        return new MySqlColumn(name, type, length, generatedValueStrategy, nullable);
    }

    @Override
    public String defineColumn() {
        return name.getName() +
                BLANK +
                type +
                getLengthDefinition() +
                BLANK +
                getGeneratedValueStrategyDefinition() +
                getNullableDefinition();
    }

    private String getLengthDefinition() {
        if (type != ColumnType.VARCHAR) {
            return "";
        }

        return String.format("(%d)", length.getLength());
    }

    private String getGeneratedValueStrategyDefinition() {
        String generatedValueStrategyDefinition = "";

        if (generatedValueStrategy != null) {
            generatedValueStrategyDefinition = generatedValueStrategy.getMySqlStrategyDDL() + BLANK;
        }

        return generatedValueStrategyDefinition;
    }

    private String getNullableDefinition() {
        if (nullable) {
            return "null";
        }

        return "not null";
    }
}
