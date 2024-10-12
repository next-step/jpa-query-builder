package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.query.CreateQueryBuilder.SQLTypeTranslator;
import persistence.sql.ddl.query.PrimaryKeyGenerationStrategy;

import java.lang.reflect.Field;
import java.util.Objects;

public class TableColumn {

    private final String name;
    private final String type;
    private final boolean nullable;
    private final boolean isTransient;

    private final boolean isPrimaryKey;
    private PrimaryKey primaryKey;

    public TableColumn(Field field) {
        boolean hasColumnAnnotation = field.isAnnotationPresent(Column.class);
        boolean nullable = false;
        boolean isPrimaryKey = field.isAnnotationPresent(Id.class);
        String columnType = field.getType().getSimpleName();
        String columnDefinition = field.getName();

        if (hasColumnAnnotation) {
            Column column = field.getAnnotation(Column.class);
            columnDefinition = Objects.equals(column.name(), "") ? columnDefinition : column.name();
            nullable = column.nullable();
        }

        if (isPrimaryKey) {
            GeneratedValue generatedValue = null;

            if (field.isAnnotationPresent(GeneratedValue.class)) {
                generatedValue = field.getAnnotation(GeneratedValue.class);
            }

            this.primaryKey = new PrimaryKey(this, generatedValue);
        }

        this.isTransient = field.isAnnotationPresent(Transient.class);
        this.name = columnDefinition;
        this.type = columnType;
        this.nullable = nullable;
        this.isPrimaryKey = isPrimaryKey;
    }

    public String name() {
        return name;
    }

    public Boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public PrimaryKey primaryKey() {
        return primaryKey;
    }

    public void applyToQuery(StringBuilder query, PrimaryKeyGenerationStrategy strategy) {
        if (isTransient) return;

        String sqlType = SQLTypeTranslator.translate(type);
        query.append(name).append(" ").append(sqlType);

        if (!nullable) {
            query.append(" NOT NULL");
        }

        if (isPrimaryKey()) {
            PrimaryKey pk = primaryKey();
            query.append(" ").append(strategy.generatePrimaryKeySQL(pk));
        }

        query.append(", ");
    }
}
