package persistence.sql.ddl.column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;


public class Column {

    private static final String BLANK = " ";

    private final ColumnName name;
    private final ColumnType type;
    private final ColumnLength length;
    private final GenerationTypeStrategy generatedValueStrategy;
    private final boolean nullable;
    private final boolean hasId;

    private Column(ColumnName name, ColumnType type, ColumnLength length, GenerationTypeStrategy generatedValueStrategy, boolean nullable, boolean hasId) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.generatedValueStrategy = generatedValueStrategy;
        this.nullable = nullable;
        this.hasId = hasId;
    }

    public static Column from(Field field) {
        jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        Id id = field.getAnnotation(Id.class);

        if (field.isAnnotationPresent(Transient.class)) {
            return null;
        }

        ColumnName name = ColumnName.from(field);
        ColumnType type = ColumnType.findColumnType(field.getType());
        GenerationTypeStrategy generatedValueStrategy = GenerationTypeStrategy.from(generatedValue);
        ColumnLength length = ColumnLength.from(field);
        boolean nullable = column != null && column.nullable();
        boolean hasId = id != null;

        return new Column(name, type, length, generatedValueStrategy, nullable, hasId);
    }

    public String defineColumn() {
        return name.getName() +
                BLANK +
                type.getMysqlColumnType() +
                getLengthDefinition() +
                BLANK +
                generatedValueStrategy.getMySqlStrategyDDL() +
                getIdDefinition() +
                getNullableDefinition();
    }

    private String getLengthDefinition() {
        if (type != ColumnType.STRING) {
            return "";
        }

        return String.format("(%d)", length.getLength());
    }

    private String getIdDefinition() {
        if (hasId) {
            return "PRIMARY KEY ";
        }

        return "";
    }

    private String getNullableDefinition() {
        if (nullable) {
            return "null";
        }

        return "not null";
    }

    public boolean hasId() {
        return hasId;
    }
}
