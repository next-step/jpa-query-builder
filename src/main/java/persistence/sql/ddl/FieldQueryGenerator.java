package persistence.sql.ddl;

import persistence.sql.meta.Column;
import persistence.sql.dialect.Dialect;

public class FieldQueryGenerator {

    private final Dialect dialect;
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String PRIMARY_KEY_DEFINITION = "PRIMARY KEY";
    private static final String NOTNULL_DEFINITION = "NOT NULL";


    private FieldQueryGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public static FieldQueryGenerator from(Dialect dialect) {
        return new FieldQueryGenerator(dialect);
    }

    public String generate(Column field) {
        StringBuilder builder = new StringBuilder();

        builder.append(field.getColumnName());
        builder.append(SPACE);
        builder.append(getSqlType(field));
        builder.append(getGeneratedValue(field));
        builder.append(getPkConstraint(field));
        builder.append(getNotNullConstraint(field));

        return builder.toString();
    }

    private String getGeneratedValue(Column field) {
        if (field.isGeneratedValueAnnotation()) {
            return SPACE + dialect.getAutoIncrementDefinition();
        }
        return EMPTY;
    }

    private String getSqlType(Column field) {
        Class<?> type = field.getType();
        return dialect.getSqlTypeDefinition(type);
    }

    private String getPkConstraint(Column field) {
        if (field.isIdAnnotation()) {
            return SPACE + PRIMARY_KEY_DEFINITION;
        }
        return EMPTY;
    }

    private String getNotNullConstraint(Column field) {
        if (!field.isNullable()) {
            return SPACE + NOTNULL_DEFINITION;
        }
        return EMPTY;
    }
}
