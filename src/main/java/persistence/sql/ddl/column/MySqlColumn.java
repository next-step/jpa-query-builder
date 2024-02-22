package persistence.sql.ddl.column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class MySqlColumn implements Column {

    private static final int DEFAULT_LENGTH = 255;
    private static final String BLANK = " ";

    private final String name;
    private final ColumnType type;
    private final int length;
    private final GenerationTypeStrategy generatedValueStrategy;
    private final boolean nullable;

    private MySqlColumn(String name, ColumnType type, int length, GenerationTypeStrategy generatedValueStrategy, boolean nullable) {
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

        String name = findName(field, column);
        ColumnType type = ColumnType.findColumnType(field.getType());
        GenerationTypeStrategy generatedValueStrategy = GenerationTypeStrategy.from(generatedValue);
        int length = findLength(column);
        boolean nullable = findNullable(column);

        return new MySqlColumn(name, type, length, generatedValueStrategy, nullable);
    }

    private static String findName(Field field, jakarta.persistence.Column column) {
        if (column == null || column.name().isEmpty()) {
            return field.getName();
        }

        return column.name();
    }

    private static int findLength(jakarta.persistence.Column column) {
        if (column == null) {
            return DEFAULT_LENGTH;
        }

        return column.length();
    }

    private static boolean findNullable(jakarta.persistence.Column column) {
        return column != null && column.nullable();
    }

    @Override
    public String defineColumn() {
        return name +
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

        return String.format("(%d)", length);
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
